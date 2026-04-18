# Documentacao do Projeto CMS Enterprise AWS

Esta pasta concentra a documentacao tecnica do monorepo em Markdown com diagramas Mermaid.

## Indice

- [01 - Visao Geral e Arquitetura](./01-visao-geral-e-arquitetura.md)
- [02 - Camada de Interface (Frontend)](./02-camada-interface-frontend.md)
- [03 - Camada de Backend (Java 21)](./03-camada-backend-java.md)
- [04 - Fluxos Sincronos e Assincronos](./04-fluxos-sincronos-e-assincronos.md)
- [05 - Infraestrutura AWS e Seguranca](./05-infra-aws-seguranca.md)
- [06 - Estrutura de Pastas do Monorepo](./06-estrutura-monorepo.md)

## Documentacao por microsservico

- [service-core](./services/cmsaws-service-core.md)
- [service-users](./services/cmsaws-service-users.md)
- [service-contacts](./services/cmsaws-service-contacts.md)
- [service-forum](./services/cmsaws-service-forum.md)
- [worker-forum](./services/cmsaws-worker-forum.md)

## Decisoes arquiteturais

- [ADRs](./adr/README.md)

## Operacao

- [Runbook operacional inicial](./runbooks/operacao-inicial.md)

## Escopo inicial

- Visao arquitetural de alto nivel
- Separacao de responsabilidades por camada
- Fluxos de requisicao por tipo de operacao
- Base para detalhamento futuro de APIs, contratos e runbooks
