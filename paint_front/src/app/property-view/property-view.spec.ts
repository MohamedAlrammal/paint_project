import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyView } from './property-view';

describe('ProertyView', () => {
  let component: PropertyView;
  let fixture: ComponentFixture<PropertyView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertyView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyView);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
