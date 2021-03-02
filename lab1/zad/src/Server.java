import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.regex.Pattern;

public class Server {
    public static void main(String[] args) throws IOException {

        List<ServerClient> users = new ArrayList<>();

        Pattern pattern = Pattern.compile("login [a-z]+");

        ServerSocket serverSocket = createSocket();
        if(serverSocket == null){
            return;
        }
        try{
            while(true){
                Socket clientSocket = serverSocket.accept();
                Thread createUserAndListen = new Thread(() -> {
                    try {
                        ServerClient client = new ServerClient(clientSocket);
                        users.add(client);
                        while(!Thread.interrupted()){
                            String msg = client.in.readLine();
                            if(msg.equals("close")){
                                client.close();
                                Thread.currentThread().interrupt();
                            }else if(pattern.matcher(msg).matches()){
                                msg = msg.substring(6);
                                if(checkForUsersWithThisName(users, client, msg)){
                                    client.sendBackMessage("Username already exists");
                                }
                                else {
                                    System.out.println("here");
                                    client.setLogin(msg);
                                    client.sendBackMessage("Successfully logged in!");
                                }
                            }
                            else {
                                if(client.login == null){
                                    client.sendBackMessage("You need to login before writing into chat!");
                                }
                                else {
                                    sendMessages(users, msg, client);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                createUserAndListen.start();
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private static boolean checkForUsersWithThisName(List<ServerClient> users, ServerClient toCheck, String login){
        System.out.println(login);
        for(ServerClient user : users){
            if(!toCheck.equals(user))
                if(login.equals(user.login)){
                    return true;
                }
        }
        return false;
    }

    private static void sendMessages(List<ServerClient> users, String message, ServerClient sender){
        for (ServerClient user : users){
            System.out.println(user.login + " " + sender.login);
            if(!user.login.equals(sender.login))
                user.sendMessage(message, sender);
        }
    }

    private static ServerSocket createSocket(){
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(12345);
            return serverSocket;
        } catch(IOException e){
            System.out.println("Error while creating socket!");
            return null;
        }
    }

    private static class ServerClient{
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        String login;

        public ServerClient(Socket clientSocket) throws IOException {
            socket = clientSocket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void setLogin(String userLogin){
            login = userLogin;
        }

        public void sendMessage(String message, ServerClient sender){
            out.println(sender.login + " writes: " + message);
        }

        public void sendBackMessage(String message){
            out.println(message);
        }

        public void close() throws IOException {
            socket.close();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ServerClient that = (ServerClient) o;
            return Objects.equals(socket, that.socket) &&
                    Objects.equals(out, that.out) &&
                    Objects.equals(in, that.in);
        }
    }
}
