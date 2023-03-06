const amqp = require("amqplib");
let channel, connection;

const exchange_name = "scraper-exchange";
const queue_name    = 'scheduler-queue';

async function connectToRabbitMQ() {
    try {
        connection = await amqp.connect("amqp://localhost:5672");
        channel = await connection.createChannel();

        connectToQueue()

    } catch (error) {
        console.log(error);
    }
}
async function connectToQueue() {

    const q = await channel.assertQueue(queue_name, { exclusive: false });

    console.log("Waiting for messages....");

    // binding the queue

    const binding_key = "";
    channel.bindQueue(q.queue, exchange_name, binding_key);
    console.log("consuming messages from queue: ", q.queue);

    channel.consume(
        q.queue,
        (msg) => {
            if (msg.content)
                console.log("Received message: ", msg.content.toString());
        },
        { noAck: true }
    );
}

connectToRabbitMQ();
