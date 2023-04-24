import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPServer {
    public static void main(String argv[]) throws Exception
    {
        Socket clientSocket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = new ServerSocket(1234);

        while (true) {
            try {
                // Wait for connections and establish one with a client
                clientSocket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);

                outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                Scanner scanner = new Scanner(System.in);

                while (true) {
                    // Read message sent by the client to the server
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println("Message from client: " + messageFromClient);

                    // Send confirmation that the message was recieved
                    bufferedWriter.write("Message recieved.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush(); // send

                    // Check if the client wants to disconnect
                    if (messageFromClient.equalsIgnoreCase("BREAK")) break;

                }

                // If client asked to disconnect, close connection and wait for new one
                try {
                    if (clientSocket != null) clientSocket.close();
                    if (inputStreamReader != null) inputStreamReader.close();
                    if (outputStreamWriter != null) outputStreamWriter.close();
                    if (bufferedReader != null) bufferedReader.close();
                    if (bufferedWriter != null) bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}