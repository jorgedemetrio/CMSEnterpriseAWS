# cmsaws-backend-java

Projeto backend Java (Spring Boot 3.3.5 / Java 21) com multiplos servicos Maven.

## Como rodar

### 1) Subir dependencias locais

No diretorio `cmsaws-infra-aws/docker`:

```bash
docker compose -f docker-compose.local-deps.yml up -d
```

### 2) Build e testes

No diretorio `cmsaws-backend-java`:

```bash
mvn -B clean test
```

### 3) Rodar servicos localmente (recomendado)

Use um banco separado por servico para evitar conflito de checksum do Flyway (`V1__init` diferente por modulo).

```bash
# core
cd cmsaws-service-core
set SERVER_PORT=18080
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_core
mvn -B spring-boot:run

# users
cd ../cmsaws-service-users
set SERVER_PORT=18081
set CORE_API_URL=http://localhost:18080
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_users
mvn -B spring-boot:run

# contacts
cd ../cmsaws-service-contacts
set SERVER_PORT=18082
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_contacts
mvn -B spring-boot:run

# forum
cd ../cmsaws-service-forum
set SERVER_PORT=18083
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_forum
set KAFKA_BOOTSTRAP_SERVERS=localhost:29092
mvn -B spring-boot:run

# worker
cd ../cmsaws-worker-forum
set DB_URL=jdbc:postgresql://localhost:5432/newsflow_worker
set KAFKA_BOOTSTRAP_SERVERS=localhost:29092
mvn -B spring-boot:run
```

## Deploy

### Build de artefatos

```bash
mvn -B clean package
```

### Containerizacao (exemplo por servico)

```bash
cd cmsaws-service-core
docker build -t cmsaws-service-core:latest .
```

### Kubernetes (manifests no monorepo)

```bash
kubectl apply -f ../cmsaws-infra-aws/k8s/namespace.yaml
kubectl apply -f ../cmsaws-infra-aws/k8s/secrets/
kubectl apply -f ../cmsaws-infra-aws/k8s/deployments/
kubectl apply -f ../cmsaws-infra-aws/k8s/services/
kubectl apply -f ../cmsaws-infra-aws/k8s/ingress/
```

## Health checks

```bash
curl http://localhost:18080/actuator/health
curl http://localhost:18081/actuator/health
curl http://localhost:18082/actuator/health
curl http://localhost:18083/actuator/health
```
