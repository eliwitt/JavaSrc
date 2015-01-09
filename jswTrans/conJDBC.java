import java.sql.*;
import java.net.URL;
import java.util.Vector;

public class conJDBC implements intJDBC {
	
	jdbcData theData;
	private static String debug;
	private static String databaseName = "jdbc:odbc:oradev8b";

	public void setDebug(String stgDebug) { debug = stgDebug; }
	//
	//  this is the interface method for querying the database
	//
	public jdbcData queryJDBC(String key) {
	
		theData = new jdbcData();
		theData.setKey(key);
		try { 			
			getObj(theData);
		}
		catch (SQLException ex) {

		// A SQLException was generated.  Catch it and
		// display the error information.  Note that there
		// could be multiple error objects chained
		// together

			System.out.println ("\n*** SQLException caught ***\n");
			theData.setMsg(ex.getMessage());
			while (ex != null) {
				System.out.println ("SQLState: " +
					ex.getSQLState ());
			System.out.println ("Message:  " + ex.getMessage ());
			System.out.println ("Vendor:   " +
					ex.getErrorCode ());
			ex = ex.getNextException ();
			System.out.println ("");

			}
			return theData;
		}
		
		return theData;
		
	}

	//
	//  this is the interface method for inserting into the database
	//
	public jdbcData insertJDBC(String key, String balance) {
	
		theData = new jdbcData();
		theData.setKey(key);
		theData.setBal(balance);
		
		try { 
			
			insertObj(theData);
			theData.setMsg("Inserted " + theData.getKey() + " and " + theData.getBal() + " !");			
		}
		catch (SQLException ex) {

		// A SQLException was generated.  Catch it and
		// display the error information.  Note that there
		// could be multiple error objects chained
		// together

			System.out.println ("\n*** SQLException caught ***\n");
			theData.setMsg(ex.getMessage());
			while (ex != null) {
				System.out.println ("SQLState: " +
					ex.getSQLState ());
			System.out.println ("Message:  " + ex.getMessage ());
			System.out.println ("Vendor:   " +
					ex.getErrorCode ());
			ex = ex.getNextException ();
			System.out.println ("");
			}
			return theData;
		}
		
		return theData;
		
	}

	//
	//  this is the interface method for inserting into the database
	//
	public Vector getAccts() {
		Vector theList = new Vector();
		try { 
			theList = listAccts();	
		}
		catch (SQLException ex) {

		// A SQLException was generated.  Catch it and
		// display the error information.  Note that there
		// could be multiple error objects chained
		// together

			System.out.println ("\n*** SQLException caught ***\n");
			jdbcData tempData = new jdbcData();
			tempData.setMsg(ex.getMessage());
			theList.addElement(tempData);
			while (ex != null) {
				System.out.println ("SQLState: " +
					ex.getSQLState ());
			System.out.println ("Message:  " + ex.getMessage ());
			System.out.println ("Vendor:   " +
					ex.getErrorCode ());
			ex = ex.getNextException ();
			System.out.println ("");
			}
			return theList;
		}
		
		return theList;
		
	}
  
	// Load mSQL driver when class is first loaded.
	public conJDBC() {
		try {
		// Load the jdbc-odbc bridge driver

		//Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName ("com.ms.jdbc.odbc.JdbcOdbcDriver");
			DriverManager.setLogStream(System.out);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
	//
	//  Perform the Query
	//
	public void getObj (jdbcData q) throws SQLException {
	
		Connection con = DriverManager.getConnection(databaseName, "system", "manager");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(
			"Select * from  Account where primaryKey = '" + q.getKey() + "'");
		rs.next();
		rs.getString("primaryKey");
		if (rs.wasNull()) {
			throw new SQLException("No record found");
		}
		q.setBal(rs.getString("balance"));
		q.setMsg("Record retrieved");
		
	}
  //
  //    Insert object
  //
	public void insertObj (jdbcData o)
		throws SQLException {
	
		Connection con = DriverManager.getConnection(databaseName, "system", "manager");
		Statement stmt = con.createStatement();
		stmt.executeUpdate(
			"INSERT INTO Account (primaryKey, balance) VALUES (" +
	      "'" + o.getKey() + "'," + o.getBal() + ")"
		);
	}

	public Vector listAccts() throws SQLException {
		Connection con = DriverManager.getConnection(databaseName, "system", "manager");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Account");
		Vector v = new Vector();
		while (rs.next()) {
			v.addElement(new jdbcData(
			rs.getString(1),
			rs.getString(2)));
		}
		
		return v;
	}
}