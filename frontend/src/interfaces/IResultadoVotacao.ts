export interface IResultadoVotacao {
    pautaId: number;
    totalSim: number;
    totalNao: number;
    resultadoVotacao: 'APROVADO' | 'REJEITADO' | 'EMPATE';
}