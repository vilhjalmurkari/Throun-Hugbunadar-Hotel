import java.sql.*;

class V8c {
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:company.db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select Ssn from employee");

			PreparedStatement pStatement = connection.prepareStatement("UPDATE employee SET Salary = Salary + 1000 WHERE Ssn = ?"); 

			while(rs.next()) {
				pStatement.clearParameters();
				pStatement.setString(1, ""+rs.getInt("Ssn"));
				pStatement.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				System.err.println(e);
			}
		}
	}
}