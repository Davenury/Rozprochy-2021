import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Admin {
    private Connection connection;
    private Channel channel;
    private String TEAM_QUEUE;
    private String SUPPLIER_QUEUE;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String EXCHANGE_NAME = "exchange";

    public Admin(String TEAM_QUEUE, String SUPPLIER_QUEUE) throws Exception {
        this.TEAM_QUEUE = TEAM_QUEUE;
        this.SUPPLIER_QUEUE = SUPPLIER_QUEUE;
        setUpChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        startListening();
        waitForOrders();
    }

    private void setUpChannel() throws Exception{
        this.connection = setUpConnection();
        this.channel = this.connection.createChannel();
    }

    private Connection setUpConnection() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }

    private void startListening() throws Exception{
        Consumer consumer = createConsumer();
        this.channel.basicConsume(TEAM_QUEUE,true, consumer);
        this.channel.basicConsume(this.SUPPLIER_QUEUE, true, consumer);
    }

    private Consumer createConsumer(){
        return new DefaultConsumer(Admin.this.channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
            }
        };
    }

    private void setUpQueue(String name) throws Exception{
        this.channel.queueDeclare(name, false, false, false, null);
    }

    private void waitForOrders() throws Exception{
        String command;
        String message;
        while(true){
            command = br.readLine();
            if("exit".equals(command)){
                break;
            }
            message = br.readLine();
            makeOrder(command, message);
        }
    }

    private void makeOrder(String command, String message) throws Exception{
        if(command.equals("all")){
            command = "ekipa.dostawca";
        }else if(command.equals("ekipa")){
            command = "ekipa.";
        }else{
            command = ".dostawca";
        }
        message = "Od Admina: " + message;
        this.channel.basicPublish(EXCHANGE_NAME, command, null, message.getBytes());
    }
}
