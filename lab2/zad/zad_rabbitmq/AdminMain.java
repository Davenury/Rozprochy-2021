import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AdminMain {

    static String IN_QUEUE_NAME = "OD_EKIPY_DO_ADMINA";
    static String OUT_QUEUE = "OD_DOSTAWCY_DO_ADMINA";

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        Admin admin = new Admin(IN_QUEUE_NAME, OUT_QUEUE);
    }
}
