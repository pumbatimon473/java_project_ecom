package com.project.ecom.configurations.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "razorpay")
@Getter
@Setter
public class RazorpayConfigurationProperties {
    private final Api api = new Api();
    private final Payment payment = new Payment();

    @Getter
    @Setter
    public static class Api {
        private String keyId;
        private String keySecret;
    }

    @Getter
    @Setter
    public static class Payment {
        private String redirectUrl;
    }
}
