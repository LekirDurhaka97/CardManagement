## Disclaimer
This application is not fully tailored to perfection because the assessment requirement is quite simple(don't want to over-engineer).


## Description
This application is simple, created for Job Application Assessment(not fully cooked).

Its using Java 21 with Springboot framework and Maven as the dependency manager.


## Db Setup
I use docker desktop on windows run under WSL2. 

I created a .bat file and running it will setup download the mssql image.

It's using image 
```
mcr.microsoft.com/mssql/server:2025-latest
```

translating into 
```
Microsoft SQL Server 2025 (RTM-CU3) (KB5077896) - 17.0.4025.3 (X64)
Feb 25 2026 20:54:27
Copyright (C) 2025 Microsoft Corporation
Enterprise Developer Edition (64-bit) on Linux (Ubuntu 24.04.4 LTS) <X64>
```


## JUST CLICK THIS TO SETUP DB(for windows in IntelliJ) or JUST RUN IN POWERSHELL THIS TO SETUP DB(for windows in powershell)
```bash
& ".\db-docker-local-init.bat"
```
It will auto run the container, ```init.sql```, and ```table-init.sql```, creating necessary database, user, schema, tables.


## Usage
Just import ```CardManagement.postman_collection.json``` into postman, then its self-explanatory

There's a swagger also after the app run:
http://localhost:8083/api/swagger-ui/index.html#/


## Log
The log output on ```logs/card.log```