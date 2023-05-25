

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;


public class ClientHandaler implements Runnable{
    
    
public static ArrayList<ClientHandaler>clientHandalers = new ArrayList<>();
        private Socket socket;
        private BufferedReader bufferreader;
        private BufferedWriter bufferwriter;
        private String clientUsername;
        public ClientHandaler(Socket socket) throws IOException{
            try{
                this.socket=socket;
       this.bufferwriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferreader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
       this.clientUsername=bufferreader.readLine();
       clientHandalers.add(this);
       broadcastMessage("SERVER: " +clientUsername + "has enterd the chat");
       
            }catch(IOException e){
                closeEverything(socket,bufferwriter,bufferreader);
            }
            
        }
        
    @Override
    public void run() {
       String messageFromrClient;
       while(socket.isConnected()){
           try{
               messageFromrClient = bufferreader.readLine();
              broadcastMessage(messageFromrClient) ;
              
           }catch(IOException e){
               try {
                   closeEverything(socket,bufferwriter,bufferreader);
               } catch (IOException ex) {
                   Logger.getLogger(ClientHandaler.class.getName()).log(Level.SEVERE, null, ex);
               }
               
            }
           
       }
    }

    public void broadcastMessage(String messageToSend) throws IOException {
        for(ClientHandaler clientHandalers: clientHandalers ){
        try{
            if(!clientHandalers.clientUsername.equals(clientUsername)){
                clientHandalers.bufferwriter.write(messageToSend);
                clientHandalers.bufferwriter.newLine();
                clientHandalers.bufferwriter.flush();
            }
        }catch(IOException e){
                closeEverything(socket,bufferwriter,bufferreader);
               
            }
    }
    }
    public void removeClientHandaler() throws IOException{
        clientHandalers.remove(this);
  
       broadcastMessage("SERVER: " +clientUsername + "has left the chat");
    }
    

    private void closeEverything(Socket socket, BufferedWriter bufferwriter, BufferedReader bufferreader) throws IOException {
       removeClientHandaler();
       try{
           if(bufferwriter !=null){
               bufferwriter.close();
           }
            if(bufferreader !=null){
               bufferreader.close();
           }
            if(socket !=null){
               socket.close();
           }
       }catch(IOException e){
                e.printStackTrace();
               
            }
    }
    
}
