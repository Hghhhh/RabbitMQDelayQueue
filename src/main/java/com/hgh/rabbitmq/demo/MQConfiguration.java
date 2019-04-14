package com.hgh.rabbitmq.demo;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class MQConfiguration {
    /**死信队列**/
    @Bean
    public Queue deadOrderIdQueue(){
        return new Queue("deadOrderIdQueue");
    }

    @Bean
    public DirectExchange deadOrderIdExchange(){
        return new DirectExchange("deadOrderIdExchange");
    }

    @Bean
    public Binding bindingDeadExchange(Queue deadOrderIdQueue,DirectExchange deadOrderIdExchange){
        return BindingBuilder.bind(deadOrderIdQueue).to(deadOrderIdExchange).with("deadOrderIdQueue");
    }

    /**待过期队列**/
    @Bean
    public Queue delayOrderId(){
        Map<String,Object> map = new HashMap<>(2);
        //设置该队列的死信队列
        map.put("x-dead-letter-exchange","deadOrderIdExchange");
        map.put("x-dead-letter-routing-key", "deadOrderIdQueue");
        return new Queue("delayOrderId",true,false,false,map);
    }

    @Bean
    public DirectExchange delayOrderIdExchange(){
        return new DirectExchange("delayOrderIdExchange");
    }

    @Bean
    public Binding bingingExchange(Queue delayOrderId, DirectExchange delayOrderIdExchange ){
        return BindingBuilder.bind(delayOrderId).to(delayOrderIdExchange).with("delayOrderId");
    }





}
