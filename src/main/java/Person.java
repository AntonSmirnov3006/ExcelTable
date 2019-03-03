import java.util.Date;

class Person {
    String secondName;
    String firstName;
    String middleName;
    int age;
    Gender gender;
    Date dateOfBirth;
    String inn;
    int postalCode;
    String country;
    String region;
    String city;
    String street;
    String house;
    String flat;

    @Override
    public String toString() {
        return "Person{" +
                "secondName='" + secondName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", inn=" + inn +
                ", postalCode=" + postalCode +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                '}';
    }
}

enum Gender {
    MALE, FEMALE
}