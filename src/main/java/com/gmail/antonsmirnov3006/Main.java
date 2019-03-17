package com.gmail.antonsmirnov3006;

import com.gmail.antonsmirnov3006.person.Gender;
import com.gmail.antonsmirnov3006.person.Person;
import com.gmail.antonsmirnov3006.sql.SqlHelper;

import java.util.List;

public class Main {

    private Randomizer randomizer = new Randomizer();

    private ExcelWriter excelWriter = new ExcelWriter();
    private SqlHelper sqlHelper = new SqlHelper();
    private LocalGenerator localGenerator = new LocalGenerator(randomizer);
    private RemoteGenerator remoteGenerator = new RemoteGenerator(randomizer);

    public static void main(String[] args) {
        Main helper = new Main();
        helper.writeToFile();
    }

    private void writeToFile() {
        List<Person> content = createContent();

        writeContentToExcel(content);
    }

    private void cacheContentInSql(List<Person> content) {
        sqlHelper.write(content);
    }

    private void writeContentToExcel(List<Person> persons) {
        excelWriter.writeValues(persons);
    }

    private List<Person> createContent() {
        if (Utils.internetOn()) {
            List<Person> contentFromServer = createContentFromServer();
            cacheContentInSql(contentFromServer);
            return contentFromServer;
        } else {
            List<Person> contentFromDatabase = getContentFromSql();
            if (contentFromDatabase.size() > 0) {

                System.out.println("Отсутствует возможность подключиться к серверу. " +
                        "Данные будут получены из базы данных.");
                return contentFromDatabase;

            } else {

                System.out.println("Отсутствует возможность подключиться к серверу. " +
                        "Данные будут сгенерированы на основе локальных файлов.");
                return createContentFromLocalFiles();
            }
        }
    }

    private List<Person> getContentFromSql() {
        return sqlHelper.read();
    }

    private List<Person> createContentFromServer() {
        int number = randomizer.getRandomNumber(30);
        return remoteGenerator.getPersonsFromServer(number);
    }

    private List<Person> createContentFromLocalFiles() {
        int menNumber = randomizer.getRandomNumber(15);
        int womenNumber = randomizer.getRandomNumber(15);

        List<Person> personList = localGenerator.getPersonsFromTxtFiles(menNumber, Gender.MALE);
        personList.addAll(localGenerator.getPersonsFromTxtFiles(womenNumber, Gender.FEMALE));
        return personList;
    }

}
