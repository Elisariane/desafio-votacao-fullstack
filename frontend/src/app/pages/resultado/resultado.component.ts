import { Component, OnInit } from '@angular/core';
import { IResultadoVotacao } from '../../../interfaces/IResultadoVotacao';
import { IPauta } from '../../../interfaces/IPauta';
import { ResultadoService } from '../../services/resultado.service';
import { PautaService } from '../../services/pauta.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-resultado',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './resultado.component.html',
  styleUrl: './resultado.component.css'
})
export class ResultadoComponent implements OnInit {
  
pautas: IPauta[] = [];
  showModal = false;
  resultado: IResultadoVotacao | null = null;

  constructor(
    private resultadoService: ResultadoService,
    private pautaService: PautaService

  ) {}

  ngOnInit() {
    this.pautaService.listar().subscribe({
      next: ps => this.pautas = ps,
      error: err => console.error(err)
    });
  }

  abrirResultado(pauta: IPauta) {

    if (pauta.id == null) return;

    this.resultadoService.buscarResultadoPorPauta(pauta.id).subscribe({
      next: res => {
        this.resultado = res;
        this.showModal = true;
      },
      error: err => console.error(err)
    });
  }

  fecharModal() {
    this.showModal = false;
    this.resultado = null;
  }

  getProgressBarWidth(voteCount: number, totalVotes: number): number {
    return totalVotes === 0 ? 0 : (voteCount / totalVotes) * 100;
  }

  obterTituloPauta(id: number | string): string {
    const pauta = this.pautas.find(p => p.id === id);
    return pauta ? pauta.titulo : 'Pauta desconhecida';
  }

}