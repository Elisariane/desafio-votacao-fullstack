import { IPauta } from "./IPauta";

export interface ISessaoVotacao {
    id?: number;
    pautaId: number;
    pauta?: IPauta; 
    inicio: Date | null;
    fim: Date | null;
    ativa: boolean;
}