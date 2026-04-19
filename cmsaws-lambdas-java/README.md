# cmsaws-lambdas-java

Projeto Maven multi-modulo com Lambdas Java.

## Como rodar

### Build de todos os modulos

No diretorio `cmsaws-lambdas-java`:

```bash
mvn -B clean verify
```

### Build de um modulo especifico

```bash
mvn -B -pl cmsaws-lambda-auth -am clean package
```

## Deploy

Cada lambda gera um JAR em `target/`. O deploy pode ser feito via pipeline CI/CD ou AWS CLI.

### Exemplo (AWS CLI)

```bash
aws lambda update-function-code \
  --function-name cmsaws-lambda-auth \
  --zip-file fileb://cmsaws-lambda-auth/target/cmsaws-lambda-auth-0.1.0-SNAPSHOT.jar
```

Repita para os outros modulos:
- `cmsaws-lambda-home`
- `cmsaws-lambda-article`
- `cmsaws-lambda-producer`

## Observacoes

- Em ambiente real, prefira deploy versionado por pipeline.
- Valide variaveis de ambiente e IAM antes de publicar.
