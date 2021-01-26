# Excel2Database

Programa em Java para salvar uma planilha Excel no SQL Server 2019 

# Docker SQL Server 2019 

---

        docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=*******' --name sql1 -h sql1 -p 1433:1433 -d mcr.microsoft.com/mssql/server:2019-latest
        
        sudo docker exec -it sql1 "bash"

        /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P ********

        CREATE DATABASE TestDB
        go

        SELECT Name from sys.Databases
        go

        USE TestDB
        go

        CREATE TABLE students (id int NOT NULL IDENTITY, name varchar(128) NOT NULL, enrolled Date NOT NULL, progress numeric(4,2) NOT NULL, PRIMARY KEY (id));
        go
        
---


