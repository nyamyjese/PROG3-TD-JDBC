-- créer la base de donnée :
CREATE DATABASE product_management_db;

-- créer l'utilisateur :
CREATE USER product_manager_user WITH PASSWORD '123456';

-- privilège à l'utilisateur sur la base de donnée :
GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;

-- permettre à l'utilisateur de faire les CRUD sur les tables :
GRANT SELECT , INSERT , UPDATE , DELETE ON ALL TABLES IN SCHEMA public TO product_manager_user;