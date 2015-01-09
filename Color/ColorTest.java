// This module will create several panels
import java.applet.*;
import java.awt.*;

public class ColorTest extends Applet {

   public void init() {
   
      Panel P1 = new Panel();
      P1.setLayout(new GridLayout(1,3,10,10));
      
      swatch = new Canvas();
      Color theColor = new Color(0,0,0);
      swatch.setBackground(theColor);
      
      RGBcontrols = new ColorControls(this, "Red", "Green", "Blue", 
                                 theColor.getRed(), theColor.getGreen(),
                                 theColor.getBlue());
      
      float[] HSB = Color.RGBtoHSB(theColor.getRed(), theColor.getGreen(),
                                 theColor.getBlue(), (new float[3]));
      HSBcontrols = new ColorControls(this, "Hue", "Saturation", "Brightness",
                                 (int)(HSB[0] * 360), (int)(HSB[1] * 100),
                                 (int)(HSB[2] * 100));
      
      P1.add(swatch);
      P1.add(RGBcontrols);
      P1.add(HSBcontrols);

      Panel P2 = new Panel();
      P2.setLayout(new FlowLayout(FlowLayout.CENTER));
      P2.add(new Button("Window"));
      ColorTxt = new TextField(ColorObj.TxtField, 10);
      P2.add(ColorTxt);

      setLayout(new BorderLayout());
      add("North", P1);
      add("South", P2);
            
   }

   public Insets insets() {
   
      return new Insets(10,10,10,10);
      
   }

   public boolean action(Event evt, Object arg) {

      if (evt.target instanceof Button) {
		  if (arg.equals("Window")) {
		      if (ColorWin.isShowing())
			      ColorWin.hide();
			  else
			      ColorWin.show(new ColorText(ColorTxt.getText()));
		  }
	  } else return false;

	  return true;

   }

   public void update(Object FrmTxt) {

      ColorText NewText = (ColorText)FrmTxt;
	  ColorTxt.setText(NewText.TxtField);
	  repaint();

   }

   public void update(ColorControls in) {
   
      Color c;
      
      String v1 = in.f1.getText();
      String v2 = in.f2.getText();
      String v3 = in.f3.getText();
      
      if (in == RGBcontrols) {
         c = new Color(Integer.parseInt(v1),
                  Integer.parseInt(v2),
                  Integer.parseInt(v3));
         swatch.setBackground(c);
         float[] HSB = Color.RGBtoHSB(c.getRed(), c.getGreen(),
                                 c.getBlue(), (new float[3]));
         HSB[0] *= 360;
         HSB[1] *= 100;
         HSB[2] *= 100;
         
         HSBcontrols.f1.setText(String.valueOf((int)HSB[0]));
         HSBcontrols.f2.setText(String.valueOf((int)HSB[1]));
         HSBcontrols.f3.setText(String.valueOf((int)HSB[2]));
         
      } else {
         int f1 = Integer.parseInt(v1);
         int f2 = Integer.parseInt(v2);
         int f3 = Integer.parseInt(v3);

         c = Color.getHSBColor((float)f1/360,
                  (float)f2/100,(float)f3/100);
         swatch.setBackground(c);
         
         RGBcontrols.f1.setText(String.valueOf(c.getRed()));
         RGBcontrols.f2.setText(String.valueOf(c.getGreen()));
         RGBcontrols.f3.setText(String.valueOf(c.getBlue()));
      }
      
      swatch.repaint();

   }

   private Canvas swatch;
   private ColorText ColorObj = new ColorText("Color Start");
   private ColorFrame ColorWin = new ColorFrame(this, "Popup Frame", ColorObj);
   private TextField ColorTxt;
   private ColorControls RGBcontrols, HSBcontrols;

}

class ColorControls extends Panel {

   ColorTest outerparent;
   
   TextField f1, f2, f3;
   
   ColorControls(ColorTest target,
         String I1, String I2, String I3,
         int v1, int v2, int v3) {
   
      outerparent = target;
      
      setLayout(new GridLayout(3,2,10,10));
      
      f1 = new TextField(String.valueOf(v1),10);
      f2 = new TextField(String.valueOf(v2),10);
      f3 = new TextField(String.valueOf(v3),10);

      add(new Label(I1, Label.RIGHT));
      add(f1);
      add(new Label(I2, Label.RIGHT));
      add(f2);
      add(new Label(I3, Label.RIGHT));
      add(f3);
            
   }

   public Insets insets() {
      
      return new Insets(10, 10, 0, 0);
   
   }

   public boolean action(Event evt, Object arg) {
   
      if (evt.target instanceof TextField) {
         this.outerparent.update(this);
         return true;
      } else return false;
      
   }

}

class ColorText {

    public String TxtField;

	public ColorText(String StgObj) { TxtField = StgObj; }

}

class ColorFrame extends Frame {

    public ColorFrame(ColorTest parent, String title, ColorText ClrStg) {
	    super(title);
		oldfolks = parent;

		resize(260, 220);
		setLayout(new BorderLayout());

		Menu m = new Menu("File");
		m.add(new MenuItem("Exit"));
		fm.add(m);
		setMenuBar(fm);

		Panel F1 = new Panel();
		F1.add(new Label("Color Text"));
		F1.add(FrameTxt = new TextField(ClrStg.TxtField, 10));

		add("Center", F1);

	}

	public boolean action(Event evt, Object arg) {

	    if (evt.target instanceof TextField) {
		    this.oldfolks.update(new ColorText(FrameTxt.getText()));
		} else if(evt.target instanceof MenuItem) {
			    if (arg.equals("Exit")) dispose();
		}
		else return super.action(evt, arg);

		return true;

	}

	public void show(Object ShwObj) {

	    setMenuBar(fm);
		ColorText ShwTxt = (ColorText) ShwObj;
		FrameTxt.setText(ShwTxt.TxtField);
		show();
	}

	private MenuBar fm = new MenuBar();
	private TextField FrameTxt;
	private ColorTest oldfolks;

}




