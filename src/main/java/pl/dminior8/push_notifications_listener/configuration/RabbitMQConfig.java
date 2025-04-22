package pl.dminior8.push_notifications_listener.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "notificationExchange";
    public static final String PUSH_QUEUE = "pushQueue"; // Kolejka dla powiadomień push
    public static final String PUSH_ROUTING_KEY = "notification.push"; // Routing key dla powiadomień push

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://guest:guest@rabbitmq:5672");
        return factory;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue pushQueue() {
        return QueueBuilder
                .durable(PUSH_QUEUE)
                .withArgument("x-max-priority", 10)
                .build();
    }

    @Bean
    public Binding pushBinding() {
        return BindingBuilder.bind(pushQueue()).to(exchange()).with(PUSH_ROUTING_KEY); // Powiązanie z odpowiednim routing key
    }
}
