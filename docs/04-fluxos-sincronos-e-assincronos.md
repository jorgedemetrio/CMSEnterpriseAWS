# 04 - Fluxos Sincronos e Assincronos

## Objetivo

Mapear os fluxos de operacao por tipo de processamento e justificar a escolha arquitetural.

## Matriz de operacoes

| Operacao | Tipo | Caminho da Requisicao | Motivacao |
|---|---|---|---|
| Login | Sincrono | React -> Lambda -> Redis | Velocidade instantanea e baixo custo |
| Leitura de materia | Sincrono | React -> API Gateway -> Fargate | Visualizacao imediata |
| Cadastro de usuario | Assincrono | React -> Lambda -> Kafka -> Worker | Resistir a picos e reduzir perda |
| Avaliacao (estrelas) | Assincrono | React -> Lambda -> Kafka -> Worker | Nao bloquear experiencia do usuario |
| Comentario forum | Assincrono | React -> Lambda -> Kafka -> Worker | Permite moderacao/processamento separado |
| Formulario contato | Assincrono | React -> Fargate -> Kafka -> SES | Entrega confiavel e trilha de auditoria |

## Sequencia - fluxo sincrono (leitura de materia)

```mermaid
sequenceDiagram
    actor U as Usuario
    participant FE as React Portal
    participant GW as API Gateway/WAF
    participant CORE as cmsaws-service-core
    participant DB as PostgreSQL

    U->>FE: Abre materia
    FE->>GW: GET /api/core/articles/{id}
    GW->>CORE: Encaminha requisicao
    CORE->>DB: Consulta artigo e categoria
    DB-->>CORE: Retorna dados
    CORE-->>GW: Payload de resposta
    GW-->>FE: HTTP 200
    FE-->>U: Renderiza conteudo
```

## Sequencia - fluxo assincrono (comentario/forum)

```mermaid
sequenceDiagram
    actor U as Usuario
    participant FE as React Portal
    participant L as Lambda Producer
    participant K as AWS MSK Kafka
    participant W as cmsaws-worker-forum
    participant DB as PostgreSQL

    U->>FE: Envia comentario
    FE->>L: POST evento
    L->>K: Publica mensagem
    L-->>FE: Ack rapido
    FE-->>U: Feedback imediato

    K->>W: Entrega evento
    W->>DB: Persiste/atualiza dados
    DB-->>W: Confirmacao
```

## Criterio de decisao

- Sincrono: quando o valor de negocio depende da resposta em tempo real
- Assincrono: quando ha beneficio em desacoplar, absorver pico e reduzir latencia percebida
