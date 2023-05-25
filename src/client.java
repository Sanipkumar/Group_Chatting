

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class client {
    private Socket socket;
       private BufferedReader bufferreader;
        private BufferedWriter bufferwriter;
        private String username;
        
        public client(Socket socket, String username) throws IOException{
            try{
                this.socket=socket;
                  this.socket=socket;
                  this.bufferwriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                  this.bufferreader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  this.username=username;
        
            }catch(IOException e){
                closeEverything(socket,bufferwriter,bufferreader);
            }
        }
        public void senmessage() throws IOException{
            try{
                bufferwriter.write(username);
                bufferwriter.newLine();
                bufferwriter.flush();
                 Scanner sc = new Scanner(System.in);
                 while(socket.isConnected()){
                       String messagetosend = sc.nextLine();
                       bufferwriter.write(username + ": " +messagetosend);
                         bufferwriter.newLine();
                          bufferwriter.flush();
                          
                 }
            }catch(IOException e){
                closeEverything(socket,bufferwriter,bufferreader);
            }
        }
        public void lishentformessage(){
            new Thread(new Runnable(){
                @Override
                public void run(){
                    String messageFromGroupChat;
                    while(socket.isConnected()){
                        try{
                           messageFromGroupChat=bufferreader.readLine();
                            System.out.println(messageFromGroupChat);
                            
                            
                        }catch(IOException e){
                            try {
                                closeEverything(socket,bufferwriter,bufferreader);
                            } catch (IOException ex) {
                                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                            }
            }
                    }
                }
            }).start();
            
                
            
        }

   private void closeEverything(Socket socket, BufferedWriter bufferwriter, BufferedReader bufferreader) throws IOException {

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
    public static void main(String[] args) throws IOException {
         Scanner sc = new Scanner(System.in);
         System.out.println("Enter your user name for group chat : ");
         String username = sc.nextLine();
          Socket socket = new Socket("localhost", 5000);
          client client = new client(socket, username);
          client.lishentformessage();
          client.senmessage();
          
    }

   
    
}
