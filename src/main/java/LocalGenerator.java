import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class LocalGenerator {

    private Randomizer randomizer;

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

    LocalGenerator(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    private static List<String> readTxtFile(String fileName) throws IOException {
        Charset charset = Charset.forName("Windows-1251");
        return Files.readAllLines(
                Paths.get("." + File.separator + "src" + File.separator + "main" +
                        File.separator + "resources" + File.separator + fileName + ".txt"), charset);
    }

    List<Person> getPersonsFromTxtFiles(int randomNumber, Gender gender) {

        List<Person> result = new ArrayList<>();

        for (int i = 0; i < randomNumber; i++) {

            Person person = new Person();

            if (gender == Gender.MALE) {
                person.secondName = maleLastNames.get(i);
                person.firstName = maleNames.get(i);
                person.middleName = maleMiddleNames.get(i);
            } else if (gender == Gender.FEMALE) {
                person.secondName = femaleLastNames.get(i);
                person.firstName = femaleNames.get(i);
                person.middleName = femaleMiddleNames.get(i);
            }

            person.dateOfBirth = randomizer.createDate();
            Date now = new Date();
            person.age = now.getYear() - person.dateOfBirth.getYear();

            person.gender = gender;

            person.inn = generateInn();
            person.postalCode = randomizer.createPostcode();

            person.country = countries.get(i);
            person.region = regions.get(i);
            person.city = cities.get(i);
            person.street = streets.get(i);

            person.house = String.valueOf(randomizer.getRandomNumber());
            person.flat = String.valueOf(randomizer.getRandomNumber());

            result.add(person);
        }

        return result;
    }

    private String generateInn() {
        int[] inn = {7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] weights1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
        int[] weights2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};

        for (int i = 2; i < 10; i++) {
            inn[i] = generateNumber(0, 9);
        }

        int controlSum1 = 0;
        for (int i = 0; i < weights1.length; i++) {
            controlSum1 += weights1[i] * inn[i];
        }
        int number1 = controlSum1 % 11;
        if (number1 > 9) {
            number1 = number1 % 10;
        }
        inn[10] = number1;
        int controlSum2 = 0;
        for (int i = 0; i < weights1.length; i++) {
            controlSum2 += weights2[i] * inn[i];
        }
        int number2 = controlSum2 % 11;
        if (number2 > 9) {
            number2 = number2 % 10;
        }
        inn[11] = number2;

        String result = "";
        for (int i = 0; i < inn.length; i++) {
            result += inn[i];
        }
        return result;
    }

    private Integer generateNumber(Integer from, Integer to) {
        return (from + (int) Math.round(Math.random() * (to - from)));
    }

}
