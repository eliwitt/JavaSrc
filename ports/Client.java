// we will need the same networking and I/O Packages
import java.net.*;
import java.io.*;

/**
 * Class that shows connecting to a server and carrying on a protocol
 * @see network.Server
 **/
public class Client {

  public static void main( String args[] ) throws Exception {
      
    // open a socket to the host
    Socket socket = new Socket( "127.0.0.1", 9999 );
    // convention is that the host Address 127.0.0.1 refers to me
      
    ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );
      
    // read using serialization
    NewProtocol protocol = (NewProtocol)(input.readObject() );
      
    socket.close();
    }
  }
