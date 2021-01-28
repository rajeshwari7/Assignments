package Assignments;
 
import java.io.*;
import java.sql.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
 
/**
 * Assignment2: A Java program that exports data from database to Excel file.
 */
public class Db2ExcelExporter {
 
    public static void main(String[] args) {
        new Db2ExcelExporter().export();
    }
     
    public void export() {
        String jdbcURL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&useUnicode=yes&characterEncoding=UTF-8";
        String username = "root";
        String password = "root";
 
        String excelFilePath = "student-export.xlsx";
 
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM student";
 
            Statement statement = connection.createStatement();
 
            ResultSet result = statement.executeQuery(sql);
 
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Students");
 
            writeHeaderLine(workbook,sheet);
 
            writeDataLines(result, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);

            System.out.println("Data has written to Excel");

            statement.close();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
 
    private void writeHeaderLine(XSSFWorkbook wb,XSSFSheet sheet) {
 
        Row headerRow = sheet.createRow(0);

        XSSFFont font= wb.createFont();
        XSSFCellStyle my_style = wb.createCellStyle();

        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setBold(true);
        font.setItalic(false);

        my_style.setFont(font);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(my_style);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Class");
        headerCell.setCellStyle(my_style);
 
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Subject");
        headerCell.setCellStyle(my_style);
 
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Marks");
        headerCell.setCellStyle(my_style);
    }
 
    private void writeDataLines(ResultSet result, XSSFWorkbook workbook,
            XSSFSheet sheet) throws SQLException {
        int rowCount = 1;
 
        while (result.next()) {
            String studentName = result.getString("Name");
            int classNum = result.getInt("Class");
            String subject = result.getString("Subject");
            int marks = result.getInt("Marks");

            Row row = sheet.createRow(rowCount++);
 
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(studentName);
 
            cell = row.createCell(columnCount++);
            if(classNum!=0)
                cell.setCellValue(classNum);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(subject);

            cell = row.createCell(columnCount++);
            cell.setCellValue(marks);

        }
    }
 
}