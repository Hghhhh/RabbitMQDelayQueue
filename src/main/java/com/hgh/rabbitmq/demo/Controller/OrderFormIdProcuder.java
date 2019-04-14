package com.hgh.rabbitmq.demo.Controller;

import com.hgh.rabbitmq.demo.pojjo.OrderIdConfiremCallBack;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;



@RestController
public class OrderFormIdProcuder {

    @Autowired
    private RabbitTemplate amqpTemplate;

    @Autowired
    private OrderIdConfiremCallBack orderIdConfiremCallBack;

    @RequestMapping(value="DelayOrderId",method = RequestMethod.POST)
    public void sendOrderId(@RequestParam String orderFormId) {
        //设置Confirem
        amqpTemplate.setConfirmCallback(orderIdConfiremCallBack);
        amqpTemplate.convertAndSend("delayOrderIdExchange", "delayOrderId", orderFormId,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //设置ttl
                        message.getMessageProperties().setExpiration("3000");
                        //设置deliver_mode为2
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        return message;
                    }

                },new  CorrelationData(UUID.randomUUID().toString()));
    }
}
