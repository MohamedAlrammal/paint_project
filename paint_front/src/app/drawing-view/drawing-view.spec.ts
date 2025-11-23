import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrawingView } from './drawing-view';

describe('DrawingView', () => {
  let component: DrawingView;
  let fixture: ComponentFixture<DrawingView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DrawingView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrawingView);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
