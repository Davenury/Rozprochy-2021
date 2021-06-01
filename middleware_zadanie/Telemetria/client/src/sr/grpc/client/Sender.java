package sr.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sr.Definitions;
import sr.TelemetryServiceGrpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static sr.TelemetryServiceGrpc.newBlockingStub;

public class Sender implements Runnable {
    private List<Definitions.Telemetry> telemetries = new ArrayList<>();
    private TelemetryServiceGrpc.TelemetryServiceBlockingStub stub;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final int maxTelemetryBuffor;

    public Sender(int maxTelemetryBuffor, int scheduleTime){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        stub = newBlockingStub(channel);
        this.maxTelemetryBuffor = maxTelemetryBuffor;
        scheduler.scheduleAtFixedRate(this, 0, scheduleTime, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        this.sendTelemetry();
    }

    public void sendTelemetry(Definitions.Telemetry telemetry){
        telemetries.add(telemetry);
        if(telemetries.size() == this.maxTelemetryBuffor){
            this.sendTelemetry();
        }
    }

    private String getSendedTelemetries(){
        StringBuilder builder = new StringBuilder();
        telemetries.forEach(name -> { builder.append(name.getDetectorCase()).append(" "); });
        return builder.toString();
    }

    private void sendTelemetry(){
        if(telemetries.size() != 0) {
            stub.sendTelemetries(
                    Definitions.ListOfTelemetry
                            .newBuilder()
                            .addAllTelemetries(this.telemetries)
                            .build()
            );
            System.out.println("Sended " + telemetries.size() + " combined telemetries: " + getSendedTelemetries());
            telemetries.clear();
        }
    }
}
