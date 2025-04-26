import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { AlertComponent } from '../../shared/alert/alert.component';
import { formatDate } from '@angular/common';
import { ISessaoVotacao } from '../../../interfaces/ISessaoVotacao';
import { SessaoService } from '../../services/sessao.service';
import { IPauta } from '../../../interfaces/IPauta';
import { PautaService } from '../../services/pauta.service';

@Component({
  selector: 'app-votacao',
  standalone: true,
  imports: [CommonModule, FormsModule, AlertComponent],
  templateUrl: './sessao.component.html',
  styleUrl: './sessao.component.css'
})
export class SessaoComponent implements OnInit {

  sessoes: ISessaoVotacao[] = [];
  pautas: IPauta[] = [];
  showModal = false;
  pautaId: number | null = null;
  inicio: string | null = null;
  fim: string | null = null;
  ativa: boolean = false;
  alertMessage: string | null = null;
  alertType: 'success' | 'danger' = 'success';

  constructor(
    private sessaoService: SessaoService,
    private pautaService: PautaService
  ) {}

  ngOnInit() {
    this.carregarPautas();
    this.carregarSessoes();
  }

  carregarSessoes() {
    this.sessaoService.listar().subscribe({
      next: (dados) => this.sessoes = dados,
      error: (erro) => this.showAlert('Erro ao buscar sessões', 'danger', 5000)
    });
  }

  carregarPautas() {
    this.pautaService.listar().subscribe({
      next: (res) => this.pautas = res,
      error: (err) => this.showAlert('Erro ao buscar pautas', 'danger', 5000)
    });
  }

  criarSessao() {
    this.showModal = true;
  }

  fecharModal() {
    this.showModal = false;
    this.pautaId = null;
    this.inicio = '';
    this.fim = '';
    this.ativa = false;
  }

  salvarSessao(form: NgForm) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    const sessaoVotacao: ISessaoVotacao = {
      pautaId: this.pautaId!,
      inicio: this.inicio ? new Date(this.inicio) : null,
      fim: this.fim ? new Date(this.fim) : null,
      ativa: this.ativa
    };

    this.sessaoService.criar(sessaoVotacao).subscribe({
      next: (res) => {
        this.sessoes.push(res);
        this.fecharModal();
        this.showAlert('Sessão criada com sucesso!', 'success', 3000);
      },
      error: (erro) => {
        this.fecharModal();
        const mensagens = erro?.error?.mensagens || ['Erro ao salvar sessão de votação...'];
        this.showAlert(mensagens.join('\n'), 'danger', 5000);
      }
    });
  }

  private showAlert(msg: string, type: 'success' | 'danger', durationMs: number) {
    this.alertMessage = msg;
    this.alertType = type;
    setTimeout(() => this.alertMessage = null, durationMs);
  }

  formatarData(data: string | Date | null): string {
    if (!data) return 'Não informado';
    return formatDate(data, 'dd/MM/yyyy - HH:mm', 'pt-BR');
  }

  get sessoesAtivas() {
    return this.sessoes.filter(s => s.ativa);
  }

  get sessoesEncerradas() {
    return this.sessoes.filter(s => !s.ativa);
  }

}
