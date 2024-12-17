package br.com.projeto.todolist.user.producer;

import br.com.projeto.todolist.user.dtos.EmailDTO;
import br.com.projeto.todolist.user.models.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;
    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${spring.rabbitmq.routing.name}")
    private String routingKey;

    @Value(value = "${spring.rabbitmq.exchange.name}")
    private String exchangeKey;

    public void publishMessageEmail(User user) {
        var emailDTO = new EmailDTO();
        emailDTO.setFirstName(user.getFirstName());
        emailDTO.setLastName(user.getLastName());
        emailDTO.setEmail(user.getEmail());
        rabbitTemplate.convertAndSend(exchangeKey, routingKey, emailDTO);
    }
}
