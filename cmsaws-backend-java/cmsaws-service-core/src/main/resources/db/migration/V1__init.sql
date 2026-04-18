CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS categories (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(120) NOT NULL UNIQUE,
  status_dado INTEGER NOT NULL DEFAULT 1,
  status_conteudo INTEGER NOT NULL DEFAULT 1,
  datahora_publicacao TIMESTAMP,
  datahora_despublicar TIMESTAMP
);

CREATE TABLE IF NOT EXISTS articles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title VARCHAR(200) NOT NULL,
  content TEXT NOT NULL,
  category_id UUID NOT NULL REFERENCES categories(id),
  is_highlight BOOLEAN NOT NULL DEFAULT FALSE,
  status_dado INTEGER NOT NULL DEFAULT 1,
  status_conteudo INTEGER NOT NULL DEFAULT 1,
  datahora_publicacao TIMESTAMP,
  datahora_despublicar TIMESTAMP
);