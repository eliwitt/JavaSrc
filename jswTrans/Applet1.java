import java.awt.*;
import java.applet.*;
import java.util.Vector;
import java.util.Enumeration;

/**
 * This class reads PARAM tags from its HTML host page and sets
 * the color and label properties of the applet. Program execution
 * begins with the init() method. 
 */
public class Applet1 extends Applet
{
	/**
	 * The entry point for the applet. 
	 */
	public void init()
	{
		initForm();

		usePageParams();

		// TODO: Add any constructor code after initForm call.
	}

	private	final String labelParam = "label";
	private	final String backgroundParam = "background";
	private	final String foregroundParam = "foreground";
	private final String debugParam = "debug";
	private String debug;

	/**
	 * Reads parameters from the applet's HTML host and sets applet
	 * properties.
	 */
	private void usePageParams()
	{
		final String defaultLabel = "Default label";
		final String defaultBackground = "C0C0C0";
		final String defaultForeground = "000000";
		final String defaultDebug = "0";
		String labelValue;
		String backgroundValue;
		String foregroundValue;

		/** 
		 * Read the <PARAM NAME="label" VALUE="some string">,
		 * <PARAM NAME="background" VALUE="rrggbb">,
		 * and <PARAM NAME="foreground" VALUE="rrggbb"> tags from
		 * the applet's HTML host.
		 */
		labelValue = getParameter(labelParam);
		backgroundValue = getParameter(backgroundParam);
		foregroundValue = getParameter(foregroundParam);
		debug = getParameter(debugParam);
			/**
			 * There was something wrong with the HTML host tags.
			 * Generate default values.
			 */		
		if (labelValue == null) labelValue = defaultLabel;
		if (backgroundValue == null) backgroundValue = defaultBackground;
		if (foregroundValue == null) foregroundValue = defaultForeground;
		if (debug == null) debug = defaultDebug;
		jdbcCon.setDebug(debug);
		/**
		 * Set the applet's string label, background color, and
		 * foreground colors.
		 */
		label1.setText(labelValue);
		label1.setBackground(stringToColor(backgroundValue));
		label1.setForeground(stringToColor(foregroundValue));
		this.setBackground(stringToColor(backgroundValue));
		this.setForeground(stringToColor(foregroundValue));
	}

	/**
	 * Converts a string formatted as "rrggbb" to an awt.Color object
	 */
	private Color stringToColor(String paramValue)
	{
		int red;
		int green;
		int blue;

		red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
		green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
		blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();

		return new Color(red,green,blue);
	}

	/**
	 * External interface used by design tools to show properties of an applet.
	 */
	public String[][] getParameterInfo()
	{
		String[][] info =
		{
			{ labelParam, "String", "Label string to be displayed" },
			{ backgroundParam, "String", "Background color, format \"rrggbb\"" },
			{ foregroundParam, "String", "Foreground color, format \"rrggbb\"" },
			{ debugParam, "String", "Value set to display messages"},
		};
		return info;
	}

	Label label1 = new Label();
	Label statusMsg = new Label();
	TextField keyFld, balFld;
	List listFld;
	
	/**
	 * Intializes values for the applet and its components
	 */
	void initForm()
	{
		Panel inputPnl;
		Label lblPnl;
		
		this.setBackground(Color.lightGray);
		this.setForeground(Color.black);
		label1.setText("");
		this.setLayout(new BorderLayout());
		this.add("North",label1);
		
		inputPnl = new Panel();
		lblPnl = new Label("Key: ");
		inputPnl.add(lblPnl);
		inputPnl.add(keyFld = new TextField(30));

		lblPnl = new Label("Balance: ");
		inputPnl.add(lblPnl);
		inputPnl.add(balFld = new TextField(20));
		lblPnl = new Label("Message: ");
		inputPnl.add(lblPnl);
		statusMsg.setText("Please your data then press a button");
		inputPnl.add(statusMsg);
		inputPnl.add(listFld = new List(10));
		this.add("Center", inputPnl);
		
		inputPnl = new Panel();
		inputPnl.add(new Button("Query"));
		inputPnl.add(new Button("Insert"));
		inputPnl.add(new Button("List"));
		inputPnl.add(new Button("Clear"));
		this.add("South", inputPnl);
		
	}
	
//----------------------------------------------------------------
//  action 
//  this function is called by handlevent once the user has pressed
//  or performed some type of action in the applet.
//	
//  This function will perform actions based on the following:
//
//             Query  - Perform a query on the db based on the value 
//                      entered in the "Key" field.
//
//             Insert - Perform an insert of the record based on the 
//                      entered in both the key and balance field.
//
//             Clear  - Clears the text from the input fields.
//
//----------------------------------------------------------------
	intJDBC jdbcCon = new conJDBC();
	jdbcData dataObj = new jdbcData();
	
	public boolean action(Event p1, Object p2)
	{
		// TODO: Add your own implementation.
		if (p2.equals("Query")) {
			jdbcData dataObj = jdbcCon.queryJDBC(keyFld.getText());
			balFld.setText(dataObj.getBal());			statusMsg.setText(dataObj.getMsg());
		} else if (p2.equals("Insert")) {
			jdbcData dataObj = jdbcCon.insertJDBC(keyFld.getText(), balFld.getText());			statusMsg.setText(dataObj.getMsg());
		} else if (p2.equals("Clear")) {
			statusMsg.setText("You hit the clear key");			keyFld.setText("");			balFld.setText("");
			listFld.removeAll();		} else if (p2.equals("List")) {		    try {
				Enumeration listAcct  = jdbcCon.getAccts().elements();
				while (listAcct.hasMoreElements()) {
			        jdbcData oi = (jdbcData) listAcct.nextElement();
			        listFld.addItem(oi.getKey() + " " + oi.getBal());
				}
				statusMsg.setText("Here is your list");
		    } catch (Exception e) {
				e.printStackTrace();
		    }
		} else 			return super.action(p1, p2);
		return true;
	}


}
