import java.net.*;
import java.io.*;

/**
 * Class that introduces Sockets and exercises the protocol object
 **/
public class MultiThreadedServer {

  public static void main( String args[] ) throws Exception {
    
    // create a server side socket - same as before
    ServerSocket server = new ServerSocket( 9999 );
    
    // create myself as a daemon thread on 9999
    while( true ) {
      
      // will block automatically and hand off socket 
      Socket socket = server.accept();
      // pass socket along to the new thread
      new ThreadedSocket( socket ).start();
      // now go back to waiting while ThreadedSocket does the work
      }
    }
  }

class ThreadedSocket extends Thread {
// here is where all the real work is done.
  private Socket socket;
  
  ThreadedSocket( Socket socket ) {
    this.socket = socket;
  }
  
  public void run() {
    try{
      // once I have socket I can get its streams
      NewProtocol protocol = new NewProtocol();
      protocol.initialize(1000);
      System.out.println( "Sending protocol" );
      
      ObjectOutputStream output = new ObjectOutputStream( socket.getOutputStream() );
    
      output.writeObject( protocol );
      socket.close();
    }catch( Exception e ) {
      e.printStackTrace();
    }
  }
}

