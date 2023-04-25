import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TCPServer {
    private static Map<String, String> dictionary = new HashMap<String, String>() {{
        put("rede", "network");
        put("roteador", "router");
        put("firewall", "firewall");
        put("protocolo", "protocol");
        put("IP", "IP");
        put("TCP", "TCP");
        put("UDP", "UDP");
        put("servidor", "server");
        put("cliente", "client");
        put("gateway", "gateway");
    }};
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

                System.out.println("Connection established with " + clientSocket.getInetAddress());

                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                Scanner scanner = new Scanner(System.in);

                // While connection with client is active: read messages and handle requests
                while (true) {

                    // Read message sent by the client to the server
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println("Message from client: " + messageFromClient);

                    // Send confirmation that the message was received
                    // bufferedWriter.write("Message received.");
                    // bufferedWriter.newLine();
                    // bufferedWriter.flush();

                    // If client wants to disconnect, exit the request handling mode
                    if (messageFromClient.equalsIgnoreCase("break")) {
                        System.out.println("Client asked to disconnect.");
                        System.out.println("Closing connection with " + clientSocket.getInetAddress());
                        break;
                    }

                    // If word sent by the client is in the dictionary, send its translation to the client
                    System.out.print("Input validation: ");
                    if (dictionary.containsKey(messageFromClient)) {
                        System.out.println(messageFromClient + " was found in the database.");
                        System.out.println("Sending response.");

                        // Send translation
                        bufferedWriter.write(dictionary.get(messageFromClient));
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } else {
                        System.out.println(messageFromClient + " was not found in the database.");
                        System.out.println("Sending response.");

                        // Send translation
                        bufferedWriter.write("Error: Word not found in the database.");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
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