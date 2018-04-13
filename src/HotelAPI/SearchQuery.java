package HotelAPI;

import java.util.Date;
import java.util.ArrayList;

public class SearchQuery {
	public ArrayList<String> cities;
	public int price_min = Integer.MIN_VALUE;
	public int price_max = Integer.MAX_VALUE;
	public int rating_min = Integer.MIN_VALUE;
	public int rating_max = Integer.MAX_VALUE;
	public int size_min = Integer.MIN_VALUE;
	public int size_max = Integer.MAX_VALUE;

	public SearchQuery(ArrayList<String> cities, int price_min, int price_max, int rating_min, int rating_max, int size_min, int size_max) {
		this.cities = cities;
		this.price_min = price_min;
		this.price_max = price_max;
		this.rating_min = rating_min;
		this.rating_max = rating_max;
		this.size_min = size_min;
		this.size_max = size_max;
	}
}
