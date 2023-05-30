package sg.edu.nus.iss.app.tfipcarcaremailing;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        System.out.println("host: "+connectionFactory.getHost());
        System.out.println("port: "+connectionFactory.getPort());
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listenerAdapter);

        container.setQueueNames("email");

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageListener listener) {
        return new MessageListenerAdapter(listener, "onMessageReceived");
    }
}
