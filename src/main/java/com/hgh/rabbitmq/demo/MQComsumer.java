package com.hgh.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues="deadOrderIdQueue")
public class MQComsumer {
    static int count = 0;
    @RabbitHandler
    public void handler(String s,Channel channel, Message orderId){

        try {
            count++;
            System.out.println(count);
            channel.basicQos(3,true);
            System.out.println( "123 "+s);
            TimeUnit.SECONDS.sleep(5);
            //channel.basicRecover(true);
            channel.basicAck(orderId.getMessageProperties().getDeliveryTag(),false);
            //System.out.println(s);
            if(count%3==0){
                System.out.println("-------");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //channel.basicNack(orderId.getMessageProperties().getDeliveryTag(), false,false);
        }
    }

}
