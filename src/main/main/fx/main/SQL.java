package fx.main;

/**
 * Created by kamilek on 23/02/2017.
 */
public class SQL {

    public String getAddPicturePath() {
        return "UPDATE TEST SET PROFILE = ? WHERE ID = ?";
    }

    public String getAddSql() {

        return "INSERT INTO TEST VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    }

    public String getDelSql() {

        return "DELETE FROM TEST WHERE NAME = ? AND SURNAME = ?";
    }

    public String getUpdSql() {
        return "UPDATE TEST SET NAME = ?, SURNAME = ?, DOB=?, STREET=?, CITY=?, POSTCODE=?, COUNTRY=?, PHONE=?, " +
                "EMAIL=? WHERE ID = ?";
    }

}
