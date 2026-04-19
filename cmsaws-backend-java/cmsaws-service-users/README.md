# cmsaws-service-users

Servico de usuarios (Spring Boot, Feign e Redis cache).

## Rodar local

```bash
set SERVER_PORT=18081
set CORE_API_URL=http://localhost:18080
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_users
set REDIS_HOST=localhost
set REDIS_PORT=6379
mvn -B spring-boot:run
```

Health:

```bash
curl http://localhost:18081/actuator/health
```

## Build

```bash
mvn -B clean package
```

## Deploy

```bash
docker build -t cmsaws-service-users:latest .
```

Publicar imagem e atualizar deployment no Kubernetes/ECS.
