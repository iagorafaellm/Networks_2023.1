import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* Authors: João Pedro Silveira and Iago Rafael Martins */
public class UDPServer {

    // Attributes
    private DatagramSocket datagramSocket; // Represents the udp socket through which translated words will be sent to client
    private byte[] buffer; // Represents a buffer for messages to be sent to and received from the client

    // Represents the translation dictionary
    private static HashMap<String, String> dictionary = new HashMap<String, String>() {{
        put("rede", "network");
        put("roteador", "router");
        put("firewall", "firewall");
        put("protocolo", "protocol");
        put("ip", "IP");
        put("tcp", "TCP");
        put("udp", "UDP");
        put("servidor", "server");
        put("cliente", "client");
        put("gateway", "gateway");
    }};

    // Constructors

    public UDPServer(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    // Methods
    /**
     * Handles requests for translation from clients sent through UDP.
     */
    private void translationService() {

        while (true) {
            buffer = new byte[256];
            try {
                // Read word sent by the client through the udp on the datagram socket
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength()).toLowerCase();
                System.out.println("Message from client: " + messageFromClient);

                // Prepare response
                // If word is on the dictionary: response is its translation
                if (dictionary.containsKey(messageFromClient)) {
                    buffer = dictionary.get(messageFromClient).getBytes();
                    System.out.println(buffer.length);
                } else /* if not, response is not found message */ {
                    buffer = "not found".getBytes();
                }

                // Send response to the client
                datagramPacket =  new DatagramPacket(buffer, buffer.length, inetAddress, port);
                String toClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    // Main method
    public static void main(String[] args) throws SocketException {

        // Create server that connects to client on localhost through UDP
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        UDPServer udpServer = new UDPServer(datagramSocket);

        // Execute translation service
        System.out.println("Server executing...");
        udpServer.translationService();
    }
}
