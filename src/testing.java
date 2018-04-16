import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;

class testing {
	public static void main(String[] args) throws SQLException {
		HotelAPI api = new HotelAPI();
		ArrayList<Hotel> hotels = api.getAllHotels();

		for(Hotel h : hotels) {
			System.out.println(h.name);
		}
	}

}
