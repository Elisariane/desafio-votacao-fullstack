import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PautaService } from '../../services/pauta.service';
import { IPauta } from '../../../interfaces/IPauta';

@Component({
  selector: 'app-pauta',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pauta.component.html',
  styleUrl: './pauta.component.css'
})
export class PautaComponent implements OnInit {

  pautas: IPauta[] = [];
  showModal = false;
  novoTitulo = '';
  novaDescricao = '';

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
      },
      error: (erro) => console.error('Erro ao salvar pauta:', erro)
    });
  }

}
