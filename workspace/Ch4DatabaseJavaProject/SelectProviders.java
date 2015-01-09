/*
 * Created on Dec 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * @author j13155w
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SelectProviders {

	public static void main(String[] args) {
		
		try {
			Class.forName("com.sybase.jdbc2.jdbc.SybDriver");
			String url = "jdbc:sybase:Tds:CPSDVLP:12075/facets_dvlp_01";
			
			Connection cn =
				DriverManager.getConnection(url, "faoper", "test69");
			Statement stmt = cn.createStatement();
			ResultSet rs = 
				stmt.executeQuery("set ROWCOUNT 10 Select PROV.PRPR_ID, PROV.PRPR_NAME FROM dbo.CMC_PRPR_PROV PROV");
			while (rs.next()) {
				String prId = rs.getString("PRPR_ID");
				String pName = rs.getString("PRPR_NAME");
				System.out.println(prId + " " + pName);
			}
			rs.close();
			stmt.close();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
