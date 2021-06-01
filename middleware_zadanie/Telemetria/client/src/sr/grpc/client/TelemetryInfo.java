package sr.grpc.client;

import sr.Definitions;

import java.sql.Timestamp;
import java.util.*;

public class TelemetryInfo {
    private final String sender;
    private final String time;
    private final int value;
    private Definitions.Telemetry.DetectorCase detectorCase;
    private String detectorValue;

    public TelemetryInfo(String sender, String time, int value, Definitions.Telemetry.DetectorCase detectorCase, String detectorValue) {
        this.sender = sender;
        this.time = time;
        this.value = value;
        this.detectorCase = detectorCase;
        this.detectorValue = detectorValue;
    }

    public Definitions.Telemetry build() {
        Definitions.Telemetry.Builder telemetryBuilder = Definitions.Telemetry.newBuilder()
                .setSender(this.sender)
                .setTime(this.time)
                .setValue(this.value);
        telemetryBuilder = this.addDetectorToTelemetryBuilder(telemetryBuilder);
        return telemetryBuilder.build();
    }

    private Definitions.Telemetry.Builder addDetectorToTelemetryBuilder(Definitions.Telemetry.Builder builder){
        return switch(this.detectorCase){
            case WATER -> builder.setWater(Definitions.Water.newBuilder().setAppartement(this.detectorValue).build());
            case ELECTRICITY -> builder.setElectricity(Definitions.Electricity.newBuilder().setDevice(this.detectorValue).build());
            case TEMPERATURE -> builder.setTemperature(Definitions.Temperature.newBuilder().setPlace(this.detectorValue).build());
            case DETECTOR_NOT_SET -> throw new IllegalStateException("Detector type is not defined!");
        };
    }

    public static TelemetryInfo randomTelemetry(String clientName, Definitions.Telemetry.DetectorCase detectorCase){
        return new TelemetryInfo(
                clientName,
                new Timestamp(new Date().getTime()).toString(),
                randomValue(),
                detectorCase,
                randomDetectorValue(detectorCase)
        );
    }

    private static final Random random = new Random();
    private static int randomValue(){
        return random.nextInt(200) + random.nextInt(50);
    }

    private static String randomDetectorValue(Definitions.Telemetry.DetectorCase detectorCase){
        return switch (detectorCase){
            case WATER -> randomWater();
            case ELECTRICITY -> randomElectricity();
            case TEMPERATURE -> randomTemperature();
            case DETECTOR_NOT_SET -> throw new IllegalStateException("Detector type is not defined!");
        };
    }

    private static String randomWater(){
        return randomFromList(Arrays.asList("1", "2", "2a", "3", "3a", "3b", "4", "4a", "4b", "4c", "5"));
    }

    private static String randomElectricity(){
        return randomFromList(Arrays.asList("Radio", "TV", "Lamp", "Stereo", "Telephone", "Charger", "Iron"));
    }

    private static String randomTemperature(){
        return randomFromList(Arrays.asList("New York", "Paris", "Warsaw", "Krakau", "Portugal", "Madrit", "Berlin"));
    }

    private static String randomFromList(List<String> list){
        return list.get(random.nextInt(list.size()));
    }
}
