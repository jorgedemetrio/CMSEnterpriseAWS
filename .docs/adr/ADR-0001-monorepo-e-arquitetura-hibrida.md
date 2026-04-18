# ADR-0001 - Monorepo e arquitetura hibrida

- Status: Aceito
- Data: 2026-04-18

## Contexto

O projeto possui multiplos frontends, lambdas e microsservicos backend com releases coordenados.

## Decisao

Adotar monorepo e arquitetura hibrida combinando comunicacao sincrona e assincrona.

## Consequencias

- Pro: governanca centralizada e evolucao coordenada
- Pro: melhor desacoplamento para cargas pesadas
- Contra: pipeline e ownership exigem disciplina maior
