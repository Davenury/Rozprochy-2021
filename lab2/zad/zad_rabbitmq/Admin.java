import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.List;

public class Admin {
    private Connection connection;
    private Channel channel;
    private String TEAM_QUEUE;
    private String SUPPLIER_QUEUE;

    public Admin(String TEAM_QUEUE, String SUPPLIER_QUEUE) throws Exception {
        this.TEAM_QUEUE = TEAM_QUEUE;
        this.SUPPLIER_QUEUE = SUPPLIER_QUEUE;
        setUpChannel();
        startListening();
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
}
