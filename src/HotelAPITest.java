import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.sql.*;


class HotelAPITest {
	private HotelAPI test;

	@Before
	public void setUp() throws Exception {
		try {
			System.out.print("hinga�");
			this.test = new HotelAPI();
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@After
	public void tearDown() {
		System.out.print("hinga�");
		this.test = null;
			
	}

	@Test
	void getAllHotelsTest() {
		try {
			
			if(test == null) {
				
				System.out.print("fiskur");
			}
			
			int results = this.test.getAllHotels().size();
			
			assertEquals(3, results);
			
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}
	
	@Test
	void getHotelTest() {
		//getHotelsByNameTest
		try {
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
			Room test_room = test.getRoomsFromHotel(test.getAllHotels().get(0)).get(0);
			int new_value = 1234;

			test.setRoomPrice(new_value, test_room);
			
			assertEquals(test_room.price, new_value);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@Test
	void changeRoomPriceByAmountTest() {
		try {
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
			
			Hotel newHotel = new Hotel("bla", 4, "hello my dude", 111, null,null);
			test.addHotel(newHotel);
			assertTrue("þetta er í lagi", newHotel != null);
		} 
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }	
	}

	@Test
	void addRoomToHotelTest() {
		try {
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
}
