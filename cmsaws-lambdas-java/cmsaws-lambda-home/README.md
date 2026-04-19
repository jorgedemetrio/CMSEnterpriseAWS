# cmsaws-lambda-home

Lambda Java para funcionalidades da home.

## Rodar build local

```bash
mvn -B clean package
```

## Deploy

```bash
aws lambda update-function-code \
  --function-name cmsaws-lambda-home \
  --zip-file fileb://target/cmsaws-lambda-home-0.1.0-SNAPSHOT.jar
```
