package SMenu;
public class MenuBean {
	private String browserVersion;
	private String browserType;
	//saves the browser name
	//saves the browser name
	public void setBrowserType(
		String btype) {
		browserType = btype; 
	}
	public String getBrowserType() {
		return browserType;
	}
	//saves the browser version
	public void setBrowserVersion(
		String bversion) {
		browserVersion = bversion;
	}
	public String getBrowserVersion() {
		return browserVersion;
	} 

	public String getBrowserMenu() {
		int i = browserType.indexOf("Netscape");
		
		if (i>=0)
			return "NSMenus.js";
		else
			return "IEMenus.js";
	}
}