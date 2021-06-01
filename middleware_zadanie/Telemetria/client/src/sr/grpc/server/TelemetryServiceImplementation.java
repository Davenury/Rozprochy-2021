package sr.grpc.server;


import io.grpc.stub.StreamObserver;
import sr.Definitions;
import sr.TelemetryServiceGrpc;

public class TelemetryServiceImplementation extends TelemetryServiceGrpc.TelemetryServiceImplBase {

    @Override
    public void sendTelemetry(Definitions.Telemetry request, StreamObserver<Definitions.Null> responseObserver) {
        String telemetry = "[" +
                request.getTime() +
                "] " +
                "Telemetry from: " +
                request.getSender() +
                ": " +
                getFields(request);

        System.out.println(telemetry);
        responseObserver.onNext(Definitions.Null.newBuilder().build());
        responseObserver.onCompleted();
    }

    private String getFields(Definitions.Telemetry request) {
        Definitions.Telemetry.DetectorCase detectorCase = request.getDetectorCase();
        return switch (detectorCase) {
            case WATER -> "appartement: " + request.getWater().getAppartement();
            case ELECTRICITY -> "device: " + request.getElectricity().getDevice();
            case TEMPERATURE -> "place: " + request.getTemperature().getPlace();
            default -> "Error - wrong telemetry type";
        };
    }
}
