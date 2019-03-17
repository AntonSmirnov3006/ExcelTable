package com.gmail.antonsmirnov3006.sql;

import com.gmail.antonsmirnov3006.person.Person;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.antonsmirnov3006.Utils.convertGender;
import static com.gmail.antonsmirnov3006.Utils.convertGenderBack;
import static com.gmail.antonsmirnov3006.sql.SqlUtils.*;

public class SqlHelper {

    private SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd");

    public void write(List<Person> persons) {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);

            connection.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            connection.createStatement().executeUpdate("TRUNCATE address");
            connection.createStatement().executeUpdate("TRUNCATE persons");
            connection.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 1");


            for (Person currentPerson : persons) {
                String addressUpdateCommand = "insert into address (postcode, country, region, city, " +
                        "street, house, flat) values " + getSqlAddress(currentPerson);

                PreparedStatement addressStatement = connection.prepareStatement(addressUpdateCommand, Statement.RETURN_GENERATED_KEYS);

                int affectedRows = addressStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = addressStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String personUpdateCommand = "insert into persons (surname, name, middlename, birthday, " +
                                "gender, inn, address_id) values " + getSqlPerson(currentPerson, generatedKeys.getLong(1));

                        PreparedStatement personStatement = connection.prepareStatement(personUpdateCommand, Statement.RETURN_GENERATED_KEYS);
                        personStatement.executeUpdate();
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Person> read() {

        List<Person> result = new ArrayList<>();

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);

            String query = "SELECT * FROM address " +
                    "INNER JOIN persons ON address.id=persons.address_id;";

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Person person = new Person();

                person.setSecondName(rs.getString("surname"));
                person.setFirstName(rs.getString("name"));
                person.setMiddleName(rs.getString("middlename"));
                person.setDateOfBirth(rs.getDate("birthday"));
                person.setGender(convertGenderBack(rs.getString("gender")));
                person.setInn(rs.getString("inn"));
                person.setPostalCode(rs.getString("postcode"));
                person.setCountry(rs.getString("country"));
                person.setRegion(rs.getString("region"));
                person.setCity(rs.getString("city"));
                person.setStreet(rs.getString("street"));
                person.setHouse(rs.getString("house"));
                person.setFlat(rs.getString("flat"));

                result.add(person);
            }
            rs.close();

            st.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return result;

    }

    private String getSqlAddress(Person person) {
        return "('" + person.getPostalCode().replace("'", "''")
                + "', '" + person.getCountry().replace("'", "''")
                + "', '" + person.getRegion().replace("'", "''")
                + "', '" + person.getCity().replace("'", "''")
                + "', '" + person.getStreet().replace("'", "''")
                + "', " + person.getHouse().replace("'", "''")
                + ", " + person.getFlat().replace("'", "''")
                + ")";
    }

    private String getSqlPerson(Person person, long foreignKey) {
        return "('" + person.getSecondName().replace("'", "''")
                + "', '" + person.getFirstName().replace("'", "''")
                + "', '" + person.getMiddleName().replace("'", "''")
                + "', DATE '" + formatter.format(person.getDateOfBirth()).replace("'", "''")
                + "', '" + convertGender(person.getGender()).replace("'", "''")
                + "', '" + person.getInn().replace("'", "''")
                + "', '" + foreignKey
                + "')";
    }
}
