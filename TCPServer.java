import java.io.*;
import java.net.*;

// Server that accepts a string from a client, converts it to upper case, and returns it to the client.

class TCPServer {
    public static void main(String argv[]) throws Exception
    {
        String clintSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clintSentence = inFromClient.readLine();
            capitalizedSentence = clintSentence.toUpperCase() + '\r';
            outToClient.writeBytes(capitalizedSentence);
            // welcomeSocket.close(); // This is not needed, because the server is always listening.
        }
    }
}