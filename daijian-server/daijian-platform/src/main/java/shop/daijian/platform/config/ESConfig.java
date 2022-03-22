//package shop.daijian.platform.config;
//
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
///**
// * @Author stronghwan
// * @Verison
// * @Date2019/9/3-09-32
// */
//@Configuration
//public class ESConfig {
//
//    @Bean
//    public TransportClient transportClient()  {
//        TransportClient transportClient=null;
//        try {
//
//
//            Settings settings = Settings.builder()
//                    .put("cluster.name", "docker-cluster")
//                    .build();
//            transportClient =  new PreBuiltTransportClient(settings);
//            transportClient.addTransportAddress(new TransportAddress(
//                    InetAddress.getByName("elasticsearch"), 9300));
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        return transportClient;
//    }
//
//    @PostConstruct
//    void init() {
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//    }
//}
