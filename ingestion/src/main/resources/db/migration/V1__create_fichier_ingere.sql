CREATE SCHEMA IF NOT EXISTS electrix;

CREATE TABLE IF NOT EXISTS electrix.file_ingestion
(
  id          BIGSERIAL PRIMARY KEY,
  filename    VARCHAR(500)             NOT NULL,
  type        VARCHAR(100)             NOT NULL,
  ingested_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  row_count   INTEGER,
  status      VARCHAR(50)              NOT NULL DEFAULT 'SUCCESS'
);
