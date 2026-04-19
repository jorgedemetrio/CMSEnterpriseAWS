# cmsaws-lambda-producer

Lambda Java para producao/publicacao de eventos.

## Rodar build local

```bash
mvn -B clean package
```

## Deploy

```bash
aws lambda update-function-code \
  --function-name cmsaws-lambda-producer \
  --zip-file fileb://target/cmsaws-lambda-producer-0.1.0-SNAPSHOT.jar
```
