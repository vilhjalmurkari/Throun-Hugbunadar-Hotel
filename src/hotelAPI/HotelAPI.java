package hotelAPI;

import java.util.ArrayList;
import java.sql.*;

public class HotelAPI {

	public HotelAPI() throws SQLException {
		DBmanager.init();
	}

	public ArrayList<Hotel> getAllHotels() throws SQLException {
		return DBmanager.getAllHotels();
	}

	public Hotel getHotel(String hotel_name, String hotel_city) throws SQLException {
		return DBmanager.getHotel(hotel_name, hotel_city);
	}

	public ArrayList<Hotel> getHotelsByName(String search_string) throws SQLException {
		return DBmanager.getHotelsByName(search_string);
	}

	public ArrayList<Room> getRoomsFromHotel(Hotel hotel) throws SQLException {
		return hotel.rooms;
	}

	public Room getRoomFromHotel(int room_id, Hotel hotel) throws SQLException {
		return DBmanager.getRoomFromHotel(room_id, hotel);
	}

	public void setRoomPrice(int new_price, Room room) throws SQLException {
		room.price = new_price;
		DBmanager.setRoomPrice(new_price, room);
	}

	public void setRoomPrice(int new_price, ArrayList<Room> rooms) throws SQLException {
		for(Room r : rooms) {
			setRoomPrice(new_price, r);
		}
	}

	public void changeRoomPriceByAmount(double price_change, Room room) throws SQLException {
		DBmanager.changeRoomPriceByAmount(price_change, room);
	}

	public void changeRoomPriceByPercent(double percent, Room room) throws SQLException {
		DBmanager.changeRoomPriceByPercent(percent, room);
	}

	public void addHotel(Hotel hotel) throws SQLException {
		DBmanager.addHotel(hotel);
	}

	public void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		DBmanager.addHotels(hotels);
	}

	public void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		hotel.rooms.add(room);
		DBmanager.addRoomToHotel(room, hotel.name, hotel.city);
	}
	
	public void bookRoom(Room room, User user, long start_date, long end_date) throws SQLException {
		DBmanager.bookRoom(room, user, start_date, end_date);
	}

	public ArrayList<Hotel> getHotelsWithQuery(String hotelQuery, String roomQuery) throws SQLException {
		return DBmanager.getHotelsWithQuery(hotelQuery, roomQuery);
	}

	public ArrayList<Room> getRoomsWithQuery(Hotel hotel, String roomQuery) throws SQLException {
		return DBmanager.getRoomsWithQuery(hotel.name, hotel.city, roomQuery);
	}

	public void deleteHotel(Hotel hotel) throws SQLException {
		DBmanager.deleteHotel(hotel);
	}

	public void deleteRoom(Room room) throws SQLException {
		DBmanager.deleteRoom(room);
	}

	private int getRoomCount() throws SQLException {
		return DBmanager.getRoomCount();
	}

	private ArrayList<Hotel> search(SearchQuery query) throws SQLException {
		return DBmanager.search(query);
	}

	private ArrayList<String> getHotelTags(Hotel hotel) throws SQLException {
		return hotel.tags;
	}

	private ArrayList<String> getRoomTags(Room room) throws SQLException {
		return room.tags;
	} 
}
