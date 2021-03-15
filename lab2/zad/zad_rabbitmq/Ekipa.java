import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Ekipa {

    Connection connection;
    Channel channel;
    String name;
    String OUT_QUEUE = "OD_EKIPY";
    String IN_QUEUE = "OD_DOSTAWCY";
    String order;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Ekipa(String name) throws Exception{
        this.name = name;
        this.createConsumerAndListen();
        this.makeOrders();
    }

    private void setUpChannel() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    private void setUpQueues() throws Exception{
        this.setUpChannel();
        String QUEUE_NAME = "OD_EKIPY";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueDeclare("OD_DOSTAWCY", false, false, false, null);
    }

    private void createConsumerAndListen() throws Exception{
        this.setUpQueues();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String[] splitted = message.split(" ");
                if(!splitted[0].equals("ACK")){
                    try {
                        sendOrder();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println(message);
                }
            }
        };
        this.channel.basicConsume(IN_QUEUE, true, consumer);
    }

    private void close() throws Exception{
        this.channel.close();
        this.connection.close();
    }

    private void sendOrder() throws Exception{
        String message = this.name + " " + order;
        channel.basicPublish("", OUT_QUEUE, null, message.getBytes());
        System.out.println("Sent: " + message);
    }

    private void makeOrders() throws Exception{
        while(true){
            order = this.br.readLine();
            if("exit".equals(order)){
                break;
            }
            this.sendOrder();
        }
    }
}
