# cmsaws-lambda-article

Lambda Java para dominio de artigos.

## Rodar build local

```bash
mvn -B clean package
```

## Deploy

```bash
aws lambda update-function-code \
  --function-name cmsaws-lambda-article \
  --zip-file fileb://target/cmsaws-lambda-article-0.1.0-SNAPSHOT.jar
```
