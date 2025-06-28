package com.project.ecom;

import com.project.ecom.configurations.clients.AuthClientConfigProperties;
import com.project.ecom.configurations.payment.RazorpayConfigurationProperties;
import com.project.ecom.configurations.payment.StripeConfigurationProperties;
import com.project.ecom.kafka.KafkaTopicsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties({
		StripeConfigurationProperties.class, RazorpayConfigurationProperties.class,
		AuthClientConfigProperties.class,
		KafkaTopicsConfigurationProperties.class})
public class JavaEcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaEcomApplication.class, args);
	}

}
