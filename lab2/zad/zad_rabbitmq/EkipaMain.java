import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EkipaMain {

    public static void main(String[] argv) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input name of this equipe: ");
        String name = br.readLine();
        Ekipa equipe = new Ekipa(name);
    }
}
