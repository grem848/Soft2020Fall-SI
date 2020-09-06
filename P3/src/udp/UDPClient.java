package udp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author Dora Di
 */
public class UDPClient {
    // Client needs to know server identification, <IP:port>
    private static final int serverPort = 7777;

    // buffers for the messages
    public static String message;
    private static byte[] dataIn = new byte[256];
    private static byte[] dataOut = new byte[256];
    private static String imgPath;
    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;
    private static DatagramPacket responsePacket;
    private static DatagramSocket clientSocket;

    public static void main(String[] args) throws IOException {
        clientSocket = new DatagramSocket();
//        InetAddress serverIP = InetAddress.getByName(args[0]);
        InetAddress serverIP = InetAddress.getLocalHost();
        System.out.println(serverIP);
        imgPath = args[0];

        sendRequest(serverIP);
        receiveResponse();
        clientSocket.close();
    }

    public static void sendRequest(InetAddress serverIP) throws IOException {
        System.out.println(imgPath);

        BufferedImage img = ImageIO.read(new File(imgPath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ImageIO.write(img, "jpg", bos);
        dataOut = bos.toByteArray();

        requestPacket = new DatagramPacket(dataOut, dataOut.length, serverIP, serverPort);
        System.out.println(dataOut.length);
        clientSocket.send(requestPacket);
    }

    public static void receiveResponse() throws IOException {
        responsePacket = new DatagramPacket(dataIn, dataIn.length);
        clientSocket.receive(responsePacket);
        String message = new String(responsePacket.getData(), 0, responsePacket.getLength());
        System.out.println("Response from Server: " + message);
    }
}
