// we will need the basic networking and I/O packages for this
import java.net.*;
import java.io.*;

/**
 * Class that introduces Sockets and exercises the protocol object
 **/
public class BasicServer {

  public static void main( String args[] ) throws Exception {
    
    // create a server side socket
    ServerSocket server = new ServerSocket( 9999 );
    
    // create myself as a daemon thread on 9999
    while( true ) {
      
      // will block automatically and hand off socket 
      Socket socket = server.accept();
      // once I have socket I can get the streams associated with it
      NewProtocol protocol = new NewProtocol();
      protocol.initialize(10);
      System.out.println( "Sending protocol" );
      
      ObjectOutputStream output = new ObjectOutputStream( socket.getOutputStream() );
      
      // write the Object       
      output.writeObject( protocol );
      // close the network connection
      socket.close();
      }
    }
  }
