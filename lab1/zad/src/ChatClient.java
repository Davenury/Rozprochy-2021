import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws IOException {
        Socket socket = establishConnection();
        if(socket == null){
            System.out.println("Error while establishing the connection!");
            return;
        }
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread thread = new Thread(() -> {
            try {
                while(!Thread.interrupted()){
                    String message = in.readLine();
                    if (message != null)
                        System.out.println(message);
                }
            } catch (Exception ignored) {

            }
        });
        thread.start();

        while(true){
            String command = getCommand();
            int code = parseCommand(command, socket, out, in, thread);
            if(code == -1){
                break;
            }
        }
    }

    private static Socket establishConnection(){
        Socket socket;
        try {
            socket = new Socket("localhost", 12345);
            return socket;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static void closeConnection(Socket socket, Thread thread, PrintWriter out) throws IOException {
        if(socket != null){
            thread.interrupt();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("close");
            socket.close();
        }
    }

    private static String getCommand(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static int parseCommand(String command, Socket socket, PrintWriter out,
                                    BufferedReader in, Thread thread) throws IOException {
        command = command.toLowerCase();
        if(command.equals("logout")){
            closeConnection(socket, thread, out);
            return -1;
        } else{
            out.println(command);
        }
        return 0;
    }
}
