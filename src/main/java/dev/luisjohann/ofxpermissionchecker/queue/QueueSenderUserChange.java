package dev.luisjohann.ofxpermissionchecker.queue;


import dev.luisjohann.ofxpermissionchecker.dto.SseUserChangeMessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSenderUserChange {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.routing-key.user-change}")
    private String routingKeySse;

    @Value("${exchange.name}")
    private String exchangeName;

    public void send(SseUserChangeMessageDTO message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKeySse, message);
    }
}
