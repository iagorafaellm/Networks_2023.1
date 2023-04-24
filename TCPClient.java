import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client that sends a string to a server, and prints the modified string returned by the server.

class TCPClient {
    public static void main(String arg[]) throws Exception
    {
        Socket clientSocket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            // Establish connection
            clientSocket = new Socket( "localhost", 1234); // file descriptor for socket

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            // Start executing messaging function
            while (true) {
                // Read message from user´s input
                System.out.println("Message: ");
                String messageToBeSent = scanner.nextLine();

                // Send user´s message through socket
                bufferedWriter.write(messageToBeSent);
                bufferedWriter.newLine();
                bufferedWriter.flush(); // send

                // Read server´s answer
                System.out.println("Message from server: " + bufferedReader.readLine());

                if (messageToBeSent.equalsIgnoreCase("BREAK")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close connection and related data structures and objects
            try {
                if (clientSocket != null) clientSocket.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (outputStreamWriter != null) outputStreamWriter.close();
                if (bufferedReader != null) bufferedReader.close();
                if (bufferedWriter != null) bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}