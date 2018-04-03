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
			ArrayList<Hotel> hotels = test.getHotelsByName("my hotel");
			assertEquals(2, hotels.size());
			assertEquals(5, hotels.get(0).getHotelRating());
			assertEquals(5, hotels.get(1).getHotelRating());
			
			hotels = test.getHotelsByName("noname");
			assertEquals(0,hotels.size());
			
			hotels = test.getHotelsByName(null);
			assertEquals(0,hotels.size());
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}
	
	@Test
	void getHotelTagsTest() {
		try {
			HotelAPI test = new HotelAPI();
			
			ArrayList<Hotel> hotels = test.getHotelsByName("my hotel");
			ArrayList<String> listOfStrings = test.getHotelTags(hotels.get(0));
			assertEquals(5, listOfStrings.size());
			
			listOfStrings = test.getHotelTags(hotels.get(1));
			assertEquals(0, listOfStrings.size());
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}
	
	@Test
	void getRoomsFromHotelTest() {
		try {
			HotelAPI test = new HotelAPI();
			
			ArrayList<Hotel> hotels = test.getHotelsByName("my hotel");
			
			ArrayList<Room> listOfRooms = test.getRoomsFromHotel(hotels.get(0));
			assertEquals(6, listOfRooms.size());
			
			listOfRooms = test.getRoomsFromHotel(hotels.get(1));
			assertEquals(0, listOfRooms.size());
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}
	
	@Test
	void getRoomTagsTest() {
		try {
			HotelAPI test = new HotelAPI();
			
			ArrayList<Hotel> hotels = test.getHotelsByName("my hotel");
			ArrayList<Room> rooms = test.getRoomsFromHotel(hotels.get(0));
			ArrayList<String> room_tags = test.getRoomTags(rooms.get(0));
			assertEquals(4, room_tags.size());
			
			rooms = test.getRoomsFromHotel(hotels.get(0));
			room_tags = test.getRoomTags(rooms.get(1));
			assertEquals(0, room_tags.size());
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

}
