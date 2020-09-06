package udp;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Date;

/**
 * @author Dora Di
 */
public class UDPServer {
    private static final int serverPort = 7777;

    // buffers for the messages
    private static byte[] dataIn = new byte[10000000];
    private static byte[] dataOut = new byte[10000000];

    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;
    private static DatagramPacket responsePacket;
    private static DatagramSocket serverSocket;

    // folder for imgs, uses project root path + imgs folder
    private static String imgDir = System.getProperty("user.dir") + "\\imgs";


    public static void main(String[] args) throws Exception {
        try {
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            // Opens socket for accepting requests
            serverSocket = new DatagramSocket(serverPort);
            while (true) {
                System.out.println("Server " + serverIP + " running ...");
                receiveRequest();
                sendResponse("img received");
            }
        } catch (Exception e) {
            System.out.println(" Connection fails: " + e);
        } finally {
            serverSocket.close();
            System.out.println("Server port closed");
        }
    }

    public static void receiveRequest() throws IOException {
        requestPacket = new DatagramPacket(dataIn, dataIn.length);
        serverSocket.receive(requestPacket);

        ByteArrayInputStream bis = new ByteArrayInputStream(requestPacket.getData());
        BufferedImage image = ImageIO.read(bis);
        Date date = new Date(); // This object contains the current date value
        ImageIO.write(image, "jpg", new File(imgDir + "\\test_" + date.getTime() + ".jpg"));
    }

    public static String processRequest(String message) {
        return message.toUpperCase();
    }

    public static void sendResponse(String message) throws IOException {
        InetAddress clientIP;
        int clientPort;

        clientIP = requestPacket.getAddress();
        clientPort = requestPacket.getPort();
        System.out.println("Client port: " + clientPort);
        System.out.println("Response: " + message);
        dataOut = message.getBytes();
        responsePacket = new DatagramPacket(dataOut, dataOut.length, clientIP, clientPort);
        serverSocket.send(responsePacket);
        System.out.println("Message sent back " + message);
    }
}