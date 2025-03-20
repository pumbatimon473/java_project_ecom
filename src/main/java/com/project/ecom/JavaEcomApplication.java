package com.project.ecom;

import com.project.ecom.configurations.payment.RazorpayConfigurationProperties;
import com.project.ecom.configurations.payment.StripeConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StripeConfigurationProperties.class, RazorpayConfigurationProperties.class})
public class JavaEcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaEcomApplication.class, args);
	}

}
