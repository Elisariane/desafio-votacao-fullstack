import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IVoto } from '../../interfaces/IVoto';

@Injectable({
  providedIn: 'root'
})
export class VotacaoService {

  private readonly baseUrl = 'http://localhost:8080/v1/votos'; // ajuste se estiver diferente

  constructor(private http: HttpClient) {}

  votar(dados: IVoto): Observable<IVoto> {
    return this.http.post<IVoto>(this.baseUrl, dados);
  }

}
