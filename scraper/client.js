//dependencies
const grpc = require("@grpc/grpc-js");
const protoLoader = require("@grpc/proto-loader");

//path to our proto file
const PROTO_FILE = "./scraper.proto";

//options needed for loading Proto file
const options = {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true,
};

const pkgDefs = protoLoader.loadSync(PROTO_FILE, options);

//load Definition into gRPC
const ScraperService = grpc.loadPackageDefinition(pkgDefs).ScraperService;

//create the Client
const client = new ScraperService(
    "localhost:50051",
    grpc.credentials.createInsecure()
);

//make a call to GetUser
client.ScrapeTarget({logId: 0, target: "https://www.zoro.com", disguise: true}, (error, confirmation) => {
    if (error) {
        console.log(error);
    } else {
        console.log(confirmation);
    }
});
