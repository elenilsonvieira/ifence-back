-- Cria banco e usuário de desenvolvimento para o IFence
-- Execute com um superusuário (por exemplo, postgres):
--   psql -U postgres -f ./sql/dev/init-postgres.sql

DO $$
BEGIN
   IF NOT EXISTS (
       SELECT FROM pg_roles WHERE rolname = 'dev_ifence'
   ) THEN
      CREATE ROLE dev_ifence LOGIN PASSWORD 'dev_ifence123';
   END IF;
END
$$;

DO $$
BEGIN
   IF NOT EXISTS (
       SELECT FROM pg_database WHERE datname = 'ifence'
   ) THEN
      CREATE DATABASE ifence OWNER dev_ifence;
   END IF;
END
$$;

GRANT ALL PRIVILEGES ON DATABASE ifence TO dev_ifence;
