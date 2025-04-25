import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IPauta } from '../../interfaces/IPauta';

@Injectable({
  providedIn: 'root'
})
export class PautaService {
  private apiUrl = 'http://localhost:8080/v1/pauta';

  constructor(private http: HttpClient) {}

  listar(): Observable<IPauta[]> {
    return this.http.get<IPauta[]>(this.apiUrl);
  }

  criar(pauta: IPauta): Observable<IPauta> {
    return this.http.post<IPauta>(this.apiUrl, pauta);
  }
}