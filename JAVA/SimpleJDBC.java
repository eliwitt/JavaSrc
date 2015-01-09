import java.sql.*;
import java.net.URL;
import java.util.*;

class SimpleJDBC {

	public static void main(String args[]) {

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			// DriverManager.setLogStream(System.out);
			System.out.println("testing baby");
			String databaseName = "jdbc:odbc:grace";
			System.out.println("Try to connect");
			Connection con =
				DriverManager.getConnection(databaseName, "etl_dba", "etl_dba");
			System.out.println("Process the statement");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
				"SELECT r_a_key_id, r_a_ticket_nbr FROM dwh_r_a");

			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2));
			}

		}
		catch (Exception e) {
			System.out.println("Ouch!!  exception baby1");
			e.printStackTrace();
		}
	}

}
