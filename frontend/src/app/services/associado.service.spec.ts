import { TestBed } from '@angular/core/testing';

import { AssociadoService } from './associado.service';

describe('AssociadoService', () => {
  let service: AssociadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssociadoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
