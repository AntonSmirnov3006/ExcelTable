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
                person.setSecondName(maleLastNames.get(i));
                person.setFirstName(maleNames.get(i));
                person.setMiddleName(maleMiddleNames.get(i));
            } else if (gender == Gender.FEMALE) {
                person.setSecondName(femaleLastNames.get(i));
                person.setFirstName(femaleNames.get(i));
                person.setMiddleName(femaleMiddleNames.get(i));
            }

            person.setDateOfBirth(randomizer.createDate());
            Date now = new Date();
            person.setAge(now.getYear() - person.getDateOfBirth().getYear());

            person.setGender(gender);

            person.setInn(randomizer.generateInn());
            person.setPostalCode(String.valueOf(randomizer.createPostcode()));

            person.setCountry(countries.get(i));
            person.setRegion(regions.get(i));
            person.setCity(cities.get(i));
            person.setStreet(streets.get(i));

            person.setHouse(String.valueOf(randomizer.getRandomNumber(50)));
            person.setFlat(String.valueOf(randomizer.getRandomNumber(100)));

            result.add(person);
        }

        return result;
    }

}
