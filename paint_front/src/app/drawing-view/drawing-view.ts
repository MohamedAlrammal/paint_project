import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
import Konva from 'konva';
import { DrawTools, ShapeColor } from '../types/enum';

@Component({
  selector: 'app-drawing-view',
  standalone: true,
  templateUrl: './drawing-view.html',
  styleUrl: './drawing-view.css',
})
export class DrawingViewComponent implements AfterViewInit {
  @ViewChild('stageContainer', { static: true }) stageContainer!: ElementRef<HTMLDivElement>;
  @Input() tool!: DrawTools;
  @Input() colors!: ShapeColor;
  // emit a trigger to revert to mouse after drawing

  private stage!: Konva.Stage;
  private layer!: Konva.Layer;
  private previewShape: Konva.Shape | null = null;
  private startPos = { x: 0, y: 0 };
  private selectedShape: Konva.Shape | null = null;
  private selectionStrokeColor = '#0077ff'; // highlight color
  private deleteKey = ['Delete', 'Backspace'];
  private transformer!: Konva.Transformer;
  private undoStack: string[] = [];
  private redoStack: string[] = [];
  private currentState: string = '';
  ngAfterViewInit(): void {
    const { clientWidth, clientHeight } = this.stageContainer.nativeElement;

    this.stage = new Konva.Stage({
      container: this.stageContainer.nativeElement,
      width: clientWidth,
      height: clientHeight,
    });

    this.layer = new Konva.Layer();
    this.stage.add(this.layer);
    this.transformer = new Konva.Transformer({
      enabledAnchors: [
        'top-left',
        'top-center',
        'top-right',

        'middle-left',
        'middle-right',

        'bottom-left',
        'bottom-center',
        'bottom-right',
      ],
      rotateEnabled: true,
    });
    this.layer.add(this.transformer);
    this.currentState = this.layer.toJSON();
    this.stage.on('mousedown touchstart', (e) => this.onPointerDown());
    this.stage.on('mousemove touchmove', (e) => this.onPointerMove());
    this.stage.on('mouseup touchend', (e) => this.onPointerUp());
    this.layer.on('click', (e) => this.onShapeClick(e));
    this.stage.on('click', (e) => {
      // stage click without shape → deselect
      if (e.target === this.stage) {
        this.clearSelection();
      }
    });
    // erase click
    
    window.addEventListener('keydown', (e) => this.onKeyDown(e));
    this.transformer.on('transformend', () => {
      // this.saveState();
    });
    this.wireLayerEvents();
    window.addEventListener('keydown', (e) => {
      if (e.ctrlKey && e.key.toLowerCase() === 'z') {
        console.log(this.undoStack);
        e.preventDefault();
        this.undo();
      }
      if (e.ctrlKey && e.key.toLowerCase() === 'y') {
        e.preventDefault();
        this.redo();
      }
    });

  }

  private onPointerDown(): void {
    if (!this.shouldDraw()) return;
    
    const pos = this.stage.getPointerPosition();
    if (!pos) return;

    this.startPos = pos;
    this.previewShape = this.createShape(pos.x, pos.y, pos.x, pos.y);
    if (this.previewShape) {
      this.layer.add(this.previewShape);
      this.layer.draw();
    }
  }

  private onPointerMove(): void {
    if (!this.previewShape) return;
    
    const pos = this.stage.getPointerPosition();
    if (!pos) return;
    this.resizeShape(this.previewShape, this.startPos, pos);
    this.layer.batchDraw();
  }

  private onPointerUp(): void {
    // if the element is zero-sized don't add it
    const pos = this.stage.getPointerPosition();
    if(this.startPos.x === pos?.x && this.startPos.y === pos?.y) {
      this.previewShape?.destroy();
      this.previewShape = null;
      this.layer.draw();
      
      return;
    }
    if (!this.previewShape) return;
    this.saveState();
    
    this.previewShape = null;
    
  }

  // ──────────────────────────────────────────────────────────
  private shouldDraw(): boolean {
    return (
      this.tool === DrawTools.Line ||
      this.tool === DrawTools.Rectangle ||
      this.tool === DrawTools.Ellipse ||
      this.tool === DrawTools.Triangle
    );
  }

  private createShape(x1: number, y1: number, x2: number, y2: number): Konva.Shape | null {
    const stroke = this.colors.outline;
    const fill = this.colors.backGround;

    switch (this.tool) {
      case DrawTools.Line:
        return new Konva.Line({
          points: [x1, y1, x2, y2],
          stroke: stroke,
          strokeWidth: 2,
        });

      case DrawTools.Rectangle:
        return new Konva.Rect({
          x: x1,
          y: y1,
          width: 0,
          height: 0,
          stroke: stroke,
          fill: fill,
          strokeWidth: 2,
        });

      case DrawTools.Ellipse:
        return new Konva.Ellipse({
          x: x1,
          y: y1,
          radiusX: 0,
          radiusY: 0,
          stroke: stroke,
          fill: fill,
          strokeWidth: 2,
        });

      case DrawTools.Triangle:
        return new Konva.Line({
          points: [x1, y1, x1, y1, x1, y1],
          closed: true,
          stroke: stroke,
          fill: fill,
          strokeWidth: 2,
        });

      default:
        return null;
    }
  }

  private resizeShape(
    shape: Konva.Shape,
    start: { x: number; y: number },
    pos: { x: number; y: number }
  ) {
    const { x: x1, y: y1 } = start;
    const { x: x2, y: y2 } = pos;

    if (shape instanceof Konva.Line && this.tool === DrawTools.Line) {
      shape.points([x1, y1, x2, y2]);
    }

    if (shape instanceof Konva.Rect) {
      shape.width(x2 - x1);
      shape.height(y2 - y1);
    }

    if (shape instanceof Konva.Ellipse) {
      shape.radiusX(Math.abs(x2 - x1) / 2);
      shape.radiusY(Math.abs(y2 - y1) / 2);
      shape.x((x1 + x2) / 2);
      shape.y((y1 + y2) / 2);
    }

    if (shape instanceof Konva.Line && this.tool === DrawTools.Triangle) {
      shape.points([x1, y2, (x1 + x2) / 2, y1, x2, y2]);
    }
  }
  private onShapeClick(e: Konva.KonvaEventObject<MouseEvent>) {
    if (this.tool !== DrawTools.Mouse) return; // only when Mouse tool selected

    const shape = e.target as Konva.Shape;

    if (shape === this.selectedShape) return;

    this.selectShape(shape);
  }

  private selectShape(shape: Konva.Shape) {
    this.clearSelection();

    this.selectedShape = shape;
    shape.draggable(true);

    // highlight
    shape.stroke(this.selectionStrokeColor);
    shape.strokeWidth(3);

    // attach transformer
    this.transformer.nodes([shape]);

    this.layer.draw();
  }

  private clearSelection() {
    if (!this.selectedShape) return;

    // Restore real colors
    this.selectedShape.stroke(this.colors.outline);
    this.selectedShape.fill(this.colors.backGround);
    this.selectedShape.strokeWidth(2);

    this.selectedShape.draggable(false);
    this.transformer.nodes([]);
    this.saveState();
    this.selectedShape = null;
    this.layer.draw();
  }

  private onKeyDown(e: KeyboardEvent) {
    if (!this.selectedShape) return; // no selected shape → ignore

    // only delete when the Mouse tool is active so tools don't conflict
    if (this.tool !== DrawTools.Mouse) return;

    if (this.deleteKey.includes(e.key)) {
      this.selectedShape.destroy(); // remove from canvas
      this.transformer.nodes([]);
      this.selectedShape = null; // clear internal reference
      this.layer.draw(); // update layer
      // this.saveState();
    }
  }
private saveState() {
  const current = this.layer.toJSON(); // snapshot
  if (this.currentState === current) return; // no changes
  if (this.currentState !== '') {
    this.undoStack.push(this.currentState);
  }
  this.currentState = current;
}


  private undo() {
    if (this.undoStack.length === 0) return;
    this.redoStack.push(this.currentState);
    this.currentState = this.undoStack.pop()!;
    this.applyState(this.currentState);
  }



  private redo() {
    if (this.redoStack.length === 0) return;
    this.undoStack.push(this.currentState);
    this.currentState = this.redoStack.pop()!;
    this.applyState(this.currentState);
  }
  private applyState(json: string) {
    this.layer.destroy();
    this.layer = Konva.Node.create(json) as Konva.Layer;
    this.stage.add(this.layer);
    this.wireLayerEvents();
    this.layer.draw();
  }


  private restoreState(json: string) {
    this.clearSelection();

    this.layer.destroy();
    this.layer = Konva.Node.create(json) as Konva.Layer;
    this.stage.add(this.layer);

    // rebind selection + eraser + drag listeners
    this.wireLayerEvents();
    this.layer.draw();
  }
  private wireLayerEvents() {
    // selection
    this.layer.on('click', (e) => {
      if (this.tool !== DrawTools.Mouse) return;
      if (e.target === this.stage) return this.clearSelection();
      const shape = e.target as Konva.Shape;
      this.selectShape(shape);
    });

    this.layer.on('click', (e) => {
      if (this.tool !== DrawTools.Eraser) return;

      if (e.target === this.stage) return; // ignore empty background

      const shape = e.target as Konva.Shape;

      // if the shape being erased was selected, clear highlight first
      if (this.selectedShape === shape) {
        this.selectedShape = null;
      }

      shape.destroy(); // remove shape permanently
      this.transformer.nodes([]);
      this.layer.draw();
      // this.saveState();
    });
    this.layer.on('click', (e) => this.onShapeClick(e));
    
  }
}
