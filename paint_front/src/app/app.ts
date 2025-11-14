import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToolBar } from './tool-bar/tool-bar';
import { DrawTools } from './types/enum';
import { Test } from "./test/test";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ToolBar, Test],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  tool: DrawTools = DrawTools.Mouse;
  onToolChange(selectedTool: DrawTools) {
    this.tool = selectedTool;
  }
  protected readonly title = signal('paint_front');
}
