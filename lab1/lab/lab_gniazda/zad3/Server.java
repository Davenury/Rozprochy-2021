package zad3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Server {

    public static void main(String[] args){
        DatagramSocket socket = null;

        try{
            socket = new DatagramSocket(9876);

            InetAddress clientAddress = null;
            int port = 0;
            int num;

            while(true){
                DatagramPacket receivePacket = new DatagramPacket(new byte[256], 256);
                socket.receive(receivePacket);

                clientAddress = receivePacket.getAddress();
                port = receivePacket.getPort();

                num = ByteBuffer.wrap(receivePacket.getData()).order(ByteOrder.LITTLE_ENDIAN).getInt();
                System.out.println("Server received: " + num);
                System.out.println("Server received from: " + clientAddress);

                break;
            }

            num++;
            byte[] sender = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(num).array();
            DatagramPacket sendPacket = new DatagramPacket(sender, sender.length, clientAddress, port);
            socket.send(sendPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
