// we'll need the basic I/O libraries here.
import java.io.*;
/**
 * Class that defines a basic serializable protocol handler
 **/

public class NewProtocol implements Serializable{
  // some data to send, as Strings
  private String[] sendData;
  private int numStrings = 0;
  
  /**
   * initialize a NewProtocol object and its strings
   * to be as many as dataLength
   * @param dataLength The number of strings to initialize
   **/
  public void initialize( int dataLength ) {
    sendData = new String[dataLength];
    numStrings = dataLength;
    for( int x=0; x<dataLength; x++ ) {
      sendData[x] = new String( "Data String: " + x );
      }
    }

  /**
   * implement the write portion of the protocol handler
   **/
  public void writeProtocol( ObjectOutputStream output ) throws IOException {
    
    // write out number of strings
    output.writeInt( sendData.length );
    
    // write out each string as bytes with a newline
    for( int x=0; x<sendData.length; x++ ) {
      output.writeBytes( sendData[x] + "\n" );
      }
    }
  
  /**
   * Implement the read portion of the protocol
   **/
  public void readProtocol( ObjectInputStream input ) throws IOException {
    // first get the number of strings to expect
    int dataLen = input.readInt();
    // convert input stream into a reader stream
    BufferedReader br = new BufferedReader(new InputStreamReader(input));   

    System.out.println( "Reading: " + numStrings + " Strings.");
    // allocate a sufficient array
    sendData = new String[ numStrings ];
    
    // read in the strings one at a time
    for( int x=0; x<numStrings; x++ ) {
      sendData[x] = br.readLine();
      }
    }
  } 
