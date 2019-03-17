package com.gmail.antonsmirnov3006;

import com.gmail.antonsmirnov3006.person.Gender;
import com.gmail.antonsmirnov3006.person.Person;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;

class RemoteGenerator {

    private Gson gson = new Gson();

    private SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");

    private Randomizer randomizer;

    RemoteGenerator(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    List<Person> getPersonsFromServer(int randomNumber) throws JSONException {

        List<Person> result = new ArrayList<>();

        for (int i = 0; i < randomNumber; i++) {

            Response response = get(Utils.SERVER_URL);

            Person person = new Person();

            ApiResponse parsedResponse = gson.fromJson(response.asString(), ApiResponse.class);
            Result personResult = parsedResponse.getResults().get(0);
            Name name = personResult.getName();
            Dob dob = personResult.getDob();
            Location location = personResult.getLocation();

            person.setSecondName(name.getLast());
            person.setFirstName(name.getFirst());
            person.setMiddleName("-");
            person.setAge(dob.getAge());

            if (personResult.getGender().equals("female")) {
                person.setGender(Gender.FEMALE);
            } else if (personResult.getGender().equals("male")) {
                person.setGender(Gender.MALE);
            }

            String stringDate = dob.getDate();
            try {
                person.setDateOfBirth(formatter.parse(stringDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            person.setInn(randomizer.generateInn());
            person.setPostalCode(location.getPostcode());

            person.setCountry("No country");
            person.setRegion(location.getState());
            person.setCity(location.getCity());
            person.setStreet(location.getStreet());

            person.setHouse(String.valueOf(randomizer.getRandomNumber(50)));
            person.setFlat(String.valueOf(randomizer.getRandomNumber(100)));

            result.add(person);
        }

        return result;
    }

}
