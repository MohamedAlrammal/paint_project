import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToolBar } from './tool-bar/tool-bar';
import { DrawTools, ShapeColor } from './types/enum';
import { PropertyView } from "./property-view/property-view";
import { DrawingViewComponent } from './drawing-view/drawing-view';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ToolBar, PropertyView, DrawingViewComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  tool: DrawTools = DrawTools.Mouse;
  colors : ShapeColor = {outline : "#000000" , backGround : "transparent"};
  onToolChange(selectedTool: DrawTools) {
    this.tool = selectedTool;
  }

  onColorChange(updatedColors: ShapeColor) {
    this.colors = updatedColors;
  }

  protected readonly title = signal('paint_front');
}
