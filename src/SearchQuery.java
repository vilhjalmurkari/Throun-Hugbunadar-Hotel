import java.util.Date;
import java.util.ArrayList;

class SearchQuery {
	public ArrayList<Integer> zipcodes;
	public int price_min;
	public int price_max;
	public int rating_min;
	public int rating_max;
	public int size_min;
	public int size_max;

	public SearchQuery(ArrayList<Integer> zipcodes, int price_min, int price_max, int rating_min, int rating_max, int size_min, int size_max) {
		this.zipcodes = zipcodes;
		this.price_min = price_min;
		this.price_max = price_max;
		this.rating_min = rating_min;
		this.rating_max = rating_max;
		this.size_min = size_min;
		this.size_max = size_max;
	}
}