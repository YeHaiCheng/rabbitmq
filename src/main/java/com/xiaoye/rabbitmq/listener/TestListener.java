package com.xiaoye.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TestListener {
    /**
     *      接收队列方法的参数要和消息的格式一致
     * **/
    @RabbitListener(queues = "xiaoye.firstqueue")
    public void test(String id){
         System.out.println("我方接受到="+id);
    }

}
