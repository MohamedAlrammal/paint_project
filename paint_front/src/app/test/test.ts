import { Component, Input } from '@angular/core';
import { DrawTools } from '../types/enum';

@Component({
  selector: 'app-test',
  imports: [],
  templateUrl: './test.html',
  styleUrl: './test.css',
})
export class Test {
  @Input() tool:DrawTools  = DrawTools.Mouse;
}
