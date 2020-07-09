package com.xiaoye.rabbitmq;

import com.rabbitmq.client.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class Test {
    // 设置连接Rabbitmq的基本信息
    private String exchangeName = "exchangedemo";
    private String rountkey = "routingkeydemo";
    private String queueName = "queuedemo";
    private String IP_ADDRESS = "47.103.22.170";
    private int  port=5672;


    @org.junit.jupiter.api.Test
    public void testProvider() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);//设置主机地址
        connectionFactory.setPort(port);//设置端口
        connectionFactory.setUsername("guest");//用户名
        connectionFactory.setPassword("guest");//密码
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();//创建信道
        AMQP.Exchange.DeclareOk direct = channel.exchangeDeclare(exchangeName, "direct", true, false, null);
        System.out.println(direct.toString());
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,rountkey);
        String message="hello,world";
        channel.basicPublish(exchangeName,rountkey, MessageProperties.PERSISTENT_TEXT_PLAIN
        ,message.getBytes());
        channel.close();
        connection.close();
    }

    @org.junit.jupiter.api.Test
    public void testconsummer() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);//设置主机地址
        connectionFactory.setPort(port);//设置端口
        connectionFactory.setUsername("guest");//用户名
        connectionFactory.setPassword("guest");//密码
        Connection connection = connectionFactory.newConnection();//创建连接
        Channel channel = connection.createChannel();  //创建信道
        channel.basicQos(64);
        DefaultConsumer consumer = new DefaultConsumer(channel) {   //消费者
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                System.out.println("recv message: " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queueName,consumer);
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }

    /****
     *    fanout、direct、topic、headers  四种交换器类型
     *
     *    fanout 它会把所有发送到该交换器的消息路由到所有与该交换器绑定的队列中
     *    direct 它会把消息路由到那些BindingKey和RoutingKey完全匹配的队列中
     *    topic ωpic类型的交换器在匹配规则上进行了扩展，它与direct类型的交换器相似，
     *    也是将消息路由到BindingKey和RoutingKey相匹配的队列中，但这里的匹配规则有些不同
     *    header headers类型的交换器不依赖于路由键的匹配规则来路由消息，而是根据发送的消息内容中的headers属性进行匹配

         Bindingkey也属于路由键中的一种，也就阐述了Bindingkey和Routingkey之间的关系（:the routing key to use for the binding）
     RoutingKey相当于填写在包裹上的地址，BindingKey相当于包裹的目的地，

     绑定是用在交换器和队列之间
     路由是用在目标队列与消息之间
     */

     /**
      *    创建交换器
      *    Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) throws IOException
      *    1.exchange:交换器的名称
      *    2.type:交换器的类型，常见的如fanout、direct、topic
      *    3.durable:设置是否持久化。durable设置为true表示持久化，反之是非持久化
      *    4.autoDelete:设置是否自动删除。autoDelete设置为true则表示自动删除。
      *    5.internal:设置是否是内置的。如果设置为true，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式。
      *    6.argument:其他一些结构化参数
      * **/
      public void test3() throws IOException, TimeoutException {
          ConnectionFactory connectionFactory = new ConnectionFactory();
          connectionFactory.setHost(IP_ADDRESS);//设置主机地址
          connectionFactory.setPort(port);//设置端口
          connectionFactory.setUsername("guest");//用户名
          connectionFactory.setPassword("guest");//密码
          Connection connection = connectionFactory.newConnection();//创建连接
          Channel channel = connection.createChannel();  //创建信道
          AMQP.Exchange.DeclareOk direct = channel.exchangeDeclare(exchangeName, "direct", true, false, null);

      }

      /**
       *    创建队列
       *    Queue. DeclareOk queueDeclare (String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<Str工ng，Object> arguments) throws IOException;
       *    1.queue:队列的名称。
       *    2.durable:设置是否持久化。为true则设置队列为持久化。持久化的队列会存盘，在服务器重启的时候可以保证不丢失相关信息。
       *    3.exclusive:设置是否排他。为true则设置队列为排他的。如果一个队列被声明为排他队列，该队列仅对首次声明它的连接可见，并在连接断开时自动删除
       *    4.autoDelete:设置是否自动删除。为true则设置队列为自动删除。自动删除的前提是:至少有一个消费者连接到这个队列，之后所有与这个队列连接的消费者都断开时，才会自动删除。
       *    5.argurnents:设置队列的其他一些参数
       * **/


      /**
       *     队列和交换器绑定
       *      Queue.BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments) throws IOException;
       *     1.queue:队列名称
       *     2.exchange:交换器的名称
       *     3.routingKey:用来绑定队列和交换器的路由键;
       *     4.argument:定义绑定的一些参数。
       * **/

      /***
       *         生产者发送确认
       *         注意：事务机制和publisherconfirm机制两者是互斥的，不能共存
       *
       * **/
      @org.junit.jupiter.api.Test
      public void test4() throws IOException{
          ConnectionFactory connectionFactory = new ConnectionFactory();
          connectionFactory.setHost(IP_ADDRESS);//设置主机地址
          connectionFactory.setPort(port);//设置端口
          connectionFactory.setUsername("guest");//用户名
          connectionFactory.setPassword("guest");//密码
          Connection connection = null;
          try {
              connection = connectionFactory.newConnection();
          } catch (TimeoutException e) {
              e.printStackTrace();
          }
          Channel channel = connection.createChannel();//创建信道
        //  AMQP.Exchange.DeclareOk direct = channel.exchangeDeclare(exchangeName, "direct", true, false, null);
          channel.confirmSelect();  //开启发送者发送确认
          String message="hello,world";
          channel.basicPublish(exchangeName,rountkey, MessageProperties.PERSISTENT_TEXT_PLAIN
                  ,message.getBytes());
          try {
              if(!channel.waitForConfirms()){  //判断是否发送确认
                   System.out.println("发送消息失败");
              }
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      /** **/
}


