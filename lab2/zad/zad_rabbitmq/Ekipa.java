import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Ekipa {

    private Connection connection;
    private Channel channel;
    private String name;
    private String OUT_QUEUE = "OD_EKIPY";
    private String IN_QUEUE = "OD_DOSTAWCY_DO_";
    private String order;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private int numberOfSuppliers;
    private int counter = 0;

    public Ekipa(String name, int numberOfSuppliers) throws Exception{
        this.name = name;
        IN_QUEUE += this.name;
        this.numberOfSuppliers = numberOfSuppliers;
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
        channel.queueDeclare(OUT_QUEUE, false, false, false, null);
        channel.queueDeclare(IN_QUEUE, false, false, false, null);
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
                        if(counter < numberOfSuppliers)
                            sendOrder();
                        else{
                            System.out.println("Nobody has it!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println(message);
                    counter = 0;
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
        this.counter++;
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
