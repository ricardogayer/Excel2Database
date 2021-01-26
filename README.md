# Excel2Database

Programa em Java para salvar uma planilha Excel no SQL Server 2019 

### Docker SQL Server 2019 

---

        docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=*******' --name sql1 -h sql1 -p 1433:1433 -d mcr.microsoft.com/mssql/server:2019-latest
        
        sudo docker exec -it sql1 "bash"

        /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P ********

        CREATE DATABASE TestDB
        go

        SELECT name from sys.databases
        go

        USE TestDB
        go

        CREATE TABLE students (id int NOT NULL IDENTITY, name varchar(128) NOT NULL, enrolled Date NOT NULL, progress numeric(4,2) NOT NULL, PRIMARY KEY (id));
        go
        
---
* Importante que a senha tenha no mínimo 8 caracteres com letra maiúscula, números e caracteres especiais.



### Modelo da Planilha de Exemplo

Student Name | Enrolled | Progress
:------------ | :--------: | --------:
Ricardo | 27/04/72 | 1
Hugo | 18/03/74 | 0,5
Renato | 08/12/70 | 0,8


