import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ChatClient {

    static int port = 12345;
    static String name = "localhost";
    static String multicastName = "230.0.0.0";
    static int multicastPort = 4446;

    public static void main(String[] args) throws IOException {
        SocketPacket socketPacket = establishConnection();
        if(socketPacket == null || socketPacket.tcp == null || socketPacket.udp == null){
            System.out.println("Error while establishing the connection!");
            return;
        }
        Socket socket = socketPacket.tcp;
        DatagramSocket udpSocket = socketPacket.udp;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

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

        Thread udpThread = new Thread(() -> {
            try{
                byte[] receiveBuffer = new byte[1024];
                while(!Thread.interrupted()){
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receivePacket);
                    System.out.println(new String(receivePacket.getData()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        udpThread.start();

        Thread multicastThread = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
                InetAddress group = InetAddress.getByName(multicastName);
                multicastSocket.joinGroup(group);
                while(!Thread.interrupted()){
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(msg);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        multicastThread.start();

        while(true){
            String command = getCommand();
            int code = parseCommand(command, socket, out, in, thread, udpSocket, udpThread);
            if(code == -1){
                break;
            }
        }
    }

    private static void sendByUDP(DatagramSocket socket, String msg) throws IOException {
        byte[] sendBuffer = msg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer,
                sendBuffer.length, InetAddress.getByName(name), port);
        socket.send(sendPacket);
    }

    private static void sendByMulticast(String msg) throws IOException {
        byte[] buffer = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(multicastName);

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, multicastPort);
        socket.send(packet);
        socket.close();
    }

    private static SocketPacket establishConnection(){
        Socket socket;
        DatagramSocket udpSocket;
        try {
            socket = new Socket(name, port);
            udpSocket = new DatagramSocket();
            sendByUDP(udpSocket, "Init");
            return new SocketPacket(socket, udpSocket);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static void closeConnection(Socket socket, Thread thread,
                                        PrintWriter out, DatagramSocket udpSocket, Thread udpThread) throws IOException {
        if(socket != null){
            thread.interrupt();
            udpThread.interrupt();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("close");
            udpSocket.close();
            socket.close();
        }
    }

    private static String getCommand(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static int parseCommand(String command, Socket socket, PrintWriter out,
                                    BufferedReader in,
                                    Thread thread, DatagramSocket udpSocket, Thread udpThread) throws IOException {
        command = command.toLowerCase();
        Pattern startsWithU = Pattern.compile("^u");
        Pattern startsWithM = Pattern.compile("^m ");
        if(command.equals("logout")){
            closeConnection(socket, thread, out, udpSocket, udpThread);
            return -1;
        } else if(startsWithU.matcher(command).find()){
            sendByUDP(udpSocket, command.substring(2));
        } else if(startsWithM.matcher(command).find()) {
            sendByMulticast(command.substring(2));
        }else{
                out.println(command);
        }
        return 0;
    }

    private static class SocketPacket{
        Socket tcp;
        DatagramSocket udp;

        public SocketPacket(Socket tcpSocket, DatagramSocket udpSocket){
            tcp = tcpSocket;
            udp = udpSocket;
        }
    }
}
