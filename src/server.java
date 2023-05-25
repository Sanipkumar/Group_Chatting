
import java.io.*;
import java.net.*;
public class server {
    
    private ServerSocket serversocket;

    public server(ServerSocket serversocket ) {
        this.serversocket=serversocket;
    }
public void startServer(){
    try{
    while(!serversocket.isClosed()){
      Socket socket= serversocket.accept();
        System.out.println("A new client is connected");
        ClientHandaler clienthandaler = new ClientHandaler(socket);
        Thread thread = new Thread(clienthandaler);
        thread.start();
    }
    }catch(IOException e){
        
    }
}
  public void clooseServerSocket(){
      try{
          if(serversocket!=null){
              serversocket.close();
          }
      }catch(IOException e){
          e.printStackTrace();
      }
  } 
    public static void main(String[] args) throws IOException {
           ServerSocket serversocket = new ServerSocket(5000);
          server server = new server(serversocket);
          server.startServer();
          
           
           
    }
}
