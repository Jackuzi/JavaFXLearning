package fx.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by kamilek on 30/11/2016.
 */
public class Users {
    private final String sql = "SELECT * FROM TEST ORDER BY ID ASC";
    private DBConnection db;
    private ObservableList<Member> memberData = FXCollections.observableArrayList();

    public Users() throws SQLException {
    }

    public void retrieveData() throws SQLException {
        db = new DBConnection();
        Connection conn = db.getConn();

        Statement statement = conn.createStatement();

        try {
            ResultSet rs = statement.executeQuery(getSql());
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                Date dob = rs.getDate("DOB");
                String city = rs.getString("CITY");
                String postcode = rs.getString("POSTCODE");
                String street = rs.getString("STREET");
                String country = rs.getString("COUNTRY");
                Integer phone = rs.getInt("PHONE");
                String email = rs.getString("EMAIL");
                String profile = rs.getString("PROFILE");

                memberData.add(new Member(id, name, surname, profile, dob, city, postcode, street, country, phone, email));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public ObservableList<Member> getMemberData() {
        return memberData;
    }


    private String getSql() {
        return sql;
    }
}
