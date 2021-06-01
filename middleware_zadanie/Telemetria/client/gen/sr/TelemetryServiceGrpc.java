package sr;
import sr.Definitions;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: definitions.proto")
public final class TelemetryServiceGrpc {

  private TelemetryServiceGrpc() {}

  public static final String SERVICE_NAME = "TelemetryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Definitions.Telemetry,
      Definitions.Null> getSendTelemetryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendTelemetry",
      requestType = Definitions.Telemetry.class,
      responseType = Definitions.Null.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Definitions.Telemetry,
      Definitions.Null> getSendTelemetryMethod() {
    io.grpc.MethodDescriptor<Definitions.Telemetry, Definitions.Null> getSendTelemetryMethod;
    if ((getSendTelemetryMethod = TelemetryServiceGrpc.getSendTelemetryMethod) == null) {
      synchronized (TelemetryServiceGrpc.class) {
        if ((getSendTelemetryMethod = TelemetryServiceGrpc.getSendTelemetryMethod) == null) {
          TelemetryServiceGrpc.getSendTelemetryMethod = getSendTelemetryMethod =
              io.grpc.MethodDescriptor.<Definitions.Telemetry, Definitions.Null>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendTelemetry"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Definitions.Telemetry.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Definitions.Null.getDefaultInstance()))
              .setSchemaDescriptor(new TelemetryServiceMethodDescriptorSupplier("sendTelemetry"))
              .build();
        }
      }
    }
    return getSendTelemetryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Definitions.ListOfTelemetry,
      Definitions.Null> getSendTelemetriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendTelemetries",
      requestType = Definitions.ListOfTelemetry.class,
      responseType = Definitions.Null.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Definitions.ListOfTelemetry,
      Definitions.Null> getSendTelemetriesMethod() {
    io.grpc.MethodDescriptor<Definitions.ListOfTelemetry, Definitions.Null> getSendTelemetriesMethod;
    if ((getSendTelemetriesMethod = TelemetryServiceGrpc.getSendTelemetriesMethod) == null) {
      synchronized (TelemetryServiceGrpc.class) {
        if ((getSendTelemetriesMethod = TelemetryServiceGrpc.getSendTelemetriesMethod) == null) {
          TelemetryServiceGrpc.getSendTelemetriesMethod = getSendTelemetriesMethod =
              io.grpc.MethodDescriptor.<Definitions.ListOfTelemetry, Definitions.Null>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendTelemetries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Definitions.ListOfTelemetry.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Definitions.Null.getDefaultInstance()))
              .setSchemaDescriptor(new TelemetryServiceMethodDescriptorSupplier("sendTelemetries"))
              .build();
        }
      }
    }
    return getSendTelemetriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TelemetryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TelemetryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TelemetryServiceStub>() {
        @java.lang.Override
        public TelemetryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TelemetryServiceStub(channel, callOptions);
        }
      };
    return TelemetryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TelemetryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TelemetryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TelemetryServiceBlockingStub>() {
        @java.lang.Override
        public TelemetryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TelemetryServiceBlockingStub(channel, callOptions);
        }
      };
    return TelemetryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TelemetryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TelemetryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TelemetryServiceFutureStub>() {
        @java.lang.Override
        public TelemetryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TelemetryServiceFutureStub(channel, callOptions);
        }
      };
    return TelemetryServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TelemetryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendTelemetry(Definitions.Telemetry request,
        io.grpc.stub.StreamObserver<Definitions.Null> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendTelemetryMethod(), responseObserver);
    }

    /**
     */
    public void sendTelemetries(Definitions.ListOfTelemetry request,
        io.grpc.stub.StreamObserver<Definitions.Null> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendTelemetriesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendTelemetryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Definitions.Telemetry,
                Definitions.Null>(
                  this, METHODID_SEND_TELEMETRY)))
          .addMethod(
            getSendTelemetriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Definitions.ListOfTelemetry,
                Definitions.Null>(
                  this, METHODID_SEND_TELEMETRIES)))
          .build();
    }
  }

  /**
   */
  public static final class TelemetryServiceStub extends io.grpc.stub.AbstractAsyncStub<TelemetryServiceStub> {
    private TelemetryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TelemetryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TelemetryServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendTelemetry(Definitions.Telemetry request,
        io.grpc.stub.StreamObserver<Definitions.Null> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendTelemetryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendTelemetries(Definitions.ListOfTelemetry request,
        io.grpc.stub.StreamObserver<Definitions.Null> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendTelemetriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TelemetryServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<TelemetryServiceBlockingStub> {
    private TelemetryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TelemetryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TelemetryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Definitions.Null sendTelemetry(Definitions.Telemetry request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendTelemetryMethod(), getCallOptions(), request);
    }

    /**
     */
    public Definitions.Null sendTelemetries(Definitions.ListOfTelemetry request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendTelemetriesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TelemetryServiceFutureStub extends io.grpc.stub.AbstractFutureStub<TelemetryServiceFutureStub> {
    private TelemetryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TelemetryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TelemetryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Definitions.Null> sendTelemetry(
        Definitions.Telemetry request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendTelemetryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Definitions.Null> sendTelemetries(
        Definitions.ListOfTelemetry request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendTelemetriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_TELEMETRY = 0;
  private static final int METHODID_SEND_TELEMETRIES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TelemetryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TelemetryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_TELEMETRY:
          serviceImpl.sendTelemetry((Definitions.Telemetry) request,
              (io.grpc.stub.StreamObserver<Definitions.Null>) responseObserver);
          break;
        case METHODID_SEND_TELEMETRIES:
          serviceImpl.sendTelemetries((Definitions.ListOfTelemetry) request,
              (io.grpc.stub.StreamObserver<Definitions.Null>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TelemetryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TelemetryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Definitions.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TelemetryService");
    }
  }

  private static final class TelemetryServiceFileDescriptorSupplier
      extends TelemetryServiceBaseDescriptorSupplier {
    TelemetryServiceFileDescriptorSupplier() {}
  }

  private static final class TelemetryServiceMethodDescriptorSupplier
      extends TelemetryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TelemetryServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TelemetryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TelemetryServiceFileDescriptorSupplier())
              .addMethod(getSendTelemetryMethod())
              .addMethod(getSendTelemetriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
