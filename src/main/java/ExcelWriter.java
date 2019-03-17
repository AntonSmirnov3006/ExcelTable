import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
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

            System.out.println("Файл создан. Путь: " + convertRelativePathToAbsolutePath(file));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (Exception ignored) {

            }
        }
    }

    private String convertRelativePathToAbsolutePath(File file) {
        return FileSystems.getDefault().getPath(file.toString())
                .normalize().toAbsolutePath().toString();
    }

    private void fillCells(List<Person> persons) {
        String pattern = "dd-MM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);

            Row row = sheet.createRow(i + 1);

            String gender = "";
            if (person.getGender() == Gender.FEMALE) {
                gender = "Ж";
            } else if (person.getGender() == Gender.MALE) {
                gender = "М";
            }
            String[] personArray = {person.getSecondName(), person.getFirstName(), person.getMiddleName(),
                    String.valueOf(person.getAge()), gender, df.format(person.getDateOfBirth()), person.getInn(),
                    person.getPostalCode(), person.getCountry(), person.getRegion(),
                    person.getCity(), person.getStreet(), person.getHouse(), person.getFlat()
            };

            for (int j = 0; j < personArray.length; j++) {
                fillCell(row, j, personArray[j]);
            }
        }
    }

    private void fillCell(Row row, int columnNumber, String secondName) {
        Cell cell = row.createCell(columnNumber);
        cell.setCellValue(secondName);
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
