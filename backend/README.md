# 🗳️ Sistema de Votação para Cooperativas

Este é um sistema de votação desenvolvido para cooperativas, permitindo que pautas sejam criadas, sessões de votação sejam abertas e os associados possam votar de forma simples e segura. Foi desenvolvido como parte de um desafio técnico utilizando Java com Spring Boot e banco de dados PostgreSQL.

A aplicação contempla:
- Cadastro e listagem de **pautas** e **associados**
- Abertura de **sessões de votação** com tempo padrão ou customizado
- Registro de **votos únicos por pauta e associado**
- **Validação de CPF** por meio de uma API externa (fake)
- Apuração automática do **resultado da votação**
- **Swagger UI** para documentação e testes manuais
- Testes unitários e de integração


  ## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot 3
    - Spring Web
    - Spring Data JPA
    - Bean Validation
- PostgreSQL
- Docker & Docker Compose
- OpenAPI / Swagger
- JUnit 5 & Mockito

## ⚙️ Como executar o projeto

### Pré-requisitos

- Java 21 instalado
- Maven instalado
- Docker e Docker Compose

### 1. Clone o repositório

```shell
git clone https://github.com/Elisariane/desafio-votacao-fullstack.git
cd desafio-votacao-fullstack/backend 
```

### 2. Suba o banco de dados com Docker

```shell
docker-compose up -d
```
Isso irá iniciar um container PostgreSQL acessível em localhost:5433.

### 3. Execute a aplicação

```shell
./mvnw spring-boot:run
```
A API estará disponível em: http://localhost:8080

### 4. Documentação interativa (Swagger)

Acesse: http://localhost:8080/swagger-ui/index.html


# 🧠 Regras de negócio implementadas

#### 🗳️ Pauta
- Uma pauta possui título obrigatório e descrição opcional.
- Pode ser associada a várias sessões de votação.

#### ⏱️ Sessão de Votação
- Está vinculada a uma pauta existente.
- Ao abrir uma sessão:
    - Caso a data de início não seja informada, assume o horário atual.
    - Caso a data de fim não seja informada, dura 1 minuto por padrão.
- Não permite abrir uma nova sessão para uma pauta que já possui sessão ativa.
- É considerada **ativa** se a hora atual estiver entre o `início` e `fim`.

#### 👤 Associado
- Possui nome e CPF válidos.
- O CPF é único e validado via API externa mockada.

#### ✅ Voto
- Um associado pode votar **apenas uma vez por pauta**.
- Só é possível votar se a sessão estiver ativa.
- O voto só é computado se o CPF do associado estiver autorizado pela API externa.


# 🧪 Testes Automatizados

Foram criados testes unitários e de controller com cobertura completa para as principais camadas da aplicação, garantindo robustez das regras de negócio e tratamento de erros.

#### ✅ Testes criados:

- **PautaService / PautaController**
    - Criação de pauta com dados válidos
    - Validação de título obrigatório
- **SessaoVotacaoService / SessaoVotacaoController**
    - Abertura de sessão com e sem datas informadas
    - Impede reabertura de sessão ativa para a mesma pauta
- **AssociadoService / AssociadoController**
    - Cadastro com CPF válido
    - Bloqueia CPF inválido via mock de API externa
- **VotoService / VotoController**
    - Voto com sessão ativa e CPF autorizado
    - Bloqueia voto duplicado
    - Bloqueia voto em sessão encerrada
    - Bloqueia CPF não autorizado

Os testes utilizam JUnit 5, Mockito e MockMvc, com respostas documentadas no Swagger e validação detalhada das regras de negócio.

# 📊 Contabilização de Votos & Endpoint de Resultado

#### Lógica de apuração

- Dada uma **pauta**.
- Conta-se, via repositório Spring-Data:
  - votos “SIM”:  
    `countBySessao_Pauta_IdAndTipoVoto(pautaId, TipoVoto.SIM)`
  - votos “NÃO”:  
    `countBySessao_Pauta_IdAndTipoVoto(pautaId, TipoVoto.NAO)`
- Define-se o veredito:
  - `totalSim > totalNao` → **APROVADO**
  - `totalNao > totalSim` → **REJEITADO**
  - caso contrário → **EMPATE**

#### Endpoint

| Método | URI                     | Descrição                            | Retorno                          |
| ------ | ----------------------- | ------------------------------------ | -------------------------------- |
| GET    | `/v1/resultados/{id}`   | Apura resultado da pauta `id`        | `200 OK` + `ResultadoVotacaoDto` |
|        |                         |                                      | `404 Not Found` se pauta inválida|

##### Exemplo de requisição

```bash
curl -X GET http://localhost:8080/v1/resultados/42

Exemplo de resposta 200 OK

{
  "pautaId": 42,
  "totalSim": 15,
  "totalNao": 7,
  "resultadoVotacao": "APROVADO"
}

Exemplo de resposta 404 Not Found

{
  "timestamp": "2025-04-25T14:30:12.345Z",
  "status": 404,
  "error": "Entidade não encontrada",
  "path": "/v1/resultados/99",
  "mensagens": ["Pauta não encontrada"]
}
```
