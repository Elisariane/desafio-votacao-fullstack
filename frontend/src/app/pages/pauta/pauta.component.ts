import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PautaService } from '../../services/pauta.service';
import { IPauta } from '../../../interfaces/IPauta';
import { AlertComponent } from '../../shared/alert/alert.component';

@Component({
  selector: 'app-pauta',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    AlertComponent
  ],
  templateUrl: './pauta.component.html',
  styleUrl: './pauta.component.css'
})
export class PautaComponent implements OnInit {

  pautas: IPauta[] = [];
  showModal = false;
  novoTitulo = '';
  novaDescricao = '';
  alertMessage: string | null = null;
  alertType: 'success' | 'danger' = 'success';

  constructor(private pautaService: PautaService) {}

  ngOnInit() {
    this.carregarPautas();
  }

  carregarPautas() {
    this.pautaService.listar().subscribe({
      next: (dados) => this.pautas = dados,
      error: (erro) => console.error('Erro ao buscar pautas:', erro)
    });
  }

  criarPauta() {
    this.showModal = true;
  }

  fecharModal() {
    this.showModal = false;
    this.novoTitulo = '';
    this.novaDescricao = '';
  }

  salvarPauta() {
    const novaPauta: IPauta = {
      titulo: this.novoTitulo,
      descricao: this.novaDescricao
    };

    this.pautaService.criar(novaPauta).subscribe({
      next: (pautaCriada) => {
        this.pautas.push(pautaCriada);
        this.fecharModal();
        this.showAlert('Pauta criada com sucesso!', 'success', 3000);
      },
      error: (erro) => {
        const mensagens = erro?.error?.mensagens || ['Erro ao salvar pauta'];
        this.showAlert(mensagens.join('\n'), 'danger', 5000);
      }
    });
  }

  private showAlert(msg: string, type: 'success'|'danger', durationMs: number) {
    this.alertMessage = msg;
    this.alertType = type;
    setTimeout(() => this.alertMessage = null, durationMs + 100); 
  }

}
