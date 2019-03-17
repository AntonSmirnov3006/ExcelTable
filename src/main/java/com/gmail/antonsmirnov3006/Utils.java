package com.gmail.antonsmirnov3006;

import com.gmail.antonsmirnov3006.person.Gender;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Utils {

    static final String SERVER_URL = "https://randomuser.me/api/";

    static boolean internetOn() {
        try {
            final URL url = new URL(SERVER_URL);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public static String convertGender(Gender gender) {
        if (gender == Gender.FEMALE) {
            return "Ж";
        } else if (gender == Gender.MALE) {
            return "М";
        }
        return "";
    }

    public static Gender convertGenderBack(String gender) {
        if (gender.equals("Ж")) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

}
