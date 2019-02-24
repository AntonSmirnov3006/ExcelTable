import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class ExcelWriter {

    private final Randomizer randomizer;

    ExcelWriter(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    void writeValues(Sheet sheet, int number,
                     int firstRowNumber,
                     String gender,
                     List<String> lastNames,
                     List<String> middleNames,
                     List<String> names,
                     List<String> countries, List<String> regions,
                     List<String> streets, List<String> cities) {

        String pattern = "dd-MM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        for (int i = firstRowNumber; i < firstRowNumber + number; i++) {
            Row row = sheet.createRow(i + 1);

            Date now = new Date();
            Date date = randomizer.createDate();

            Cell cell = row.createCell(0);
            cell.setCellValue(lastNames.get(i));
            cell = row.createCell(1);
            cell.setCellValue(names.get(i));
            cell = row.createCell(2);
            cell.setCellValue(middleNames.get(i));
            cell = row.createCell(3);
            cell.setCellValue(now.getYear() - date.getYear());
            cell = row.createCell(4);
            cell.setCellValue(gender);
            cell = row.createCell(5);
            cell.setCellValue(df.format(date));
            cell = row.createCell(6);
            cell.setCellValue(randomizer.createInn());
            cell = row.createCell(7);
            cell.setCellValue(randomizer.createIndex());
            cell = row.createCell(8);
            cell.setCellValue(countries.get(i));
            cell = row.createCell(9);
            cell.setCellValue(regions.get(i));
            cell = row.createCell(10);
            cell.setCellValue(cities.get(i));
            cell = row.createCell(11);
            cell.setCellValue(streets.get(i));
        }
    }

}
