/*
 * Created on Dec 13, 2007
 *
 * This class handles the data base retrieval of the subscriber information.
 *
 */
package com.bcbst.ememberpayweb;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class DbSubscriber {

	private static Logger _logger = Logger.getLogger(DbSubscriber.class);	
	
	/**
	 * 
	 * @param eSub
	 */
	public void retrieveSubscriber(Subscriber eSub) {
		
		_logger.info("retrieveSubscriber --> Begin");
		long startTime = System.currentTimeMillis();
		
		String spReturn = null;
		InitialContext ctx = null;
		Hashtable parms = new Hashtable();
		parms.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.ibm.websphere.naming.WsnInitialContextFactory");
		parms.put(Context.PROVIDER_URL, "iiop:///");
		
		/* Change the code to call the stored procedure exec CPSSP_VRU_MAIN '902231492' */
		javax.sql.DataSource ds = null;
		
		try {
			String sbsbId = null;
			ctx = new InitialContext(parms);
//			ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/localds1");
			ds = getDataSource();		
			Connection conn = ds.getConnection();

			/* you should use a prepare statement and bind variables. open ended variable are subject to SQL injection.
			 * not in your case, but it is good to get the correct syntax now. 
			 * */
//			String sqlSBSB = "select SBSB_ID from CMC_SBSB_SUBSC where SBSB_CK=(" +
//						"select mm.SBSB_CK from ER_TB_SYST_EXPM_XPERMISSIONS xpm, CMC_MEME_MEMBER mm where xpm.EXTU_ID='" +
//						eSub.getSubid() + "' " +
//						"and xpm.EXPM_DATA_ID='MEME_CK' " + 
//						"and convert(int,xpm.EXPM_DATA)=mm.MEME_CK group by mm.SBSB_CK)";		
			
			String sqlSBSB = "select SBSB_ID from CMC_SBSB_SUBSC where SBSB_CK=(" +
				"select mm.SBSB_CK from ER_TB_SYST_EXPM_XPERMISSIONS xpm, CMC_MEME_MEMBER mm where xpm.EXTU_ID=? " +
				"and xpm.EXPM_DATA_ID='MEME_CK' " + 
				"and convert(int,xpm.EXPM_DATA)=mm.MEME_CK group by mm.SBSB_CK)";	
			
			_logger.debug("retrieveSubscriber --> SQL: " + sqlSBSB);
			
			//  Retrieve the SBSB id using the userid
//			Statement sbsbStmt = conn.createStatement();
//			ResultSet sbsbRs = sbsbStmt.executeQuery(sqlSBSB);	
			PreparedStatement ps = conn.prepareStatement(sqlSBSB);
			ps.setString(1, eSub.getSubid());	// set the argument specified with the first bind variable ?
			ResultSet sbsbRs = ps.executeQuery();
			while(sbsbRs.next()) {
				sbsbId = sbsbRs.getString("SBSB_ID");
				break;
			}
			
			if (sbsbId == null) {
				_logger.error("retrieveSubscriber --> The userid does not have a matching Subscriber");				
				eSub.setAmtdue(0);
		       	eSub.setBsrc("FF");
	        	eSub.setBsmsg("Userid Error ");
	        	eSub.setBserror("The userid does not have a matching Subscriber.");
	        	return;
			}
			
			eSub.setSubid(sbsbId);
			String sqlCPSSP = "{Call CPSSP_VRU_MAIN(?)}";
			_logger.debug("retrieveSubscriber --> CPSSP_VRU_MAIN");
			CallableStatement stmt = conn.prepareCall(sqlCPSSP);
			stmt.setString(1,eSub.getSubid());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				spReturn = rs.getString("Return_Code");
				if (spReturn.equalsIgnoreCase("0")) {
					eSub.setGroupid(rs.getString("Group_Number"));
					eSub.setAmtdue(rs.getDouble("Balance_Due"));
					eSub.setEligibilInd(rs.getString("Eligibility_Ind"));
					eSub.setBillfrom(rs.getString("Last_Pymt_Date"));
					eSub.setLastdue(rs.getDouble("Last_Pymt_Amt"));
					eSub.setDuedate(rs.getString("Billing_Due_Date"));
					eSub.setBsrc("AA");
				} else {
					eSub.setBsrc("FF");
					eSub.setBsmsg("Userid Error ");
					eSub.setBserror("The userid is not setup as a Subscriber.");
					_logger.error("retrieveSubscriber --> The userid is not setup as a Subscriber.");					
				}
				break;
			}
			
			cleanup(rs, stmt, conn);
//			stmt.close();
//			conn.close();
		} catch (Exception e) {
			_logger.error("retrieveSubscriber --> Exception caught", e);
	       	eSub.setBsrc("FF");
        	eSub.setBsmsg("SYBase error ");
        	eSub.setBserror(e.getMessage());
		}
		_logger.info("retrieveSubscriber --> End (runtime=" + (((System.currentTimeMillis() - startTime) / 1000) / 60) + " minutes)");		
	}
	
	/**
	 * Returns a reference to the data source
	 * @return a data source
	 * @throws NamingException
	 */
	protected DataSource getDataSource() throws NamingException {
		DataSource ds = null;
		try {
			Context context = new InitialContext();
			_logger.debug("getDataSource --> Context lookup for java:comp/env/jdbc/localds1");				
			ds = (DataSource) context.lookup("java:comp/env/jdbc/localds1");
		} catch(NamingException e) {
			_logger.error("getDataSource --> Received naming exception for java:comp/env/jdbc/localds1 (context not defined)", e);
			throw e;
		}
		return ds;
	}	
	
	/**
	 * 
	 * @param rs
	 * @param stmt
	 * @param connection
	 */
	protected void cleanup(ResultSet rs, Statement stmt, Connection connection) throws Exception {
		
		_logger.debug("cleanup -> Cleaning up database resources");
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {}
		}
		
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {}
		}
		
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {}
		}
	}	
}
