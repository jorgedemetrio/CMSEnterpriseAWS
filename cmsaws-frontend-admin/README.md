# cmsaws-frontend-admin

Frontend administrativo.

## Como rodar

No diretorio `cmsaws-frontend-admin`:

```bash
npm.cmd ci
npm.cmd run dev
```

## Build

```bash
npm.cmd run build
```

## Deploy

### Artefato estatico

Publicar o conteudo de `dist/` no host/CDN do ambiente.

### Exemplo com container

```bash
docker build -t cmsaws-frontend-admin:latest .
```

Depois publicar no registry e atualizar deployment Kubernetes/ECS.
