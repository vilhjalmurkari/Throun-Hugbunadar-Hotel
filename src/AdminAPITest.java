
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;


class HotelAPITest {
	private static AdminAPI test;
	private static User testUser;
	private static HotelAPI hotelAPI;
	private static User someUser;

	// Gerum ráð fyrir að HotelAPI virki fullkomlega.
	@BeforeAll
	static void setUp() throws Exception {
		try {
			testUser = new User("Test Admin", "admin@test.com");
			test = new AdminAPI(testUser);
			hotelAPI = new HotelAPI();
			User someUser = hotelAPI.makeUser("Some User", "some@user.com");
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	@AfterAll
	static void tearDown() {

		try {
			hotelAPI.deleteUser(testUser.email);
			hotelAPI.deleteUser(someUser.email);

			testUser = null;
			someUser = null;
			test = null;
			hotelAPI = null;
			
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }

	}


	@Test
	void escalateUserPrivelegesTest() {
		try {
			test.escalateUserPriveleges(someUser);
			assertTrue(DBmanager.isAdmin(someUser));
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}


	@Test
	void descalateUserPrivelegesTest() {
		try {
			test.descalateUserPriveleges(someUser);
			assertFalse(DBmanager.isAdmin(someUser));
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	@Test
	void setRoomPriceTest() {
		try {
			Room test_room = hotelAPI.getRoomsFromHotel(hotelAPI.getAllHotels().get(0)).get(0);
			int new_value = -1;
			test.setRoomPrice(new_value, test_room);
			assertEquals(test_room.price, new_value);
			
			int new_value = 800;
			test.setRoomPrice(new_value, test_room);
			assertEquals(test_room.price, new_value);
	

			// Testing list of hotels
			ArrayList<Room> rvkHotels = hotelAPI.hotelSearch("Reykjavík", -1,-1,-1,-1);
			int newPrice = 500;
			test.setRoomPrice(newPrice, rvkHotels);
			for(int i = 0; i < newPrices.length; i++) {
				assertEqual(newPrice,rvkHotels.get(i).price);
			}
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

			assertEquals(test_room.price, original_price * 2.0);
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	@Test
	void addHotelTest() {
		try {

			testHotel = new Hotel("bla", 4, "hello my dude", 111, null,null);
			test.addHotel(testHotel);
			assertTrue("Þetta er í lagi", testHotel != null);
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	@Test
	void addHotelsTest() {
		try {
			ArrayList<Hotel> hotelList = new ArrayList<Hotel>();

			testHotel1 = new Hotel("bla1", 4, "hello my dude", "Reykjavík", null,null);
			testHotel2 = new Hotel("bla2", 4, "holler my dude", "Reykjavík", null,null);
			testHotel3 = new Hotel("bla3", 4, "haio my dude", "Reykjavík", null,null);
			test.addHotel(testHotel1);
			test.addHotel(testHotel2);
			test.addHotel(testHotel3);
			assertEqual(3, hotelAPI.hotelSearch("bla",4,4,-1,-1).size());
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

			testRoom = new Room(50, 400, 4, null);

			test.addRoomToHotel( testRoom, ht);

			assertTrue("Ã¾aÃ° eru fleiri herbergi eftir kall Ã¡ Ã¾etta fall", ht.rooms.size() > old_room_count);
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}
	
	@Test
	void deleteHotelTest() {
		try {
			Hotel ht = test.getAllHotels();
			int old_hotel_count = ht.size();

			testRoom = new Hotel("bla1", 4, "50", "Reykjavík", null, null);

			test.deleteHotel( testRoom );

			ht = test.getAllHotels();

			assertTrue("Ã¾aÃ° eru færri herbergi eftir kall Ã¡ Ã¾etta fall", ht.size() < old_hotel_count);
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	

	@Test
	void deleteRoomTest() {
		try {
			Hotel ht = test.getAllHotels().get(0);
			int old_room_count = ht.rooms.size();
	
			testRoom = new Room(50, 400, 4, null);

			test.deleteRoom( testRoom );
			ht = test.getAllHotels().get(0);

			assertTrue("Ã¾aÃ° eru fleiri herbergi eftir kall Ã¡ Ã¾etta fall", ht.rooms.size() < old_room_count);
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}
}
