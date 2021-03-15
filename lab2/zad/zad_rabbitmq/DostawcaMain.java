import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DostawcaMain {

    static String IN_QUEUE_NAME = "OD_EKIPY";
    static String OUT_QUEUE = "OD_DOSTAWCY";

    public static void main(String[] argv) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input name of this supplier: ");
        String name = br.readLine();
        Dostawca dostawca = new Dostawca(IN_QUEUE_NAME, OUT_QUEUE, name);
    }
}
