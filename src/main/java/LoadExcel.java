import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Iterator;

public class LoadExcel {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Executando...");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;user=SA;password=********";

        // Locale.setDefault(new Locale("pt", "BR"));
        /*

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
        */

        long start = System.currentTimeMillis();
        int batchSize = 20;
        Connection connection = null;
        String sql = "INSERT INTO TestDB.dbo.students (name, enrolled, progress) VALUES (?, ?, ?)";
        String excelFilePath = "/Users/rrgayer/Documents/Students.xlsx";
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        connection = DriverManager.getConnection(connectionUrl);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(sql);
        try (Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            int pasta = workbook.getSheetIndex("Sheet1");
            Sheet sheet = workbook.getSheetAt(pasta);
            Iterator<Row> rowIterator = sheet.iterator();
            int count = 0;
            rowIterator.next();
            while (rowIterator.hasNext()) {
                System.out.println("Linha...");
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            String name = nextCell.getStringCellValue();
                            System.out.println("Student Name = " + name);
                            statement.setString(1, name);
                            break;
                        case 1:
                            LocalDateTime enrollDate = nextCell.getLocalDateTimeCellValue();
                            System.out.println("Data = " + enrollDate.toString());
                            statement.setTimestamp(2, Timestamp.valueOf(enrollDate));
                            break;
                        case 2:
                            double progress = nextCell.getNumericCellValue();
                            System.out.println("Progress = " + progress);
                            statement.setDouble(3, progress);
                            break;
                    }
                }
                statement.addBatch();
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
                workbook.close();
            }
            statement.executeBatch();
            connection.commit();
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Importação executada em %d ms\n", (end - start));
    }
}


// https://www.codejava.net/coding/java-code-example-to-import-data-from-excel-to-database

