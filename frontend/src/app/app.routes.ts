import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AssociadoComponent } from './pages/associado/associado.component';
import { SessaoComponent } from './pages/sessao/sessao.component';
import { VotacaoComponent } from './pages/votacao/votacao.component';
import { ResultadoComponent } from './pages/resultado/resultado.component';
import { PautaComponent } from './pages/pauta/pauta.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'pautas', component: PautaComponent },
    { path: 'associados', component: AssociadoComponent },
    { path: 'sessoes', component: SessaoComponent },
    { path: 'votacao', component: VotacaoComponent },
    { path: 'resultado', component: ResultadoComponent },
];
