const grpc = require('@grpc/grpc-js')
const protoLoader = require('@grpc/proto-loader')
const protoDefinition = protoLoader.loadSync("proto/definitions.proto",{
    keepCase: true,
    enums: String,
    oneofs: true
})

const protoDescriptor = grpc.loadPackageDefinition(protoDefinition)


const server = new grpc.Server()
server.addService(protoDescriptor.TelemetryService.service, {
        sendTelemetry: sendTelemetry,
        sendTelemetries: sendTelemetries
    }
)

server.bindAsync('127.0.0.1:8080', grpc.ServerCredentials.createInsecure(), () => {
    server.start();
});

function handleTelemetry(telemetry) {
    console.log(telemetry)
    return protoDescriptor.Null
}
function sendTelemetry(call, callback){
    callback(null, handleTelemetry(call.request))
}

function handleSendTelemetries(request){
    request.telemetries.forEach(telemetry => console.log(telemetry))
    return protoDescriptor.Null
}
function sendTelemetries(call, callback){
    callback(null, handleSendTelemetries(call.request))
}