import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShapeColor } from '../types/enum';
import { ColorChromeModule } from 'ngx-color/chrome';

@Component({
  selector: 'app-property-view',
  imports: [CommonModule, ColorChromeModule],
  templateUrl: './property-view.html',
  standalone: true,
  styleUrls: ['./property-view.css'],
})
export class PropertyView {
  @Input() colors : ShapeColor = {outline : "black" , backGround : "#ffffff"};
  @Output() colorChange = new EventEmitter<ShapeColor>();

  presetColors: string[] = ['#000000', '#ffffff','#4cc3d9',
                            '#563838',  '#f4b942', '#e94e3c'];

  setOutlineColor(outlineColor : string) {
    this.colors.outline = outlineColor;
    this.colorChange.emit(this.colors)
  }

  setBackColor(backColor : string) {
    this.colors.backGround = backColor;
    this.colorChange.emit(this.colors)
  }
}