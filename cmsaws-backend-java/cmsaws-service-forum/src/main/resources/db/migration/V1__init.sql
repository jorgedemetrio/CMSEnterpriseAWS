CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS forum_topics (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title VARCHAR(200) NOT NULL,
  status_dado INTEGER NOT NULL DEFAULT 1,
  datahora_criado TIMESTAMP
);

CREATE TABLE IF NOT EXISTS forum_posts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  topic_id UUID NOT NULL REFERENCES forum_topics(id),
  author_name VARCHAR(150) NOT NULL,
  content TEXT NOT NULL,
  status_dado INTEGER NOT NULL DEFAULT 1,
  datahora_criado TIMESTAMP
);