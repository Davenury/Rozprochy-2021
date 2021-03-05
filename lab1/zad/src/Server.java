import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class Server {
    public static void main(String[] args) throws IOException {

        List<ServerClient> users = new ArrayList<>();
        List<UDPClient> udpUsers = new ArrayList<>();

        SocektsPacket sockets = createSocket();
        ServerSocket serverSocket;
        DatagramSocket udpSocket;
        if(sockets == null || sockets.tcp == null || sockets.udp == null){
            return;
        }
        serverSocket = sockets.tcp;
        udpSocket = sockets.udp;
        try{
            listenOnUDP(udpSocket, udpUsers);
            listenOnTCP(serverSocket, users);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private static void listenOnUDP(DatagramSocket udpSocket, List<UDPClient> users){
        Thread thread = new Thread(() -> {
            try {
                byte[] receiveBuffer = new byte[1024];
                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    InetAddress address = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    UDPClient user = new UDPClient(address, port);

                    if(msg.equals("Init")){
                        users.add(user);
                    } else{
                        for(UDPClient udpUser : users){
                            if(!udpUser.equals(user))
                                user.sendMessage(msg, udpSocket);
                        }
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            } finally {
                udpSocket.close();
            }
        });
        thread.start();
    }

    private static void listenOnTCP(ServerSocket serverSocket, List<ServerClient> users)
            throws IOException {
        Pattern pattern = Pattern.compile("login [a-z]+");
        while(true){
            Socket clientSocket = serverSocket.accept();
            Thread createUserAndListen = new Thread(() -> {
                serviceClient(clientSocket, users, pattern);
            });
            createUserAndListen.start();
        }
    }

    private static void serviceClient(Socket clientSocket, List<ServerClient> users, Pattern pattern){
        try {
            ServerClient client = new ServerClient(clientSocket);
            users.add(client);
            while(!Thread.interrupted()){
                parseMessage(users, client, pattern);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseMessage(List<ServerClient> users, ServerClient client, Pattern pattern)
            throws IOException {
        String msg = client.in.readLine();
        System.out.println("Sent by TCP: " + msg);
        if(msg.equals("close")){
            client.close();
            users.remove(client);
            Thread.currentThread().interrupt();
        }else if(pattern.matcher(msg).matches()){
            loginTCPClient(users, client, msg);
        }
        else {
            sendMessagesOfLoginClient(users, client, msg);
        }
    }

    private static void loginTCPClient(List<ServerClient> users, ServerClient client, String msg){
        msg = msg.substring(6);
        if(checkForUsersWithThisName(users, client, msg)){
            client.sendBackMessage("Username already exists");
        }
        else {
            client.setLogin(msg);
            client.sendBackMessage("Successfully logged in!");
        }
    }

    private static void sendMessagesOfLoginClient(List<ServerClient> users, ServerClient client, String msg){
        if(client.login == null){
            client.sendBackMessage("You need to login before writing into chat!");
        }
        else {
            sendMessages(users, msg, client);
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

    private static SocektsPacket createSocket(){
        ServerSocket serverSocket;
        DatagramSocket udpSocket;
        try{
            serverSocket = new ServerSocket(12345);
            udpSocket = new DatagramSocket(12345);
            return new SocektsPacket(serverSocket, udpSocket);
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

    private static class SocektsPacket{
        ServerSocket tcp;
        DatagramSocket udp;
        public SocektsPacket(ServerSocket tcpSocket, DatagramSocket udpSocket){
            tcp = tcpSocket;
            udp = udpSocket;
        }
    }

    private static class UDPClient{
        InetAddress address;
        int port;
        public UDPClient(InetAddress udpAddress, int udpPort){
            address = udpAddress;
            port = udpPort;
        }

        public InetAddress getAddress(){
            return address;
        }

        public int getPort(){
            return port;
        }

        public void sendMessage(String msg, DatagramSocket socket) throws IOException {
            byte[] sendBuffer = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, getAddress(), getPort());
            socket.send(sendPacket);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UDPClient udpClient = (UDPClient) o;
            return port == udpClient.port &&
                    Objects.equals(address, udpClient.address);
        }
    }
}
