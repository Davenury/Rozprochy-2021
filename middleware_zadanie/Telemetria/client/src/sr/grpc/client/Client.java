package sr.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sr.Definitions;
import sr.TelemetryServiceGrpc;

import java.sql.Timestamp;
import java.util.Date;

public class Client {
    public static void main(String[] args){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        TelemetryServiceGrpc.TelemetryServiceBlockingStub stub =
                TelemetryServiceGrpc.newBlockingStub(channel);

        stub.sendTelemetry(Definitions.Telemetry.newBuilder()
            .setSender("Client1")
            .setTime(new Timestamp(new Date().getTime()).toString())
            .setValue(50)
            .setElectricity(Definitions.Electricity.newBuilder()
                    .setDevice("Radio")
                    .build())
            .build()
        );
        System.out.println("Sent electricity");

        stub.sendTelemetry(Definitions.Telemetry.newBuilder()
                .setSender("Client1")
                .setTime(new Timestamp(new Date().getTime()).toString())
                .setValue(50)
                .setWater(Definitions.Water.newBuilder()
                        .setAppartement("My appartement")
                        .build())
                .build()
        );
        System.out.println("Sent water");

        stub.sendTelemetry(Definitions.Telemetry.newBuilder()
                .setSender("Client1")
                .setTime(new Timestamp(new Date().getTime()).toString())
                .setValue(50)
                .setTemperature(Definitions.Temperature.newBuilder()
                        .setPlace("My place")
                        .build())
                .build()
        );

        Definitions.Telemetry telemetry = Definitions.Telemetry.newBuilder()
                .setSender("Client1")
                .setTime(new Timestamp(new Date().getTime()).toString())
                .setValue(50)
                .setTemperature(Definitions.Temperature.newBuilder()
                        .setPlace("My place")
                        .build())
                .build();

        stub.sendTelemetries(Definitions.ListOfTelemetry.newBuilder()
                .addTelemetries(telemetry)
                .build());
        System.out.println("Send temperature");
    }
}
