# cmsaws-lambda-auth

Lambda Java de autenticacao.

## Rodar build local

```bash
mvn -B clean package
```

## Deploy

```bash
aws lambda update-function-code \
  --function-name cmsaws-lambda-auth \
  --zip-file fileb://target/cmsaws-lambda-auth-0.1.0-SNAPSHOT.jar
```
