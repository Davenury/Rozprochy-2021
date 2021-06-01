package sr.grpc.client;

import sr.Definitions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class Client {
    private static String clientName;
    private static Sender sender;
    private static int maxTelemetryBuffor;

    private static void sendRandomTelemetry(Definitions.Telemetry.DetectorCase detectorCase){
        sender.sendTelemetry(TelemetryInfo.randomTelemetry(clientName, detectorCase).build());
    }

    public static void main(String[] args) throws IOException {
        clientName = args[0];
        maxTelemetryBuffor = parseInt(args[1]);
        int scheduleTime = parseInt(args[2]);
        sender = new Sender(maxTelemetryBuffor, scheduleTime);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println(clientName + " $> ");
            String telemetryType = reader.readLine();
            switch (telemetryType.toLowerCase()) {
                case "e" -> sendRandomTelemetry(Definitions.Telemetry.DetectorCase.ELECTRICITY);
                case "w" -> sendRandomTelemetry(Definitions.Telemetry.DetectorCase.WATER);
                case "t" -> sendRandomTelemetry(Definitions.Telemetry.DetectorCase.TEMPERATURE);
                default -> System.out.println("Wrong letter. \nTry: \ne for sending electricity \nw for sending water \nt for sending temperature.");
            }
        }
    }
}
