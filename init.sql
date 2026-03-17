-- Create database
CREATE DATABASE TESTDB;
GO

-- Create login and user
CREATE LOGIN testinghuhu WITH PASSWORD = 'uHuHGn1t$3T';
GO

USE TESTDB;
CREATE USER testinghuhu FOR LOGIN testinghuhu;
ALTER ROLE db_owner ADD MEMBER testinghuhu;
GO