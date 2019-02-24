import java.util.Date;
import java.util.Random;

public class Randomizer {

    Date createDate() {
        Random random = new Random();
        int day = random.nextInt(28) + 1;
        int month = random.nextInt(12) + 1;
        int year = random.nextInt(80) + 1919;
        return new Date(year - 1900, month, day);
    }

    int createIndex() {
        Random r = new Random();
        return r.nextInt(100000) + 100000;
    }

    String createInn() {
        Random random = new Random();
        int tax = random.nextInt(10000);
        int personal = random.nextInt(10000);
        int check = 55;
        return "77" + tax + personal + check;
    }

    int getRandomNumber() {
        return new Random().nextInt(15);
    }

}
