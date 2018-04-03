import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.sql.*;

class HotelAPITest {
	
	@Test
	void getAllHotelsTest() {
		try {
			HotelAPI test = new HotelAPI();
			int results = test.getAllHotels().size();
			assertEquals(3, results);
			
			
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
			assertEquals(4, hotelHolt.getHotelRating());
			
		/*	Hotel hNull = null;
			Hotel hotel = test.getHotel(null, 100);
			assertEquals(hNull, hotel);
			*/
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

}
