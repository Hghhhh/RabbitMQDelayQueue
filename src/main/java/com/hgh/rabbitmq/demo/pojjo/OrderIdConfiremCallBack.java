package com.hgh.rabbitmq.demo.pojjo;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class OrderIdConfiremCallBack implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(b==true){
            System.out.println(correlationData.getId());
        }else{
            System.out.println("交由定时器扫描数据库");
        }
    }
}
