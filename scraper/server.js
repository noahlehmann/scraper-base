const grpc = require("@grpc/grpc-js");
const protoLoader = require("@grpc/proto-loader");
const PROTO_PATH = "./scraper.proto";

const options = {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true,
};

const packageDefinition = protoLoader.loadSync(PROTO_PATH, options);
const scraperProto = grpc.loadPackageDefinition(packageDefinition);

const server = new grpc.Server();

server.addService(scraperProto.ScraperService.service, {
    ScrapeTarget: (input, callback) => {
        try {
            callback(null, { logId: input.logId, confirmed: false, message: "Not implemented." });
        } catch (error) {
            callback(error, null);
        }
    },
});

server.bindAsync(
    "0.0.0.0:50051",
    grpc.ServerCredentials.createInsecure(),
    (error, port) => {
        console.log(`Server running at ${port}`);
        server.start();
    }
);