import java.util.Date;
import java.util.Random;

class Randomizer {

    Date createDate() {
        Random random = new Random();
        int day = random.nextInt(28) + 1;
        int month = random.nextInt(12) + 1;
        int year = random.nextInt(80) + 1919;
        return new Date(year - 1900, month, day);
    }

    int createPostcode() {
        Random r = new Random();
        return r.nextInt(100000) + 100000;
    }

    int getRandomNumber() {
        return new Random().nextInt(15);
    }

}
