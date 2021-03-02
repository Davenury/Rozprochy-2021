package zad4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args){
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            byte[] sendBuffer = "Java".getBytes();

            DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, address, 9876);
            socket.send(packet);

            byte[] receiveBuffer = new byte[1024];

            while(true) {
                DatagramPacket receiver = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receiver);

                String msg = new String(receiver.getData());
                System.out.println("Client received: " + msg);
                System.out.println("From: " + receiver.getAddress());
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
            }
        }
    }
}
