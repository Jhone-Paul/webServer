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

/**
 * the http server, defaults to port 12345
 */
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
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String requestLine = reader.readLine();
                String[] requestParts = requestLine.split(" ");
                //http method 
                String method = requestParts[0];
                //file path
                String path = requestParts[1];
                switch (method) {
                    case "GET":
                   // handle http requests
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
                    } else if (path.endsWith(".js")) {
                        String jsFilePath = "src/script.js";
                        File jsFile = new File(jsFilePath);
                        FileInputStream jsFileInputStream = new FileInputStream(jsFile);
                        PrintWriter out = new PrintWriter(client.getOutputStream());

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: application/javascript");
                        out.println("\r\n");
                        out.flush();

                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while ((bytesRead = jsFileInputStream.read(buffer)) != -1) {
                            out.write(new String(buffer, 0, bytesRead));
                            out.flush();
                        }

                        jsFileInputStream.close();
                        out.close();
                    }
                    else {
                    
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
                    break;
                case "POST":
                    // TODO Handle POST request
                    break;
                case "PUT":
                    // TODO Handle PUT request
                    break;
                case "DELETE":
                    // TODO Handle DELETE request
                default:
                    // Handle unsupported methods
                    PrintWriter out = new PrintWriter(client.getOutputStream());
                    out.println("HTTP/1.1 405 Method Not Allowed");
                    out.println("Content-Type: text/plain");
                    out.println("\r\n");
                    out.println("Method Not Allowed");
                    out.flush();
                    break;
                }
                
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
