package com.blesk.accountservice.Service.Authorizations;

import com.blesk.accountservice.Model.Accounts;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationsServiceImpl implements AuthorizationsService {

    @Value("${blesk.rabbitmq.authorization-exchange}")
    private String authorizationExchange;

    @Value("${blesk.rabbitmq.authorization-routing-key}")
    private String authorizationRoutingkey;

    private AmqpTemplate amqpTemplate;

    @Autowired
    public AuthorizationsServiceImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public boolean send(Accounts accounts) {
        try {
            this.amqpTemplate.convertAndSend(this.authorizationExchange, this.authorizationRoutingkey, accounts);
            return true;
        } catch (AmqpException ex) {
            return false;
        }
    }
}