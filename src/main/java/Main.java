import java.util.List;

public class Main {

    private Randomizer randomizer = new Randomizer();

    private ExcelWriter excelWriter = new ExcelWriter();
    private LocalGenerator localGenerator = new LocalGenerator(randomizer);

    public static void main(String[] args) {
        Main helper = new Main();
        helper.writeToFile();
    }

    private void writeToFile() {
        List<Person> content = createContent();

        writeContentToExcel(content);
    }

    private void writeContentToExcel(List<Person> persons) {
        excelWriter.writeValues(persons);
    }

    private List<Person> createContent() {
        int menNumber = randomizer.getRandomNumber();
        int womenNumber = randomizer.getRandomNumber();

        List<Person> personList = localGenerator.getPersonsFromTxtFiles(menNumber, Gender.MALE);
        personList.addAll(localGenerator.getPersonsFromTxtFiles(womenNumber, Gender.FEMALE));
        return personList;
    }

}
