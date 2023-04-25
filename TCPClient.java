import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class TCPClient {

    public static void main(String arg[]) throws Exception
    {
        Socket clientSocket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            // Establish connection with server
            clientSocket = new Socket( "localhost", 1234); // file descriptor for socket
            System.out.println("Connection established with " + clientSocket.getInetAddress());

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner(System.in);

            // Start executing messaging function
            while (true) {
                // Read message from user´s input
                System.out.print("Word: ");
                String messageToBeSent = scanner.nextLine();

                // Send user´s message through socket
                bufferedWriter.write(messageToBeSent);
                bufferedWriter.newLine();
                bufferedWriter.flush(); // send

                // If user sent break message, end messenger mode and close connection with the server
                if (messageToBeSent.equalsIgnoreCase("break")) {
                    System.out.println("Closing connection with server");
                    break;
                }

                // Read server´s answer
                System.out.println("Translation: " + bufferedReader.readLine());
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

    public static void loadDictionary(String fileName) {

    }

}