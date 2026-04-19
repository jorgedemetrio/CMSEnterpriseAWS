# cmsaws-service-contacts

Servico de contatos (Spring Boot).

## Rodar local

```bash
set SERVER_PORT=18082
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_contacts
mvn -B spring-boot:run
```

Health:

```bash
curl http://localhost:18082/actuator/health
```

## Build

```bash
mvn -B clean package
```

## Deploy

```bash
docker build -t cmsaws-service-contacts:latest .
```

Publicar imagem e atualizar deployment no Kubernetes/ECS.
