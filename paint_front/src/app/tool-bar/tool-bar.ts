import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DrawTools } from '../types/enum';
@Component({
  selector: 'app-tool-bar',
  imports: [],
  templateUrl: './tool-bar.html',
  styleUrl: './tool-bar.css',
})
export class ToolBar {
  @Input() tool:DrawTools  = DrawTools.Mouse;
  @Output() toolChange=new EventEmitter<DrawTools>();

  setTool(selectedTool:DrawTools){
    this.tool=selectedTool;
    this.toolChange.emit(this.tool);
  }
  protected readonly DrawTools = DrawTools;

  toolsList = [
  { tool: DrawTools.Eraser,    icon: 'ink_eraser' },
  { tool: DrawTools.Line,      icon: 'linear_scale' },
  { tool: DrawTools.Rectangle, icon: 'crop_din' },
  { tool: DrawTools.Ellipse,   icon: 'circle' },
  { tool: DrawTools.Triangle,  icon: 'change_history' },
  { tool: DrawTools.Mouse,     icon: 'mouse' },
];

}
