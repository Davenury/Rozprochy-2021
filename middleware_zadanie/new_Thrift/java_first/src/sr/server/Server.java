package sr.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import sr.Service;
import sr.common.ProtocolType;

public class Server  {
    public static void main(String[] args) {
        try {
            Runnable simple = () -> simple(args[0]);

            new Thread(simple).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void simple(String protocolTypeArg) {
        try {
            ProtocolType type = ProtocolType.valueOf(protocolTypeArg);
            Service.Processor<ServiceHandler> processor = new Service.Processor<>(new ServiceHandler());
            TServerTransport serverTransport = new TServerSocket(9080);
            TProtocolFactory factory = createProtocolFactory(type);

            TServer server = new TSimpleServer(new TServer.Args(serverTransport).protocolFactory(factory).processor(processor));
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TProtocolFactory createProtocolFactory(ProtocolType type) {
        return switch (type) {
            case COMPACT -> new TCompactProtocol.Factory();
            case JSON -> new TJSONProtocol.Factory();
            case BINARY -> new TBinaryProtocol.Factory();
        };
    }
}