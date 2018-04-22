import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;


class HotelAPITest {
	private static HotelAPI test;
	private static User testUser;
	private static Room testRoom;

	@BeforeAll
	static void setUp() throws Exception {
		try {

			test = new HotelAPI();
			testUser = new User("Testing", "test@test.com");
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	@AfterAll
	static void tearDown() {

		try {
			test.deleteUser(testUser);

			testUser = null;
			test = null;
		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }

	}
	@Test
	void getHotelTest() {
		try {
			//Prófa fyrir margar mismunandi innsetningar aðferðir
			Hotel resultHotel = test.getHotel("Hotel Holt", "Reykjavík");
			String rettNafn = "Hotel Holt";
			assertTrue( "Rangt",rettNafn.equals(resultHotel.name));

			resultHotel = test.getHotel("bull strengur", "Reykjavík");
			assertTrue( "Rangt", resultHotel == null);

			resultHotel = test.getHotel(null, "Reykjavík");
			assertTrue( "Rangt", resultHotel == null);

			resultHotel = test.getHotel("Hotel Holt", "Reykk");
			assertTrue( "Rangt", resultHotel == null);

			resultHotel = test.getHotel("Hotel Holt", "Reykk");
			assertTrue( "Rangt", resultHotel == null);

			resultHotel = test.getHotel("Hotel Holt", null);
			assertTrue( "Rangt", resultHotel == null);

			resultHotel = test.getHotel( null, null);
			assertTrue( "Rangt", resultHotel == null);

		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}

	@Test
	void hotelSearchTest() {
		/*try {
			// Prófa fyrir margar mismunandi innsetningar aðferðir
			// Eins og við höfum þetta í GUI, þá er einungis hægt að velja
			// tölur úr lista frá 1-5.
			ArrayList<Hotel> resultlist = test.hotelSearch("reyk", 2, 5);
			assertEquals(8, resultlist.size());

			resultlist = test.hotelSearch(null, 2, 5);
			assertEquals(19, resultlist.size());

			resultlist = test.hotelSearch("reyk", 5, 2);
			assertEquals(0, resultlist.size());

			resultlist = test.hotelSearch( null, 5, 2);
			assertEquals(0, resultlist.size());

			resultlist = test.hotelSearch("reyk", 2, 5);
			assertEquals(8, resultlist.size());

			resultlist = test.hotelSearch("reyk", 2, 5);
			assertEquals(8, resultlist.size());

		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }*/
	}


	@Test
	void makeUserTest() {
		try {

			User newUser = test.makeUser( "Testing", "test@test.com" );
			String rettEmail = "test@test.com";
			assertTrue("Rangt", rettEmail.equals(newUser.email));



		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}
	
	@Test
	void getUserTest() {
		try {

			User user = test.getUser( "Testing", "test@test.com" );
			String rettEmail = "test@test.com";
			String rettNafn = "Testing";
			assertTrue("Rangt", rettEmail.equals(user.email));
			assertTrue("Rangt", rettNafn.equals(user.name));

		}
		catch(SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	}


/*
	@Test
	void getAllHotelsTest() {
		try {

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
			assertEquals(7, listOfRooms.size());

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
			int new_value = 800;

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

			testHotel = new Hotel("bla", 4, "hello my dude", 111, null,null);
			test.addHotel(testHotel);
			assertTrue("Ã¾etta er Ã­ lagi", testHotel != null);
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
	}*/
}
