class bla {
	public static long parseNumber(String s) {
		long result = 0;
		boolean minus = false;

		if(s.charAt(0) == '-') {
			minus = true;
			s = s.substring(1, s.length());
		}

		for(char c : s.toCharArray()) {
			result = result * 10 + (int)(c - '0');
		}

		return minus ? -result : result;
	}
	public static void main(String[] args) {
		long n = parseNumber(args[0]);
		System.out.println(n);
	}
}