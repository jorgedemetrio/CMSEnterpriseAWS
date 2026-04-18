# NewsFlow Tech Ecosystem

Monorepo do projeto CMS Enterprise AWS, com separacao por camadas:
- Frontend de usuario e admin
- Backend Java em microsservicos
- Lambdas Java
- Infra local com Docker e Kubernetes

## Documentacao

A documentacao funcional e arquitetural esta em:
- docs
- [docs/README.md](docs/README.md)

## Estrutura principal

- .github/workflows
- cmsaws-frontend-user
- cmsaws-frontend-admin
- cmsaws-backend-java
- cmsaws-lambdas-java
- cmsaws-infra-aws
- docs

## Execucao local rapida

### 1) Subir dependencias locais (Postgres, Redis, Elasticsearch, Kafka, Zookeeper)

No diretorio cmsaws-infra-aws/docker:

```shell
docker compose -f docker-compose.local-deps.yml up -d
```

Verificar status:

```shell
docker compose -f docker-compose.local-deps.yml ps
```

Derrubar:

```shell
docker compose -f docker-compose.local-deps.yml down
```

Derrubar e remover volumes:

```shell
docker compose -f docker-compose.local-deps.yml down -v
```

### 2) Subir stack completa local (opcional)

No diretorio cmsaws-infra-aws/docker:

```shell
docker compose up -d
```

### 3) Executar backend

Na raiz de cmsaws-backend-java:

```shell
mvn -B clean test
```

### 4) Executar frontend usuario

No diretorio cmsaws-frontend-user:

```shell
npm.cmd ci
npm.cmd run dev
```

### 5) Executar testes E2E do frontend usuario

No diretorio cmsaws-frontend-user:

```shell
npm.cmd run test:e2e
```

## Kubernetes local

### Apply dos manifests base

```shell
kubectl apply -f cmsaws-infra-aws/k8s/namespace.yaml
kubectl apply -f cmsaws-infra-aws/k8s/secrets/
kubectl apply -f cmsaws-infra-aws/k8s/deployments/
kubectl apply -f cmsaws-infra-aws/k8s/services/
kubectl apply -f cmsaws-infra-aws/k8s/ingress/
```

Ingress configurado com hosts:
- user.dev.local
- admin.dev.local
- api.dev.local

## Roadmap tecnico (status atual)

- [x] Implementacao inicial de schema e migrations Flyway
- [x] Boilerplate Spring Boot Java 21 para servicos backend
- [x] Setup de Kafka local no Docker Compose
- [~] Home do frontend usuario consumindo API real (falta campo is_highlight no contrato)
- [ ] Fluxos completos de autenticacao e contato no frontend usuario
- [ ] Painel admin com CRUD completo e permissoes
- [ ] Integracao completa producer/consumer Kafka com persistencia de eventos no worker

## Observacoes

- Para um passo a passo completo de onboarding, consulte o guia em docs.
