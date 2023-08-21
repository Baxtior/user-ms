package uz.agro.users.service;

import uz.agro.users.dto.request.LoggingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueSender {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueSender.class);

    private final RabbitTemplate rabbitTemplate;

    public QueueSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(LoggingObject user){
        LOGGER.info(user.toString());
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
    }
}
