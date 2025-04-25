import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { IAssociado } from '../../../interfaces/IAssociado';
import { AssociadoService } from '../../services/associado.service';
import { AlertComponent } from '../../shared/alert/alert.component';
import { CpfFormatPipe } from '../../shared/pipes/cpf-format.pipe';

@Component({
  selector: 'app-associado',
  imports: [
    AlertComponent,
    CpfFormatPipe,
    CommonModule,
    FormsModule
  ],
  templateUrl: './associado.component.html',
  styleUrl: './associado.component.css'
})
export class AssociadoComponent implements OnInit {

  associados: IAssociado[] = [];
  showModal = false;
  novoNome = '';
  novoCpf = '';
  alertMessage: string | null = null;
  alertType: 'success' | 'danger' = 'success';

  constructor(private associadoService: AssociadoService) {}

  ngOnInit() {
    this.carregarAssociados();
  }

  carregarAssociados() {
    this.associadoService.listar().subscribe({
      next: (dados) => this.associados = dados,
      error: (erro) => console.error('Erro ao buscar associados:', erro)
    });
  }

  criarAssociado() {
    this.showModal = true;
  }

  fecharModal() {
    this.showModal = false;
    this.novoNome = '';
    this.novoCpf = '';
  }

  salvarAssociado(form: NgForm) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }
  
    const novoAssociado: IAssociado = {
      nome: this.novoNome,
      cpf: this.novoCpf
    };
  
    this.associadoService.criar(novoAssociado).subscribe({
      next: (res) => {
        this.associados.push(res);
        this.fecharModal();
        this.showAlert('Associado criado com sucesso!', 'success', 3000);
      },
      error: (erro) => {
        this.fecharModal();
        const mensagens = erro?.error?.mensagens || ['Erro ao salvar associado'];
        this.showAlert(mensagens.join('\n'), 'danger', 5000);
      }
    });
  }
  
  private showAlert(msg: string, type: 'success'|'danger', durationMs: number) {
    this.alertMessage = msg;
    this.alertType = type;
    setTimeout(() => this.alertMessage = null, durationMs + 100); 
  }

  isCpfValido(cpf: string): boolean {
    if (!cpf) return false;
  
    // Remove tudo que não for número
    cpf = cpf.replace(/\D/g, '');
  
    if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) return false;
  
    let soma = 0;
    for (let i = 0; i < 9; i++) soma += +cpf[i] * (10 - i);
    let dig1 = 11 - (soma % 11);
    dig1 = dig1 > 9 ? 0 : dig1;
  
    soma = 0;
    for (let i = 0; i < 10; i++) soma += +cpf[i] * (11 - i);
    let dig2 = 11 - (soma % 11);
    dig2 = dig2 > 9 ? 0 : dig2;
  
    return dig1 === +cpf[9] && dig2 === +cpf[10];
  }
  
}