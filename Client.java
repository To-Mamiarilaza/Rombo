package socket;
import java.io.*;
import java.net.*;
public class Client {
    public void liaisonServer() throws Exception {
        Socket socket = new Socket("localhost", 6666);
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeUTF("Hello Server");
        dout.flush();
        dout.close();
        socket.close();
    }
}
