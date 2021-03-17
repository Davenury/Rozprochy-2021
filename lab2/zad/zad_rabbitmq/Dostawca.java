import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Dostawca {

    private final String IN_QUEUE_NAME;
    private final String OUT_QUEUE_NAME;
    private String ADMIN_QUEUE = "OD_DOSTAWCY_DO_ADMINA";
    private String EXCHANGE_NAME = "exchange";
    private Connection connection;
    private Channel channel;
    private final String name;
    private List<String> items = new ArrayList<>();

    public Dostawca(String IN_QUEUE_NAME, String OUT_QUEUE_NAME, String name) throws Exception{
        this.IN_QUEUE_NAME = IN_QUEUE_NAME;
        this.OUT_QUEUE_NAME = OUT_QUEUE_NAME;
        this.name = name;
        getItems();
        System.out.println("Starting listening");
        startListening();
    }

    private void getItems() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input items this supplier has: ");
        while(true){
            String item = br.readLine();
            if("end".equals(item)){
                break;
            }
            this.items.add(item);
        }
    }

    private Connection setUpConnection() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }

    private void setUpQueue(String name) throws Exception{
        this.channel.queueDeclare(name, false, false, false, null);
    }

    private void setUpChannel() throws Exception{
        this.connection = setUpConnection();
        this.channel = this.connection.createChannel();
        this.setUpQueue(OUT_QUEUE_NAME);
        this.setUpQueue(ADMIN_QUEUE);
    }

    private void startListening() throws Exception{
        setUpChannel();
        Consumer consumer = createConsumer();
        channel.basicConsume(IN_QUEUE_NAME, true, consumer);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "*.dostawca");
        channel.basicConsume(queueName, true, new DefaultConsumer(this.channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Got: " + message);
            }
        });
    }

    private void close() throws Exception{
        this.channel.close();
        this.connection.close();
    }

    private Consumer createConsumer(){
        return new DefaultConsumer(this.channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Got: " + message);
                try {
                    parseMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    //TODO ekipy też dostają wiadomości na zmianę
    private void parseMessage(String message) throws Exception{
        String[] splitted = message.split(" ");
        for(String item : this.items){
            if(splitted[1].equals(item)){
                this.sendMessage("ACK from " + name, OUT_QUEUE_NAME + splitted[0]);
                return;
            }
        }
        this.sendMessage("DEC from " + name, OUT_QUEUE_NAME + splitted[0]);
    }

    private void sendMessage(String message, String queueName) throws Exception{
        System.out.println(message);
        this.channel.basicPublish("", queueName, null, message.getBytes());
        this.channel.basicPublish("", ADMIN_QUEUE, null, message.getBytes());
    }
}
