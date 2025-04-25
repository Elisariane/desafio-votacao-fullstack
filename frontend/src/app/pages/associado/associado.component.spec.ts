import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssociadoComponent } from './associado.component';

describe('AssociadoComponent', () => {
  let component: AssociadoComponent;
  let fixture: ComponentFixture<AssociadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssociadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssociadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
