# cmsaws-frontend-user

Frontend do portal de usuario.

## Como rodar

No diretorio `cmsaws-frontend-user`:

```bash
npm.cmd ci
npm.cmd run dev
```

App local normalmente em `http://localhost:5173`.

## Build

```bash
npm.cmd run build
```

## Testes E2E

```bash
npm.cmd run test:e2e
```

## Deploy

### Artefato estatico

Gerar build e publicar o conteudo de `dist/` no host/CDN escolhido.

### Exemplo com container

```bash
docker build -t cmsaws-frontend-user:latest .
```

Depois publicar no registry e atualizar deployment Kubernetes/ECS conforme estrategia do ambiente.
