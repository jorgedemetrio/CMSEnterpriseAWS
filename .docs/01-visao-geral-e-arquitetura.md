# 01 - Visao Geral e Arquitetura

## Objetivo

Documentar a arquitetura macro do projeto CMS Enterprise AWS (cmsaws), com foco em separacao de responsabilidades, escalabilidade e seguranca.

## Modelo arquitetural

- Monorepo para versionamento coordenado
- Microsservicos hibridos (sincrono + assincrono)
- Camada serverless para entrada rapida
- Processamento core em Java 21 no Fargate
- Kafka para desacoplamento de workloads assincronos

## Arquitetura de alto nivel

```mermaid
architecture-beta
    group edge(cloud)[Camada Edge e Interface]
    group core(server)[Camada Core - Fargate]
    group data(database)[Camada de Dados]
    group obs(cloud)[Observabilidade]

    service portal(server)[React Portal Usuario] in edge
    service admin(server)[React Admin] in edge
    service lambdas(cloud)[AWS Lambda] in edge
    service apigw(internet)[API Gateway + WAF] in edge

    service svc_core(server)[cmsaws-service-core] in core
    service svc_users(server)[cmsaws-service-users] in core
    service svc_contacts(server)[cmsaws-service-contacts] in core
    service svc_forum(server)[cmsaws-service-forum] in core
    service worker_forum(server)[cmsaws-worker-forum] in core
    service kafka(server)[AWS MSK Kafka] in core

    service postgres(database)[PostgreSQL RDS] in data
    service redis(database)[Redis ElastiCache] in data
    service s3(disk)[S3 + CloudFront] in data

    service cloudwatch(cloud)[CloudWatch] in obs

    portal:R --> L:apigw
    admin:R --> L:apigw
    portal:B --> T:lambdas
    apigw:R --> L:svc_core
    apigw:R --> L:svc_users
    apigw:R --> L:svc_contacts
    apigw:R --> L:svc_forum

    lambdas:B --> T:redis
    lambdas:R --> L:kafka

    svc_core:B --> T:postgres
    svc_users:B --> T:postgres
    svc_contacts:B --> T:postgres
    svc_forum:B --> T:postgres
    svc_core:R --> L:s3

    kafka:R --> L:worker_forum
    worker_forum:B --> T:postgres

    svc_core:R --> L:cloudwatch
    svc_users:R --> L:cloudwatch
    svc_contacts:R --> L:cloudwatch
    svc_forum:R --> L:cloudwatch
    worker_forum:R --> L:cloudwatch
```

## Decisao de comunicacao

- Sincrono quando ha necessidade de resposta imediata ao usuario
- Assincrono quando a operacao pode ser desacoplada sem impacto de UX
