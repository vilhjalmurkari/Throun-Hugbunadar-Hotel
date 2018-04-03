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

	@Test
	void setRoomPriceTest() {
		try {
			HotelAPI test = new HotelAPI();

			Room test_room = test.getRoomsFromHotel(test.getAllHotels().get(0)).get(0);
			int new_value = 1234;

			test.setRoomPrice(new_value, test_room);
			
			assertEquals(test_room.prince, new_value);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@Test
	void changeRoomPriceByAmountTest() {
		try {
			HotelAPI test = new HotelAPI();
			
			Room test_room = test.getRoomsFromHotel(test.getAllHotels().get(0)).get(0);
			int original_price = test_room.price;

			test.changeRoomPriceByAmount(1000, test_room);

			assertEquals(test_room.price, original_price + 1000);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@Test
	void changeRoomPriceByPercentTest() {
		try {
			HotelAPI test = new HotelAPI();

			Room test_room = test.getRoomsFromHotel(test.getAllHotels().get(0)).get(0);
			int original_price = test_room.price;

			test.changeRoomPriceByPercent(100, test_room);

			assertEquals(test_room.price, original_price * 2);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@Test
	void addHotelTest() {
		try {
			HotelAPI test = new HotelAPI();
			
			Hotel newHotel = test.addHotel("bla", 4, "hello my dude", 111);

			AssertNotNull(newHotel);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@Test
	void addRoomToHotelTest() {
		try {
			HotelAPI test = new HotelAPI();
			Hotel ht = test.getAllHotels().get(0);
			int old_room_count = ht.rooms.size(); 

			test.addRoomToHotel(new Room(0, 50, 400, 4, null), ht);

			assertTrue("það eru fleiri herbergi eftir kall á þetta fall", ht.rooms.size() > old_room_count);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}
	/*
	public static void bookRoom(int room_id, int user_id, long start_date, long end_date) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Bookings(user_id, room_id, start_date, end_date, confirmed) VALUES(?, ?, ?, ?, ?)");

		ps.setInt(1, user_id);
		ps.setInt(2, room_id);
		ps.setLong(3, start_date);
		ps.setLong(4, end_date);
		ps.setBoolean(5, false); // Bókanir eru alltaf fyrst óstaðfestar.

		ps.executeUpdate();
	}
	*/

}
