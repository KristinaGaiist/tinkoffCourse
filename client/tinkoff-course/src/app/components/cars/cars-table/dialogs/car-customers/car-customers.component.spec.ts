import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CarCustomersComponent} from './car-customers.component';

describe('CarCustomersComponent', () => {
  let component: CarCustomersComponent;
  let fixture: ComponentFixture<CarCustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarCustomersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
