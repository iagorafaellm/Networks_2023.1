import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/* Authors: João Pedro Silveira and Iago Rafael Martins */
public class UDPClient {
    // Attributes
    private DatagramSocket datagramSocket; // Represents the udp socket through which words will be sent to server
    private InetAddress inetAddress; // Represents the IP address of the server
    private byte[] buffer; // Represents a buffer for messages to be sent to and received from the sever

    // Constructors
    public UDPClient(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    // Methods

    /**
     * Asks the user for input and sends it for translation on server on localhost using UDP.
     */
    private void translationService() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                // Read word from user input
                System.out.print("Word: ");
                String messageToSend = scanner.nextLine();

                if(messageToSend.equalsIgnoreCase("break")) {
                    break;
                }

                // Send the word for translation on the server
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);
                datagramSocket.send(datagramPacket);

                // Read server response and output translation
                buffer = new byte[256];
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Translation: " + messageFromServer);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        datagramSocket.close();
    }

    // Main method
    public static void main(String[] args) throws UnknownHostException, SocketException {

        // Create client that connects to server on localhost through UDP
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        UDPClient udpClient = new UDPClient(datagramSocket, inetAddress);

        // Execute translation service
        System.out.println("Client executing...");
        udpClient.translationService();
    }
}
