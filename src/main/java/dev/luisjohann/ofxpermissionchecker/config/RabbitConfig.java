package dev.luisjohann.ofxpermissionchecker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitConfig {

    @Value("${queue.name.user-change}")
    private String queueUserChange;
    @Value("${queue.name.ue-change}")
    private String queueUeChange;

    @Value("${queue.routing-key.user-change}")
    private String routingKeyUserChange;
    @Value("${queue.routing-key.ue-change}")
    private String routingKeyUeChange;

    @Value("${exchange.name}")
    private String exchangeName;

    @Bean(name = "queueUserChange")
    public Queue queueUserChange() {
        return new Queue(queueUserChange, true);
    }

    @Bean(name = "queueUeChange")
    public Queue queueUeChange() {
        return new Queue(queueUeChange, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding queueBindingUserChange(@Qualifier(value = "queueUserChange") Queue importedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(importedQueue).to(exchange).with(routingKeyUserChange);
    }

    @Bean
    Binding queueBindingUeChange(@Qualifier(value = "queueUeChange") Queue importedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(importedQueue).to(exchange).with(routingKeyUeChange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
