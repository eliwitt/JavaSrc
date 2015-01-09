
class jdbcData {
	private String key;
	private String balance;
	private String statusMsg;
	public jdbcData() {}
	public jdbcData(String key, String balance) {
		setKey(key);
		setBal(balance);
	}
	public String getKey() { return key; }
	public void setKey(String sKey) { key = sKey; }
	public String getBal() { return balance; }
	public void setBal(String sBal) { balance = sBal; }
	public String getMsg() { return statusMsg; }
	public void setMsg(String sMsg) { statusMsg = sMsg; }
}
