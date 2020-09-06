package tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dora Di
 * <p>
 * 1. Create a server socket and bind it to a specific port number
 * 2. Listen for a connection from the client and accept it. This results in a client socket, created on the server, for the connection.
 * 3. Read data from the client via an InputStream obtained from the client socket
 * 4. Send data to the client via the client socket’s OutputStream.
 * 5. Close the connection with the client.
 * <p>
 * The steps 3 and 4 can be repeated many times depending on the protocol agreed between the server and the client.
 */

public class TCP {
    public static final int PORT = 6666;
    public static ServerSocket serverSocket = null; // Server gets found
    public static Socket openSocket = null;         // Server communicates with the client

    public static void main(String[] args) throws IOException {
        try {
            configureServer();
        } catch (Exception e) {
            System.out.println(" Connection fails: " + e);
        }

    }

    public static void configureServer() throws UnknownHostException, IOException {
        // get server's own IP address
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Server ip: " + serverIP);

        // create a socket at the predefined port
        serverSocket = new ServerSocket(PORT);

        // create a server task
        var serverTask = new ServerTask(serverSocket);

        // create a thread
        var serverThread = new Thread(serverTask);
        serverThread.start();

        System.out.println("Server port: " + PORT);
    }
}


class ServerTask implements Runnable {

    public ServerTask(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    private final ServerSocket serverSocket;

    // create a pool of threads, allowing multiple clients
    final ExecutorService clientThreadPool = Executors.newFixedThreadPool(5);



    @Override
    public void run() {
        try {
            // keep task running forever
            while (true) {
                // Start listening and accepting requests on the serverSocket port, blocked until a request arrives
                var clientSocket = serverSocket.accept();
                // submit task to clientThreadPool
                clientThreadPool.submit(new ClientTask(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientTask implements Runnable {

    public ClientTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
        System.out.println("New Client connected at socket: " + clientSocket);
    }
    private final Socket clientSocket;

    @Override
    public void run() {
        try {

            String request, response;
            // two I/O streams attached to the server socket:
            Scanner in;         // Scanner is the incoming stream (requests from a client)
            PrintWriter out;    // PrintWriter is the outcoming stream (the response of the server)
            in = new Scanner(clientSocket.getInputStream());
            // Parameter true ensures automatic flushing of the output buffer
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Server keeps listening for request and reading data from the Client,
            // until the Client sends "stop" requests
            while (in.hasNextLine()) {
                request = in.nextLine();

                if (request.equals("stop")) {
                    out.println("Good bye, client!");
                    System.out.println("Log: " + request + " client!");
                    break;
                } else {
                    // server responses
                    response = new StringBuilder(request).reverse().toString();
                    out.println(response);
                    // Log response on the server's console, too
                    System.out.println("Client ID:" + clientSocket.getPort() + " - Log: " + response);
                }
            }
            clientSocket.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            System.out.println("Connection to client closed at socket: " + clientSocket);
        }

    }
}