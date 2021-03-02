package zad2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class JavaServer {
    public static void main(String[] args){

        DatagramSocket socket = null;

        try{
            socket = new DatagramSocket(9876);
            byte[] receiverB = new byte[1024];
            while(true){
                DatagramPacket packet = new DatagramPacket(receiverB, receiverB.length);
                socket.receive(packet);

                String mes = new String(packet.getData());
                System.out.println("Received: " + mes);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(socket != null){
                socket.close();
            }
        }
    }
}
