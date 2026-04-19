# cmsaws-infra-aws

Infraestrutura local e manifests de deploy.

## Como rodar (local)

No diretorio `cmsaws-infra-aws/docker`:

```bash
docker compose -f docker-compose.local-deps.yml up -d
docker compose -f docker-compose.local-deps.yml ps
```

Derrubar:

```bash
docker compose -f docker-compose.local-deps.yml down
```

Derrubar com volumes:

```bash
docker compose -f docker-compose.local-deps.yml down -v
```

## Deploy Kubernetes

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/secrets/
kubectl apply -f k8s/deployments/
kubectl apply -f k8s/services/
kubectl apply -f k8s/ingress/
```

## Ingress

Hosts configurados:
- `user.dev.local`
- `admin.dev.local`
- `api.dev.local`

## Observacoes

- Ajuste secrets e imagens antes de aplicar em ambientes nao-locais.
- Terraform/Bicep (se aplicavel) deve ser executado no pipeline oficial de infraestrutura.
