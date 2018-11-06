package cn.gaowenhao;
/*
-----------------------------------------------------
    Author : 高文豪
    Github : https://github.com/gaowenhao
    Blog   : https://gaowenhao.cn
-----------------------------------------------------
*/

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerConfig {
    public static void main(String[] args) {
        // 搞个内存配置文件,好奇为啥官档不用Map举例，这玩意实现也是map。
        Properties props = new Properties();

        // 指定broker
        props.put("bootstrap.servers", "localhost:9092");

        // 持久性设置,0:不等待broker返回结果,1:消息写入本地日志后即返回结果,all:消息写入日志后,等待isr所有副本也写入后才返回结果
        props.put("acks", "all");

        // 如果发送请求出现故障重试次数,0是不重试.
        props.put("retries", 0);

        // 压缩类型了了解一下? 到目前为止kafka支持三种压缩类型: 'gzip', 'snappy', 'lz4'
        props.put("compression.type", "lz4");

        // 这个参数限制了单次producer发送请求的大小
        props.put("max.request.size", 1048576);

        // broker的超时时间
        props.put("request.timeout.ms", 30000);

        // 生产者总缓存(字节),实际上生产者这边是有个缓存池的，缓存池背后有一个专门的进程负责真正发送消息给broker，这个参数指定缓存大小
        props.put("buffer.memory", 33554432);

        // producer发送给partition的数据会封装一个batch，batch满的时候则发送，batch的大小由这个指定，当然有时候也不会等batch满了
        props.put("batch.size", 16384);

        // 上面的batch不满，等多久发送？
        props.put("linger.ms", 100);

        // 这两个参数指定打开键值对象的方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        // 创建producer对象
        Producer producer = new KafkaProducer<String, String>(props);

        producer.send(new ProducerRecord<String, String>("mytopic", "hello"));

        producer.close();
    }
}
