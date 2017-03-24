package fx.main;


import java.sql.Date;

/**
 * Created by kamilek on 02/12/2016.
 */
public class Member {
    private final String city;
    private final String postcode;
    private final String street;
    private final String country;
    private final String email;
    private Integer id;
    private String name;
    private String surname;
    private Date dob;
    private Integer phone;
    private String profile;


    public Member(Integer id, String name, String surname, String profile, Date dob, String city, String postcode, String street, String country, Integer phone, String email) {

        this.name = name;
        this.id = id;
        this.surname = surname;
        this.profile = profile;
        this.dob = dob;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }


    public String getProfile() {
        return profile;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "id " + this.getId() + "name " + this.getName() + "surname " + this.getSurname() + "profile path " + this.getProfile() + "DOB " + this.getDob() + "city " + this.getCity() + "postcode " + this.getPostcode() + "street " + this.getStreet() + "country " + this
                .getCountry() + "phone " + this.getPhone() + "email " + this.getEmail();

    }
}
