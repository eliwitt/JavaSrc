import java.awt.*;
import java.applet.*;

public class ButtonTest extends Applet implements DialInface {

        public void init() {

		        setLayout(new BorderLayout());

                Panel p = new Panel();
                p.setLayout(new FlowLayout());
                p.add(new Button("Yes"));
                p.add(new Button("No"));
                p.add(new Button("Ok"));
                p.add(new Button("Cancel"));
                p.add(new Button("Abort"));
                p.add(new Button("Retry"));
                p.add(new Button("Ignore"));
                add("North", p);

                Panel ButPnl = new Panel();
                ButText = new TextField("Select", 8);
                ButPnl.add(ButText);
                DlText = new TextField("Dial", 10);
                ButPnl.add(DlText);
                add("Center", ButPnl);

                Panel EndPnl = new Panel();
                EndPnl.setLayout(new FlowLayout());
                EndPnl.add(new Button("About"));
				EndPnl.add(new Button("DialBx"));
                add("South", EndPnl);

        }

        public boolean handleEvent(Event evt) {

                if (evt.id == Event.WINDOW_DESTROY && evt.target == this) System.exit(0);
                return super.handleEvent(evt);
               

        }

        public boolean action(Event evt, Object arg) {

                if (arg.equals("Yes")) { 
                        ButText.setBackground(Color.yellow);
                        ButText.setText("Yes");
                }
                else if (arg.equals("No")) { 
                        ButText.setBackground(Color.blue);
                        ButText.setText("No");
                }
                else if (arg.equals("Ok")) { 
						ButText.setBackground(Color.green);
                        ButText.setText("Ok");
                }
                else if (arg.equals("Cancel")) {
                        ButText.setBackground(Color.cyan);
                        ButText.setText("Cancel");
                }
                else if (arg.equals("Abort")) {
                        ButText.setBackground(Color.gray);
                        ButText.setText("Abort");
                }
                else if (arg.equals("Retry")) {
                        ButText.setBackground(Color.red);
                        ButText.setText("Retry");
                }
                else if (arg.equals("Ignore")) {
                        ButText.setBackground(Color.white);
                        ButText.setText("Ignore");
                }
                else if (arg.equals("About")) {
                        if (ab.isShowing()) ab.hide();
                        else ab.show();
                }
				else if (arg.equals("DialBx")) {
				        if (DlBx.isShowing()) DlBx.hide();       
						else DlBx.show();
				}
                else if (arg.equals("Close")) System.exit(0);
                else return false;

                repaint();
                return true;

        }
        
        public void DialRetrieve(Frame DlSrc, Object DlRt) {
                if (DlSrc instanceof DialBox) {
                     DialInfo NewInfo = (DialInfo)DlRt;
                     DlText.setText(NewInfo.DlField);
                 }
				 repaint();				
        }

		private DialInfo DlObj = new DialInfo("Steve");
		private Frame DlBx = new DialBox(this, DlObj);
		private Frame ab = new AboutDialog();
        private TextField ButText;
        private TextField DlText;
        

}

interface DialInface { public void DialRetrieve(Frame DlSrc, Object DlRt); }

class DialInfo {

         public String DlField;
         
         public DialInfo(String StgObj) { DlField = StgObj; }
         
}

class DialBox extends Frame {

         public DialBox(ButtonTest parent, DialInfo DlStg) {
        
                setTitle("Connect");
		        setLayout(new BorderLayout());
				this.AppletParent = parent;
                resize(260, 220);

                Panel DlPnl1 = new Panel();
                DlPnl1.setLayout(new GridLayout(2, 2));
                DlPnl1.add(new Label("Dial Text:"));
                DlPnl1.add(DlPnlStg = new TextField(DlStg.DlField, 10));
                add("Center", DlPnl1);
                
                Panel DlPnl2 = new Panel();
                DlPnl2.add(new Button("Ok"));
                DlPnl2.add(new Button("Cancel"));
                add("South", DlPnl2);

          }
          
          public boolean action(Event evt, Object arg) {
          
                if (arg.equals("Ok")) {
					 dispose();
                     this.AppletParent.DialRetrieve(this, new DialInfo(DlPnlStg.getText()));
                } else if (arg.equals("Cancel")) dispose();
                else return super.action(evt, arg);
                return true;
                
          }

         public boolean handleEvent(Event evt) {
         
                if (evt.id == Event.WINDOW_DESTROY) dispose();
                else return super.handleEvent(evt);
                return true;
         }
         
         private TextField DlPnlStg;
		 ButtonTest AppletParent;
         
}

class AboutDialog extends Frame {

         public AboutDialog() {
         
		        setTitle("Dialog");
				setLayout(new BorderLayout());
				resize(350, 200);

                Panel p1 = new Panel();
                p1.add(new Label("CoreJava"));
                p1.add(new Label("This app tests the panel, layout, and dialog features."));
                add("Center", p1);
                
                Panel p2 = new Panel();
                p2.add(new Button("Ok"));
                add("South", p2);
                
          }
          
          public boolean action(Event evt, Object arg) {
          
                 if(arg.equals("Ok")) {
                     dispose();
                     return true;
                 }
                 return false;
                 
           }
           
           public boolean handleEvent(Event evt) {
           
                  if (evt.id == Event.WINDOW_DESTROY && evt.target == this) dispose();
                  
                  return super.handleEvent(evt);
                  
           }

}
