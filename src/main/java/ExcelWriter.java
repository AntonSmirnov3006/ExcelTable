import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class ExcelWriter {

    private static XSSFWorkbook workbook;
    private static Sheet sheet;

    ExcelWriter() {
        setupExcelSheet();
    }

    void writeValues(List<Person> persons) {
        fillCells(persons);
        writeFile();
    }

    private void writeFile() {
        File file = new File(".", "PersonTable.xlsx");

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);

            System.out.println("Файл создан. Путь: " + file);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (Exception ignored) {

            }
        }
    }

    private void fillCells(List<Person> persons) {
        String pattern = "dd-MM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);

            Row row = sheet.createRow(i + 1);

            Cell cell = row.createCell(0);
            cell.setCellValue(person.secondName);
            cell = row.createCell(1);
            cell.setCellValue(person.firstName);
            cell = row.createCell(2);
            cell.setCellValue(person.middleName);
            cell = row.createCell(3);
            cell.setCellValue(person.age);
            cell = row.createCell(4);
            if (person.gender == Gender.FEMALE) {
                cell.setCellValue("Ж");
            } else if (person.gender == Gender.MALE) {
                cell.setCellValue("М");
            }
            cell = row.createCell(5);
            cell.setCellValue(df.format(person.dateOfBirth));
            cell = row.createCell(6);
            cell.setCellValue(person.inn);
            cell = row.createCell(7);
            cell.setCellValue(person.postalCode);
            cell = row.createCell(8);
            cell.setCellValue(person.country);
            cell = row.createCell(9);
            cell.setCellValue(person.region);
            cell = row.createCell(10);
            cell.setCellValue(person.city);
            cell = row.createCell(11);
            cell.setCellValue(person.street);
            cell = row.createCell(12);
            cell.setCellValue(person.house);
            cell = row.createCell(13);
            cell.setCellValue(person.flat);
        }
    }

    private void setupExcelSheet() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Persons");
        for (int i = 0; i < 12; i++) {
            sheet.setColumnWidth(i, 4000);
        }
        Row header = sheet.createRow(0);
        List<String> headerList = createHeaderList();

        for (int i = 0; i < headerList.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerList.get(i));
        }
    }

    private ArrayList<String> createHeaderList() {
        ArrayList<String> headerList = new ArrayList<String>();
        headerList.add("Фамилия");
        headerList.add("Имя");
        headerList.add("Отчество");
        headerList.add("Возраст");
        headerList.add("Пол");
        headerList.add("Дата рождения");
        headerList.add("Инн");
        headerList.add("Почтовый индекс");
        headerList.add("Страна");
        headerList.add("Область");
        headerList.add("Город");
        headerList.add("Улица");
        headerList.add("Дом");
        headerList.add("Квартира");
        return headerList;
    }

}
