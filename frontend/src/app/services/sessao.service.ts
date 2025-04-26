import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ISessaoVotacao } from '../../interfaces/ISessaoVotacao';

@Injectable({
  providedIn: 'root'
})
export class SessaoService {

  private baseUrl = 'http://localhost:8080/v1/sessoes';

  constructor(private http: HttpClient) {}

  listar(): Observable<ISessaoVotacao[]> {
    return this.http.get<ISessaoVotacao[]>(this.baseUrl);
  }

  criar(sessaoVotacao: ISessaoVotacao): Observable<ISessaoVotacao> {
    return this.http.post<ISessaoVotacao>(this.baseUrl, sessaoVotacao);
  }

}
