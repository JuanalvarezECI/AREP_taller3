package edu.escuelaing.arem.ASE.app.Server;

import edu.escuelaing.arem.ASE.app.MicroSpringboot;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class SimpleWebServer {
    private final int port;

    public SimpleWebServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            executor.submit(() -> handleRequest(clientSocket));
        }
    }

    private void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine != null && !requestLine.isEmpty()) {
                String[] requestParts = requestLine.split(" ");
                String method = requestParts[0];
                String path = requestParts[1];

                // Handle the request using reflection (to be implemented)
                String response = handlePath(path);

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println("Content-Length: " + response.length());
                out.println();
                out.println(response);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handlePath(String path) {
            try {
                Map<String, String> params = new HashMap<>();
                if (path.contains("?")) {
                    String[] parts = path.split("\\?");
                    path = parts[0];
                    String[] paramPairs = parts[1].split("&");
                    for (String pair : paramPairs) {
                        String[] keyValue = pair.split("=");
                        params.put(keyValue[0], keyValue[1]);
                    }
                }
                return MicroSpringboot.handleRequest(path, params);
            } catch (Exception e) {
                e.printStackTrace();
                return "500 Internal Server Error";
            }
    }

    public static void main(String[] args) throws IOException {
        SimpleWebServer server = new SimpleWebServer(8080);
        server.start();
    }
}