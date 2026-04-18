# Runbook de Operacao Inicial

## Objetivo

Padronizar a subida local do ambiente e os passos de diagnostico inicial.

## Pre-requisitos

- Docker Desktop ativo
- Java 21
- Maven 3.9+
- Node.js 20+
- npm
- kubectl (opcional para fluxo k8s local)

## Passo a passo local

### 1. Dependencias de infraestrutura

No diretorio cmsaws-infra-aws/docker:

```shell
docker compose -f docker-compose.local-deps.yml up -d
```

Verificacao:

```shell
docker compose -f docker-compose.local-deps.yml ps
```

### 2. Backend

No diretorio cmsaws-backend-java:

```shell
mvn -B clean test
```

### 3. Frontend usuario

No diretorio cmsaws-frontend-user:

```shell
npm.cmd ci
npm.cmd run dev
```

### 4. E2E usuario

No diretorio cmsaws-frontend-user:

```shell
npm.cmd run test:e2e
```

## Diagnostico rapido

### Docker daemon indisponivel

Sintoma: falha em docker compose com erro de pipe/engine.

Acao:
- iniciar Docker Desktop
- repetir docker compose

### Porta ocupada

Sintoma: bind de porta falha (5432, 6379, 9092, 9200, 5173)

Acao:
- encerrar processo local na porta
- ou ajustar mapeamento no compose

### API indisponivel na Home

Sintoma: frontend usuario mostra mensagem de erro de carregamento.

Acao:
- validar se backend-core esta ativo
- validar endpoint /api/core/articles
- validar VITE_API_BASE_URL no frontend
