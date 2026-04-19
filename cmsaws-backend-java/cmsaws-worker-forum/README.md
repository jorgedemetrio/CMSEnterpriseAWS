# cmsaws-worker-forum

Worker de consumo de eventos Kafka do forum.

## Rodar local

```bash
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_worker
set KAFKA_BOOTSTRAP_SERVERS=localhost:29092
mvn -B spring-boot:run
```

## Build

```bash
mvn -B clean package
```

## Deploy

```bash
docker build -t cmsaws-worker-forum:latest .
```

Publicar imagem e atualizar deployment no Kubernetes/ECS.

## Validacao E2E

1. Criar topico e post em `cmsaws-service-forum`.
2. Verificar registros em `forum_event_consumptions` no banco `newsflow_worker`.
