import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;

// Notkun: java -cp .;sqlite-jdbc-3.18.0.jar testSaekjaUrDB
// Eftir:  Búið er að skila hótelum úr gagnagrunninum
public class testSaekjaUrDB
{

  public static ArrayList<Hotel> saekjaAllt() {
    ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
    Connection conn = null;
    try
    {
        conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
        Statement stmt = conn.createStatement();
        ResultSet r = stmt.executeQuery("SELECT * FROM Hotels");


        while(r.next()) {

          Hotel h = new Hotel(
          r.getString("name"),
          r.getInt("rating"),
          r.getString("description"),
          r.getInt("zipcode"),
          null,
          null
          );

          listOfHotels.add(h);

        }


        //return listOfHotels;
    }
    catch(SQLException e)
    {
        System.err.println(e.getMessage());
    }
    finally
    {
        try
        {
            if(conn != null)  conn.close();
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
    }
    return listOfHotels;
  }

    public static void main( String[] args )
        throws Exception
    {
        Class.forName("org.sqlite.JDBC");

        ArrayList<Hotel> hotels = saekjaAllt();

        for ( Hotel h: hotels ) {
          System.out.println(h.getHotelRating());
        }


    }
}
