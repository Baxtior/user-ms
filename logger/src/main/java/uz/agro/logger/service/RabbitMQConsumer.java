package uz.agro.logger.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import uz.agro.logger.dto.LoggingObject;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(@Payload LoggingObject message){
        System.out.println("Store in db " + message);
    }
}
