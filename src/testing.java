import hotelAPI.*;
import java.sql.*;

class testing {
	private static long parseNumber(String s) {
		System.out.println("strengur sem kom inn: " + s);
		long result = 0;
		boolean minus = false;

		if(s.charAt(0) == '-') {
			minus = true;
			s = s.substring(1, s.length());
		}

		for(char c : s.toCharArray()) {
			result = result * 10 + (int)(c - '0');
		}

		//return minus ? -result : result;
		if(minus) {
			return -result;
		}else {
			return result;
		}
	}

	public static void main(String[] args) {
		int n = (int)parseNumber(args[0]);
		System.out.println(n);
	}
}