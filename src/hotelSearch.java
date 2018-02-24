//keyrsla: java -cp .:sqlite-jdbc....jar hotelSearch
import java.sql.*;

class Hotel {

	public String name;
	public int rating;
	public String description;
	public int zipcode;

	public Hotel(String name, int rating, String description, int zipcode) {
		this.name = name;
		this.rating = rating;
		this.description = description;
		this.zipcode = zipcode;
	}
}


class hotelSearch {
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select name from Hotels");

			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				System.err.println(e);
			}
		}
	}
}