# ADR-0003 - Flyway por microsservico

- Status: Aceito
- Data: 2026-04-18

## Contexto

Cada dominio de negocio evolui em ritmos diferentes e precisa de rastreabilidade de schema.

## Decisao

Manter migrations Flyway segregadas por microsservico em `db/migration`.

## Consequencias

- Pro: isolamento de mudancas por contexto de negocio
- Pro: versionamento claro e auditavel
- Contra: necessidade de coordenacao quando houver mudancas transversais
