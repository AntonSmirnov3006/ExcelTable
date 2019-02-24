import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TxtReader {

    List<String> cities;
    List<String> countries;
    List<String> maleNames;
    List<String> femaleNames;
    List<String> maleMiddleNames;
    List<String> femaleMiddleNames;
    List<String> maleLastNames;
    List<String> femaleLastNames;
    List<String> regions;
    List<String> streets;

    {
        try {
            cities = readTxtFile("City");
            countries = readTxtFile("Country");
            maleNames = readTxtFile("MaleName");
            femaleNames = readTxtFile("FemaleName");
            maleMiddleNames = readTxtFile("MaleMiddleName");
            femaleMiddleNames = readTxtFile("FemaleMiddleName");
            maleLastNames = readTxtFile("MaleLastName");
            femaleLastNames = readTxtFile("FemaleLastName");
            regions = readTxtFile("Region");
            streets = readTxtFile("Street");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<String> readTxtFile(String fileName) throws IOException {
        Charset charset = Charset.forName("Windows-1251");
        return Files.readAllLines(
                Paths.get(".\\src\\main\\resources\\" + fileName + ".txt"), charset);
    }

}
