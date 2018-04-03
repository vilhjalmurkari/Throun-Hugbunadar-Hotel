import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.sql.*;

class HotelAPITest {
	
	@Test
	void test() {
		try {
			HotelAPI test = new HotelAPI();
			ArrayList<Hotel> hotels = test.getAllHotels();
			System.out.println("nyet");
			int results = hotels.size();
			assertEquals(1, results);
			
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}
	
	
	@Test
	void getHotelTest() {
		try {
			HotelAPI test = new HotelAPI();
			Hotel hotelHolt = test.getHotel("Holt", 100);
			System.out.println("flott");
			assertEquals(4, hotelHolt.getHotelRating());
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

}
