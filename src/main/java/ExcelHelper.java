import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    private Randomizer randomizer = new Randomizer();
    private TxtReader reader = new TxtReader();
    private ExcelWriter excelWriter = new ExcelWriter(randomizer);

    public static void main(String[] args) {
        ExcelHelper helper = new ExcelHelper();
        helper.writeToFile();
    }

    private void writeToFile() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = createSheet(workbook);
        Row header = sheet.createRow(0);
        List<String> headerList = createHeaderList();

        for (int i = 0; i < headerList.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerList.get(i));
        }

        int menNumber = randomizer.getRandomNumber();
        int womenNumber = randomizer.getRandomNumber();

        excelWriter.writeValues(sheet, menNumber, 0, "М",
                reader.maleLastNames,
                reader.maleMiddleNames,
                reader.maleNames,
                reader.countries,
                reader.regions,
                reader.streets,
                reader.cities);
        excelWriter.writeValues(sheet, womenNumber, menNumber, "Ж",
                reader.femaleLastNames,
                reader.femaleMiddleNames,
                reader.femaleNames,
                reader.countries,
                reader.regions,
                reader.streets,
                reader.cities);

        String fileLocation = "C:\\TableFile\\file.xlsx";
        File file = new File(fileLocation);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
            workbook.close();

            System.out.println("Файл создан. Путь: " + fileLocation);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> createHeaderList() {
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

    private static Sheet createSheet(XSSFWorkbook workbook) {
        Sheet sheet = workbook.createSheet("Persons");
        for (int i = 0; i < 12; i++) {
            sheet.setColumnWidth(i, 4000);
        }
        return sheet;
    }

}
