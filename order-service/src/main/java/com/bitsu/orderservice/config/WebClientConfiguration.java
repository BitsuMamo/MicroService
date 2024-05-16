package com.bitsu.orderservice.config;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.CloudInstanceConfig;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.jersey.TransportClientFactories;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.cloud.netflix.eureka.InstanceInfoFactory;
import org.springframework.cloud.netflix.eureka.http.WebClientTransportClientFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

   @Bean
   public WebClient webClient(){
      return WebClient.builder().build();
   }

//   @Bean
//   public DiscoveryClient discoveryClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig config, TransportClientFactories transportClientFactories) {
//      return new DiscoveryClient(applicationInfoManager, config, transportClientFactories);
//   }
//
//   @Bean
//   public ApplicationInfoManager applicationInfoManager(EurekaInstanceConfig config) {
//      InstanceInfo instanceInfo = new InstanceInfoFactory().create(config);
//      return new ApplicationInfoManager(config, instanceInfo);
//   }
//
//   @Bean
//   public EurekaInstanceConfig eurekaInstanceConfig() {
//      return new CloudInstanceConfig();
//   }
//
//   @Bean
//   public EurekaClientConfig eurekaClientConfig() {
//      return new DefaultEurekaClientConfig();
//   }
//
//   @Bean
//   public TransportClientFactories transportClientFactories() {
//      return new WebClientTransportClientFactories(WebClient::builder);
//   }

}
