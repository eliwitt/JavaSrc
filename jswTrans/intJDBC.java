import java.util.Vector;
interface intJDBC { 
	public void setDebug(String stgDebug);
	public jdbcData queryJDBC(String key);
	public jdbcData insertJDBC(String key, String balance);
	public Vector getAccts();
}
