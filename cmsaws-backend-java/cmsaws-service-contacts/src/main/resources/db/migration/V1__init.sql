CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS departments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(120) NOT NULL UNIQUE,
  status_dado INTEGER NOT NULL DEFAULT 1,
  status_conteudo INTEGER NOT NULL DEFAULT 1,
  datahora_publicacao TIMESTAMP,
  datahora_despublicar TIMESTAMP
);

CREATE TABLE IF NOT EXISTS contact_messages (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  department_id UUID NOT NULL REFERENCES departments(id),
  name VARCHAR(150) NOT NULL,
  email VARCHAR(200) NOT NULL,
  message TEXT NOT NULL,
  status_dado INTEGER NOT NULL DEFAULT 1,
  datahora_criado TIMESTAMP
);