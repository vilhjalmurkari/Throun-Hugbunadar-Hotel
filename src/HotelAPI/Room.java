package hotelAPI;

import java.util.Date;
import java.util.ArrayList;

public class Room {
	public int id;
	public int size;
	public int price;
	public int bed_count;
	public ArrayList<String> tags;

	public Room(int size, int bed_count, int price, ArrayList<String> tags) {
		this.id = (int) (Math.random()*Integer.MAX_VALUE);
		this.size = size;
		this.bed_count = bed_count;
		this.price = price;
		this.tags = tags;
	}

	/*
	Ef okkur er sama um hvað id-ið er þegar við erum að búa til hotel hlut,
	t.d. þegar við erum að setja inn í gagnagrunninn en ekki ná úr honum,
	er nytsamlegt að hafa annan constructor, því við munum ekki stilla id-ið
	sjálfir þegar við setjum inn.
	*/

	//einhver föll hér
	public int getRoomSize() {
		return this.size;
	}
}
