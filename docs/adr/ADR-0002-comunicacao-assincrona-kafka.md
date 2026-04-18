# ADR-0002 - Comunicacao assincrona com Kafka

- Status: Aceito
- Data: 2026-04-18

## Contexto

Operacoes de comentario, avaliacao e eventos de cadastro podem sofrer picos de carga.

## Decisao

Usar Kafka (AWS MSK) para desacoplar producao e consumo de eventos.

## Consequencias

- Pro: resiliencia em picos e melhor throughput
- Pro: possibilidade de reprocessamento de eventos
- Contra: maior complexidade operacional e observabilidade distribuida
