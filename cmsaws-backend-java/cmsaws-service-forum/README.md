# cmsaws-service-forum

Servico de forum (Spring Boot) com produtor Kafka.

## Rodar local

```bash
set SERVER_PORT=18083
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_forum
set KAFKA_BOOTSTRAP_SERVERS=localhost:29092
mvn -B spring-boot:run
```

Health:

```bash
curl http://localhost:18083/actuator/health
```

## Build

```bash
mvn -B clean package
```

## Deploy

```bash
docker build -t cmsaws-service-forum:latest .
```

Publicar imagem e atualizar deployment no Kubernetes/ECS.
