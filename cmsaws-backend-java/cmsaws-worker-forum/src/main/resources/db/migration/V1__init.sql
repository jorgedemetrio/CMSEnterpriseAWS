CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS forum_event_consumptions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  event_id UUID NOT NULL UNIQUE,
  event_type VARCHAR(120) NOT NULL,
  aggregate_id UUID NOT NULL,
  payload TEXT NOT NULL,
  status VARCHAR(30) NOT NULL,
  datahora_criado TIMESTAMP NOT NULL DEFAULT now()
);
