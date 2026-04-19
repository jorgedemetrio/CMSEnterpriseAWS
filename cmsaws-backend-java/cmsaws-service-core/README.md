# cmsaws-service-core

Servico core (Spring Boot).

## Rodar local

```bash
set SERVER_PORT=18080
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_core
mvn -B spring-boot:run
```

Health:

```bash
curl http://localhost:18080/actuator/health
```

## Build

```bash
mvn -B clean package
```

## Deploy

```bash
docker build -t cmsaws-service-core:latest .
```

Publicar imagem e atualizar deployment no Kubernetes/ECS conforme ambiente.
