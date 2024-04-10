import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
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

            while (true) {
            try (Socket socket = server.accept()) {
                LocalDate today = LocalDate.now();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream()
                      .write(httpResponse.getBytes("UTF-8"));
            }
        }
        }
        
    }
}