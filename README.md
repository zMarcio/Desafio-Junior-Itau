# Desafio Itau — API de Transações e Estatísticas

API RESTful em Java (Spring Boot 3) para registrar transações financeiras em memória e consultar estatísticas agregadas dentro de uma janela deslizante de tempo configurável.

Esta documentação cobre: como obter o projeto, requisitos, como executar, rotas, payloads, códigos de resposta, como testar via Postman/cURL, estrutura do projeto e decisões técnicas.

## Sumário

- Visão geral
- Pré‑requisitos
- Como obter o projeto (git clone / pull)
- Como executar (Maven Wrapper)
- Testes rápidos (Postman e cURL)
- Endpoints da API (contratos)
- Modelos de dados
- Regras de negócio e validações
- Estratégia da janela temporal (estatísticas)
- Estrutura do projeto
- Desenvolvimento e testes
- Próximos passos

## Visão geral

- Armazena transações em memória (sem banco de dados) enquanto a aplicação está em execução.
- Permite configurar a janela deslizante de tempo (em segundos) usada para computar estatísticas.
- Oferece endpoints para criar transações, apagar todas e consultar estatísticas.

Stack principal:
- Java 21
- Spring Boot 3.5.x
- Maven (com Maven Wrapper `mvnw`)
- Jackson (incluindo `jackson-datatype-jsr310` para `OffsetDateTime`)
- Lombok

## Pré‑requisitos

- JDK 21 instalado (ou usar um container/ambiente que já o inclua)
- Git instalado
- Postman (opcional, para testes manuais) ou cURL

Observação: O projeto já inclui o Maven Wrapper, então não é necessário ter Maven instalado.

## Como obter o projeto

1) Clonar o repositório:

```bash
git clone https://github.com/zMarcio/Desafio-Junior-Itau.git
cd Desafio-Junior-Itau
```

2) Se já possui o repositório local e quer atualizar:

```bash
git pull origin main
```

## Como executar

Execute com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou gere o jar e rode:

```bash
./mvnw clean package -DskipTests
java -jar target/Desafio-Itau-0.0.1-SNAPSHOT.jar
```

Aplicação padrão em http://localhost:8080

Configurações em `src/main/resources/application.properties` (porta padrão do Spring Boot 8080).

## Testes rápidos

### Usando Postman (recomendado)

1) Importe a coleção Postman em `docs/Desafio-Itau.postman_collection.json`.
2) Defina a variável de ambiente `baseUrl` para `http://localhost:8080`.
3) Use as requisições da coleção para testar os endpoints.

### Usando cURL

- Criar transação:

```bash
curl -i -X POST "http://localhost:8080/api/transacao" \
	-H "Content-Type: application/json" \
	-d '{"valor": 123.45, "dataHora": "2025-08-14T10:00:00-03:00"}'
```

- Configurar janela de segundos (ex.: 120):

```bash
curl -i -X POST "http://localhost:8080/api/transacao/segundos" \
	-H "Content-Type: application/json" \
	-d '{"segundos": 120}'
```

- Consultar estatística:

```bash
curl -s "http://localhost:8080/api/estatistica" | jq
```

- Apagar todas transações:

```bash
curl -i -X DELETE "http://localhost:8080/api/transacao"
```

## Endpoints da API

Base path: `/api`

1) POST `/api/transacao`
- Descrição: Registra uma transação.
- Request body (JSON):
	- `valor` (number, obrigatório)
	- `dataHora` (string, obrigatório, `OffsetDateTime` ISO‑8601, ex.: `2025-08-14T10:00:00-03:00`)
- Responses:
	- 201 Created (sem corpo): transação aceita e registrada
	- 400 Bad Request: JSON inválido ou campos obrigatórios ausentes/valor inválido (NaN)
	- 422 Unprocessable Entity: violação de regra de negócio (valor não permitido, data no futuro)

Exemplo de requisição:

```json
{
	"valor": 10.50,
	"dataHora": "2025-08-14T17:30:00-03:00"
}
```

2) DELETE `/api/transacao`
- Descrição: Apaga todas as transações da memória.
- Responses:
	- 200 OK (sem corpo)

3) POST `/api/transacao/segundos`
- Descrição: Define a janela deslizante de tempo (em segundos) considerada para o cálculo das estatísticas.
- Request body (JSON): `{ "segundos": <inteiro> }`
- Responses:
	- 201 Created (sem corpo)

Exemplo:

```json
{ "segundos": 120 }
```

4) GET `/api/estatistica`
- Descrição: Retorna estatísticas das transações registradas dentro da janela configurada.
- Response body (JSON):

```json
{
	"contagem": 3,
	"somaTotal": 300.0,
	"media": 100.0,
	"minimo": 50.0,
	"maximo": 150.0
}
```

## Modelos de dados

- `TransacaoModel` (`src/main/java/.../Model/TransacaoModel.java`)
	- `valor` (Double)
	- `dataHora` (OffsetDateTime)
	- `dataTransacao` (OffsetDateTime) — interno, ignorado no JSON

- `EstatisticaModel` (`src/main/java/.../Model/EstatisticaModel.java`)
	- `contagem` (int)
	- `somaTotal` (Double)
	- `media` (Double)
	- `minimo` (Double)
	- `maximo` (Double)

## Regras de negócio e validações

- Regras (implementadas em `TransacaoController` e `TransacaoService`):
	- `valor` e `dataHora` são obrigatórios (senão 400).
	- JSON inválido resulta em 400.
	- `valor` não pode ser `NaN` (senão 400).
	- Transação deve ter ocorrido no passado ou no presente, nunca no futuro (senão 422).
	- Transação deve ter `valor` positivo.

Observação importante: atualmente a implementação aceita apenas `valor > 0` (estritamente maior que zero). Mesmo que a regra desejada fosse ">= 0", o valor zero será rejeitado e resultará em 422. Ajuste futuro é possível se necessário.

## Estratégia da janela temporal (estatísticas)

- Cada transação, quando registrada, recebe um `dataTransacao` com `OffsetDateTime.now()`.
- A estatística considera apenas as transações cujo `dataTransacao` esteja dentro dos últimos `N` segundos configurados via `/api/transacao/segundos` (padrão: 60 segundos).
- Ou seja, a janela é baseada no momento de registro da transação, não no campo `dataHora` enviado no payload.

## Estrutura do projeto

```
src/main/java/com/challenger/itau/Desafio/Itau/
	├─ DesafioItauApplication.java           # Classe principal Spring Boot
	├─ Controller/
	│   └─ TransacaoController.java          # Endpoints da API
	├─ Service/
	│   └─ TransacaoService.java             # Regras de negócio
	├─ Model/
	│   ├─ TransacaoModel.java               # Modelo da transação
	│   ├─ ListaTransacoesModel.java         # Lista em memória + estatísticas
	│   └─ EstatisticaModel.java             # DTO da resposta de estatísticas
	├─ Exception/
	│   ├─ ApiExceptionHandler.java          # Mapeamento de erros para HTTP
	│   └─ TransactionException.java         # Exceção de regras de transação
	└─ Utils/
			└─ ApiResponse.java                  # Estrutura utilitária de resposta

src/main/resources/
	└─ application.properties
```

## Próximos passos (sugestões)

- Ajustar regra para aceitar `valor >= 0` caso essa seja a regra de negócio desejada.
- Basear a janela em `dataHora` (do payload) em vez de `dataTransacao` (momento do registro), se fizer sentido para o caso de uso.
- Adicionar persistência (banco de dados) caso seja necessário manter dados após reinício.
- Documentação OpenAPI/Swagger e integração com Springdoc.
- Testes de unidade e de integração adicionais para bordas (concurrency, clock skew).
