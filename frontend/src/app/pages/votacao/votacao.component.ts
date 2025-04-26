import { Component, OnInit } from '@angular/core';
import { AlertComponent } from '../../shared/alert/alert.component';
import { SessaoService } from '../../services/sessao.service';
import { AssociadoService } from '../../services/associado.service';
import { IAssociado } from '../../../interfaces/IAssociado';
import { ISessaoVotacao } from '../../../interfaces/ISessaoVotacao';
import { VotacaoService } from '../../services/votacao.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-votacao',
  imports: [CommonModule, FormsModule, AlertComponent],
  templateUrl: './votacao.component.html',
  styleUrl: './votacao.component.css'
})
export class VotacaoComponent implements OnInit {
  sessoesAtivas: ISessaoVotacao[] = [];
  associados: IAssociado[] = [];

  sessaoId: number | null = null;
  associadoId: number | null = null;
  voto: 'SIM' | 'NAO' | null = null;

  alertMessage: string | null = null;
  alertType: 'success' | 'danger' = 'success';

  constructor(
    private votoService: VotacaoService,
    private sessaoService: SessaoService,
    private associadoService: AssociadoService
  ) {}

  ngOnInit(): void {
    this.carregarSessoesAtivas();
    this.carregarAssociados();
  }

  carregarSessoesAtivas() {
    this.sessaoService.listar().subscribe({
      next: (res) => {
        this.sessoesAtivas = res.filter(sessao => sessao.ativa);
      },
      error: () => this.showAlert('Erro ao carregar sessões ativas', 'danger', 4000)
    });
  }

  carregarAssociados() {
    this.associadoService.listar().subscribe({
      next: (res) => this.associados = res,
      error: () => this.showAlert('Erro ao carregar associados', 'danger', 4000)
    });
  }

  setVoto(valor: 'SIM' | 'NAO') {
    this.voto = valor;
  }

  registrarVoto() {
    if (!this.sessaoId || !this.associadoId || !this.voto) return;

    this.votoService.votar({
      sessaoId: this.sessaoId,
      associadoId: this.associadoId,
      voto: this.voto
    }).subscribe({
      next: () => this.showAlert('Voto registrado com sucesso!', 'success', 3000),
      error: (err) => {
        const msg = err?.error?.mensagens?.join('\n') || 'Erro ao registrar voto';
        this.showAlert(msg, 'danger', 5000);
      }
    });
  }

  showAlert(msg: string, tipo: 'success' | 'danger', durationMs: number) {
    this.alertMessage = msg;
    this.alertType = tipo;
    setTimeout(() => this.alertMessage = null, durationMs);
  }
}

