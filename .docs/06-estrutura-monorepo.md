# 06 - Estrutura de Pastas do Monorepo

## Objetivo

Representar a organizacao do repositorio e a finalidade de cada macro-pasta.

## Estrutura revisada

```mermaid
flowchart TD
    ROOT[cms-enterprise-aws]

    ROOT --> GH[.github workflows]
    ROOT --> FEU[cmsaws-frontend-user]
    ROOT --> FEA[cmsaws-frontend-admin]
    ROOT --> LBD[cmsaws-lambdas-java]
    ROOT --> BE[cmsaws-backend-java]
    ROOT --> INF[cmsaws-infra-aws]

    LBD --> L1[cmsaws-lambda-auth]
    LBD --> L2[cmsaws-lambda-home]
    LBD --> L3[cmsaws-lambda-producer]
    LBD --> L4[cmsaws-lambda-article]

    BE --> B1[cmsaws-service-core]
    BE --> B2[cmsaws-service-users]
    BE --> B3[cmsaws-service-contacts]
    BE --> B4[cmsaws-service-forum]
    BE --> B5[cmsaws-worker-forum]

    INF --> I1[terraform]
    INF --> I2[docker]
    INF --> I3[k8s]
```

## Convencoes

- Cada microsservico Java possui migrations Flyway proprias
- Lambdas sao organizadas por responsabilidade funcional
- Infraestrutura como codigo centralizada em `cmsaws-infra-aws`
- CI/CD centralizado em `.github/workflows`

## Proximas evolucoes de documentacao

- ADRs por decisao arquitetural
- Contratos de API por microsservico (OpenAPI)
- Runbooks de incidentes e operacao
- Diagramas de deploy por ambiente (dev/hml/prd)
