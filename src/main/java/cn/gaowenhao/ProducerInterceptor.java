package cn.gaowenhao;
/*
-----------------------------------------------------
    Author : 高文豪
    Github : https://github.com/gaowenhao
    Blog   : https://gaowenhao.cn
-----------------------------------------------------
*/

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProducerInterceptor {
    public static void main(String[] args) {
        // 设置配置文件
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");

        // 指定序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        List<String> lst = new ArrayList<String>();
        lst.add("cn.gaowenhao.MyInterceptor");
        props.put("interceptor.classes", lst);

        // 创建producer
        KafkaProducer producer = new KafkaProducer<String, String>(props);

        // 给mytopic,发送一条消息
        producer.send(new ProducerRecord("mytopic", "Hey --By Java"));


        // 关闭producer, 读者应该知道几乎所有的close里面都有个flush
        producer.close();
    }
}
