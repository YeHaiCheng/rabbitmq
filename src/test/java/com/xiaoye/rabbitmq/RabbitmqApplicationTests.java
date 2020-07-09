package com.xiaoye.rabbitmq;

import com.rabbitmq.client.DefaultConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySources;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RabbitmqApplicationTests {
    @Autowired
	RabbitTemplate rabbitTemplate;
     /**
	  *     测试发送消息
	  * **/
	@Test
	void setMessage() {
         Map<String,Object> map=new HashMap<>();
         map.put("msg","abcdef");
		 rabbitTemplate.convertAndSend("xiao.curExchanges","xiaoye","kkk123");
		 rabbitTemplate.convertAndSend("xiao.curExchanges","xiaoye",map);
	}
	@Test
	void getMessage(){
		String uuid = rabbitTemplate.getUUID();
		System.out.println(uuid);

	}



}
