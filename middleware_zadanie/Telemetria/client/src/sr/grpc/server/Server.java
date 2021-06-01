package sr.grpc.server;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server = ServerBuilder
                .forPort(8080)
                .addService(new TelemetryServiceImplementation()).build();

        server.start();
        server.awaitTermination();
    }
}
