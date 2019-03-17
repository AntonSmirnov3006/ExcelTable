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

    int getRandomNumber(int max) {
        return new Random().nextInt(max);
    }

    String generateInn() {
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
