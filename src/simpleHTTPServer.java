//import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
public class simpleHTTPServer{

    private static boolean running = true;

    public static void main(String[] args) throws Exception{
                final ServerSocket server = new ServerSocket(12345);
        System.out.println("Listening on port 12345");
        Thread inThread = new Thread(){
            public void run(){
                try(Scanner sysIn = new Scanner(System.in)){
                    String command;
                    while(running){
                        command = sysIn.next();
                        
                        switch (command) {
                            case "exit":
                                try {
                                    server.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.exit(0);
                            default:
                                break;
                        }
                    }
                }
            }
        };
        inThread.start();
        while (true){
            Socket client = server.accept();
            //loop forever baby
            // InputStreamReader i = new InputStreamReader(client.getInputStream());
            // BufferedReader reader = new BufferedReader(i);
            // String line = reader.readLine();
            // while(!line.isEmpty()){
            //     System.out.println(line);
            //     line = reader.readLine();
            // }

        //     try (Socket socket = server.accept()) {
        //         LocalDate today = LocalDate.now();
        //         String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
        //         socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        // }
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String requestLine = reader.readLine();
                String[] requestParts = requestLine.split(" ");
                String path = requestParts[1];

                if (path.endsWith(".css")) {
                
                    String cssFilePath = "src/style.css";
                    File cssFile = new File(cssFilePath);
                    FileInputStream cssFileInputStream = new FileInputStream(cssFile);
                    PrintWriter out = new PrintWriter(client.getOutputStream());

                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/css");
                    out.println("\r\n");
                    out.flush();

                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = cssFileInputStream.read(buffer)) != -1) {
                        out.write(new String(buffer, 0, bytesRead));
                        out.flush();
                    }

                    cssFileInputStream.close();
                    out.close();
                } else {
                
                    String htmlFilePath = "src/index.html";
                    File htmlFile = new File(htmlFilePath);
                    FileInputStream htmlFileInputStream = new FileInputStream(htmlFile);
                    PrintWriter out = new PrintWriter(client.getOutputStream());

                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println("\r\n");
                    out.flush();

                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = htmlFileInputStream.read(buffer)) != -1) {
                        out.write(new String(buffer, 0, bytesRead));
                        out.flush();
                    }

                    htmlFileInputStream.close();
                    out.close();
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
