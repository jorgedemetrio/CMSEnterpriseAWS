# Runbook Operacional Inicial

## Objetivo

Fornecer passos operacionais para build, deploy, rollback e troubleshooting inicial do monorepo CMS Enterprise AWS.

## 1. Build e Testes

### Backend Java (todos os modulos)

```powershell
cd cmsaws-backend-java
mvn -B clean test
```

### Lambdas Java

```powershell
cd cmsaws-lambdas-java
mvn -B clean verify
```

### Frontend user

```powershell
cd cmsaws-frontend-user
npm ci
npm run build
```

### Frontend admin

```powershell
cd cmsaws-frontend-admin
npm ci
npm run build
```

## 2. Deploy (alto nivel)

1. Publicar imagens de containers no registry.
2. Atualizar task definitions ECS/Fargate.
3. Aplicar rollout controlado por servico.
4. Validar health endpoints e logs no CloudWatch.

## 3. Rollback

### Cenarios de rollback

- Falha em healthcheck apos deploy
- Aumento anormal de erro 5xx
- Regressao funcional critica

### Procedimento

1. Reverter task definition para revisao anterior no ECS.
2. Reduzir percentual de trafego da revisao nova para zero.
3. Confirmar estabilidade de latencia/erro.
4. Registrar incidente e causa raiz.

## 4. Troubleshooting

### Sintoma: API com 400 de validacao

- Verificar payload enviado
- Checar campo `details` no retorno para identificar todos os campos invalidos

### Sintoma: API com 404 em delete de usuario

- Confirmar ID informado
- Verificar se registro existe e se nao esta com soft delete

### Sintoma: eventos nao processados no forum

- Verificar lag do consumidor no MSK
- Validar logs do `cmsaws-worker-forum`
- Confirmar conectividade com PostgreSQL

### Sintoma: lentidao em leitura

- Confirmar cache Redis
- Inspecionar latencia no CloudWatch/APM
- Verificar queries de leitura no PostgreSQL

## 5. Checklist rapido pos-deploy

- Health endpoints dos servicos retornando 200
- Taxa de erro 5xx dentro do baseline
- Consumidor Kafka sem lag crescente
- Frontends carregando pagina inicial e fluxos basicos
