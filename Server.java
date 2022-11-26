package socket;
import java.io.*;
import java.net.*;
public class Server {
    public void liaisonClient() throws Exception{
        ServerSocket ss = new ServerSocket(6666);
        Socket s = ss.accept(); 
        DataInputStream dis = new DataInputStream(s.getInputStream());
        String str=(String) dis.readUTF();
        System.out.println("Message : " + str);
        ss.close();
    }
}
