import { Injectable } from '@angular/core';
import { IResultadoVotacao } from '../../interfaces/IResultadoVotacao';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResultadoService {

  private API = 'http://localhost:8080/v1/resultados';

  constructor(private http: HttpClient) {}

  buscarResultadoPorPauta(pautaId: number): Observable<IResultadoVotacao> {
    return this.http.get<IResultadoVotacao>(`${this.API}/${pautaId}`);
  }

}
