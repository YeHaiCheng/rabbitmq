package com.xiaoye.rabbitmq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    /***
     *     JDK 内部编码格式，  我们配置为JSON格式的序列号
     * ***/

        @Bean
        public MessageConverter messageConverter(){
            return new Jackson2JsonMessageConverter();
        }

}
