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

  public static ArrayList<Hotel> getAllHotels() {
    ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
    Connection conn = null;
    try
    {
        conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM Hotels");

        while(rset.next()) {
          ArrayList<Room> roomsInAHotel = getRoomFromHotel(rset.getString("name"), rset.getInt("zipcode"));

          Hotel h = new Hotel(
          rset.getString("name"),
          rset.getInt("rating"),
          rset.getString("description"),
          rset.getInt("zipcode"),
          null,
          roomsInAHotel
          );

          listOfHotels.add(h);
        }

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


  public static ArrayList<Room> getRoomFromHotel( String hotel_name, int hotel_zipcode) {
    ArrayList<Room> listOfRooms = new ArrayList<Room>();
    String query = "SELECT * FROM Rooms WHERE hotel_name= \"" + hotel_name +
    "\" AND hotel_zipcode=" + hotel_zipcode + ";";

    Connection conn = null;
    try
    {
        conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        while(rset.next()) {
          Room room = new Room(
          rset.getInt("id"),
          rset.getInt("size"),
          0,
          false,
          10000
          );
          listOfRooms.add(room);
        }

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
    return listOfRooms;
  }


    public static void main( String[] args )
        throws Exception
    {
        Class.forName("org.sqlite.JDBC");

        ArrayList<Hotel> hotels = getAllHotels();
        System.out.println("_________________________");
        for ( Hotel h: hotels ) {
          System.out.println(h.getHotelRating());
        }


    }
}
