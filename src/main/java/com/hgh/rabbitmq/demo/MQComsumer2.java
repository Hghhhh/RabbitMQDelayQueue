package com.hgh.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues="deadOrderIdQueue")
public class MQComsumer2 {

    static int count = 0;
    @RabbitHandler
    public void handler(String s,Channel channel, Message orderId){
        try {
            count++;
            System.out.println(count);
            //这里设置为true，表示global prefetch_count，整个channel在没收到ack之前只能预发送这么个信息
            channel.basicQos(3,true);
            System.out.println("321"+" "+s);
            TimeUnit.SECONDS.sleep(5);
            //channel.basicRecover(true);
            if(count==3){
                System.out.println("-------");
            }
            //返回ACK确认
            channel.basicAck(orderId.getMessageProperties().getDeliveryTag(),false);
           // System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
            //channel.basicNack(orderId.getMessageProperties().getDeliveryTag(), false,false);
        }
    }

}
