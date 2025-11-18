import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToolBar } from './tool-bar';

describe('ToolBar', () => {
  let component: ToolBar;
  let fixture: ComponentFixture<ToolBar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ToolBar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ToolBar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
