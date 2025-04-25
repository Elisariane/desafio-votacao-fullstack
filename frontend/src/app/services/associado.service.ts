import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IAssociado } from '../../interfaces/IAssociado';

@Injectable({
  providedIn: 'root'
})
export class AssociadoService {

  private apiUrl = 'http://localhost:8080/v1/associados';

  constructor(private http: HttpClient) {}

  listar(): Observable<IAssociado[]> {
    return this.http.get<IAssociado[]>(this.apiUrl);
  }

  criar(associado: IAssociado): Observable<IAssociado> {
    return this.http.post<IAssociado>(this.apiUrl, associado);
  }

}

