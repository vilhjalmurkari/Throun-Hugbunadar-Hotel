package hotelAPI;

import java.util.Date;
import java.util.ArrayList;

public class Hotel {
	public String name;
	public int rating;
	public String description;
	public String city;
	public ArrayList<String> tags;
	public ArrayList<Room> rooms;

	// Notkun: Hotel(n,r,d,c,t,r)
	// Fyrir:  n er nafn hótels,
	//         r er stjörnufjöldi hótels,
	//         d er lýsing hótels,
	//         c er borg hótels,
	//         t er listi taga yfir hótelið,
	//         r er listi herbergja í hótelinu.
	// Eftir:  Hotel hlutur hefur verið búinn til og upphafsstilltur.
	public Hotel(String name, int rating, String description, String city, ArrayList<String> tags, ArrayList<Room> rooms) {
		this.name = name;
		this.rating = rating;
		this.description = description;
		this.city = city;
		this.tags = tags;
		this.rooms = rooms;
	}

	// Notkun: printHotel()
	// Fyrir:  Ekkert.
	// Eftir:  Upplýsingar um hótelið hefur verið prentað á staðalúttak.
	public void printHotel() {
		System.out.println("Name: " + name);
		System.out.println("City: " + city);
		System.out.println("Rating: " + rating);
		System.out.print("description: ");

		char[] description_array = description.toCharArray();
		int max_len = 32;

		for(int i = 0;
			i < ((description_array.length > max_len) ? max_len : description_array.length);
			i++) {
			System.out.print(description_array[i]);
			if(i == max_len-1) System.out.println("...");
		}

		System.out.println();

		System.out.println("tags:");

		for(String tag : tags) {
			System.out.print(tag + ", ");
		}

		System.out.println();

		System.out.println("Rooms count: " + rooms.size());
	}
}
