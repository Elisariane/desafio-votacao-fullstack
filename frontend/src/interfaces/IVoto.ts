import { IAssociado } from "./IAssociado";
import { ISessaoVotacao } from "./ISessaoVotacao";

export interface IVoto {
    associadoId: number;
    associado?: IAssociado;
    sessaoId: number;
    sessaoVotacao?: ISessaoVotacao;
    voto: 'SIM' | 'NAO';
}