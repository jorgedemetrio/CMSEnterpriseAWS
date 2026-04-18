# 05 - Infraestrutura AWS e Seguranca

## Objetivo

Descrever os blocos de infraestrutura na AWS e os principais controles de seguranca e observabilidade.

## Componentes de infraestrutura

- API Gateway + WAF: protecao de borda
- AWS Lambda: workloads de entrada rapida
- AWS Fargate: execucao dos microsservicos Java
- AWS MSK (Kafka): mensageria assincrona
- Amazon RDS PostgreSQL: persistencia relacional
- ElastiCache Redis: cache e sessao
- S3 + CloudFront: armazenamento e distribuicao de imagens
- CloudWatch: logs, metricas e alarmes

## Topologia de referencia

```mermaid
architecture-beta
    group edge(cloud)[Borda e Acesso]
    group app(server)[Aplicacao]
    group msg(server)[Mensageria]
    group store(database)[Dados e Arquivos]
    group obs(cloud)[Observabilidade]

    service waf(internet)[WAF] in edge
    service apigw(internet)[API Gateway] in edge
    service lambda(cloud)[Lambda] in app
    service fargate(server)[Fargate Services] in app
    service msk(server)[AWS MSK Kafka] in msg
    service rds(database)[RDS PostgreSQL] in store
    service redis(database)[ElastiCache Redis] in store
    service s3(disk)[S3] in store
    service cf(cloud)[CloudFront] in store
    service cw(cloud)[CloudWatch] in obs

    waf:R --> L:apigw
    apigw:R --> L:fargate
    apigw:B --> T:lambda
    lambda:R --> L:redis
    lambda:R --> L:msk
    fargate:R --> L:rds
    fargate:R --> L:s3
    cf:L --> R:s3
    msk:R --> L:fargate
    fargate:R --> L:cw
    lambda:R --> L:cw
```

## Controles de seguranca

- Nenhum frontend com acesso direto ao banco
- Endpoints protegidos na borda com WAF
- Segredos fora do codigo (Secrets Manager/Parameter Store)
- IAM Roles para acesso de servicos AWS sem chave fixa

## Escalabilidade e resiliencia

- Auto Scaling de tasks no Fargate por CPU/memoria
- Buffer de eventos no Kafka para absorver picos
- Redis para reduzir latencia em leituras frequentes
