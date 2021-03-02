package zad1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    public static void main(String[] args){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(9876);
            byte[] receiver = new byte[1024];

            InetAddress clientAddress = null;
            int port = 0;

            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiver, receiver.length);
                socket.receive(receivePacket);

                clientAddress = receivePacket.getAddress();
                port = receivePacket.getPort();

                String msg = new String(receivePacket.getData());
                System.out.println("Server received: " + msg);
                System.out.println("Server received from: " + clientAddress);

                break;
            }

            byte[] sender = "Answer".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sender, sender.length, clientAddress, port);
            socket.send(sendPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
