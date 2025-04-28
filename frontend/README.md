# Sistema de Votação Cooperativa - Frontend

Este projeto é o frontend de uma aplicação de Votação para Cooperativas, construído com Angular 17, Bootstrap 5 e melhores práticas de desenvolvimento moderno.

## 📋 Funcionalidades

- Cadastro de Pautas

- Cadastro de Associados

- Abertura de Sessões de Votação

- Registro de Votos

- Apuração de Resultados

- Mensagens dinâmicas de sucesso e erro

- Interface responsiva e amigável

## 🛠️ Tecnologias

- Angular 17 (Standalone Components)

- Bootstrap 5

- TypeScript

- HTML5 + CSS3

- RxJS (Observables)

- Ngx-Mask para máscaras de CPF

- FontAwesome para ícones sociais (GitHub e LinkedIn)

## 📁 Estrutura de Pastas

```
/src/app
  ├── pages
  │   ├── associado
  │   ├── pauta
  │   ├── sessao
  │   ├── votacao
  │   ├── resultado
  ├── services
  │   ├── associado.service.ts
  │   ├── pauta.service.ts
  │   ├── sessao.service.ts
  │   ├── votacao.service.ts
  │   ├── resultado.service.ts
  ├── shared
  │   ├── alert (component de alerta global)
  │   ├── pipes (pipe para formatação de CPF)
  ├── interfaces
```

## 🚀 Como Rodar o Frontend

- Instale as dependências:

``` shell
npm install
```

- Rode o projeto localmente:

```shell
npm start
```

- Acesse:

    http://localhost:4200

⚠️ Importante: O backend deve estar rodando na porta 8080, conforme URLs configuradas nos serviços (http://localhost:8080).

## 🔥 Diferenciais de UX/UI

- Alertas posicionados no canto superior direito.

- Feedback visual nos botões "Sim" e "Não" ao votar.

- Modal de resultados com barras de progresso indicando percentual de votos.

- Mensagens amigáveis em todas as páginas.

- Footer fixo com link para GitHub e LinkedIn.

---

##### 👩‍💻 Desenvolvido por Feito com ☕ e ❤️ por Elisariane Barbosa