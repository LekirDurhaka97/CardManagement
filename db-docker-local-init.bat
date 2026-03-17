@echo off
REM -----------------------------
REM SQL Server Docker Setup Script
REM -----------------------------

SET "SA_PASSWORD=uHuHGn1t$3T"
SET "CONTAINER_NAME=mssql-local"
cd /d %~dp0
SET "SQL_DIR_HOST=%CD%"

SET "INIT_SQL=init.sql"
SET "TABLES_SQL=tables-init.sql"

SET "INIT_SQL_CONTAINER=/docker-entrypoint-initdb.d/%INIT_SQL%"
SET "TABLES_SQL_CONTAINER=/docker-entrypoint-initdb.d/%TABLES_SQL%"

SET "IMAGE=mcr.microsoft.com/mssql/server:2025-latest"
SET "PORT=1433"

REM -----------------------------
REM Check SQL files exist
REM -----------------------------
IF NOT EXIST "%SQL_DIR_HOST%\%INIT_SQL%" (
    echo ERROR: %INIT_SQL% not found.
    pause
    EXIT /B 1
)

IF NOT EXIST "%SQL_DIR_HOST%\%TABLES_SQL%" (
    echo ERROR: %TABLES_SQL% not found.
    pause
    EXIT /B 1
)

REM -----------------------------
REM Remove existing container
REM -----------------------------
docker ps -a --format "{{.Names}}" | findstr /i %CONTAINER_NAME% >nul
IF %ERRORLEVEL%==0 (
    echo Removing existing container...
    docker rm -f %CONTAINER_NAME%
)

REM -----------------------------
REM Start SQL Server container
REM -----------------------------
echo Starting SQL Server container...
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=%SA_PASSWORD%" ^
    -p %PORT%:1433 ^
    -v "%SQL_DIR_HOST%:/docker-entrypoint-initdb.d" ^
    --name %CONTAINER_NAME% -d %IMAGE%

REM -----------------------------
REM Wait for SQL Server ready
REM -----------------------------
:WAIT_LOOP
echo Waiting for SQL Server to be ready...
docker exec %CONTAINER_NAME% /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P %SA_PASSWORD% -Q "SELECT 1" -C -N >nul 2>&1
IF ERRORLEVEL 1 (
    timeout /t 2 >nul
    GOTO WAIT_LOOP
)

echo SQL Server is ready!

REM -----------------------------
REM Run init.sql
REM -----------------------------
echo Executing %INIT_SQL%...
docker exec -i %CONTAINER_NAME% /opt/mssql-tools18/bin/sqlcmd ^
    -S localhost ^
    -U sa ^
    -P %SA_PASSWORD% ^
    -i %INIT_SQL_CONTAINER% ^
    -C -N

REM -----------------------------
REM Run tables-init.sql
REM -----------------------------
echo Executing %TABLES_SQL%...
docker exec -i %CONTAINER_NAME% /opt/mssql-tools18/bin/sqlcmd ^
    -S localhost ^
    -U testinghuhu ^
    -P %SA_PASSWORD% ^
    -d TESTDB ^
    -i %TABLES_SQL_CONTAINER% ^
    -C -N

echo Database initialization complete.
pause