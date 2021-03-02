package zad4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(9876);
            byte[] receiver = new byte[1024];

            InetAddress clientAddress = null;
            int port = 0;
            String msg_back;
            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiver, receiver.length);
                socket.receive(receivePacket);

                clientAddress = receivePacket.getAddress();
                port = receivePacket.getPort();

                String msg = new String(receivePacket.getData(), StandardCharsets.UTF_8);
                System.out.println(msg);
                if (msg.equals("Python")){
                    msg_back = "Ping Python";
                }
                else{
                    msg_back = "Ping Java";
                }
                byte[] sender = msg_back.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sender, sender.length, clientAddress, port);
                socket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
