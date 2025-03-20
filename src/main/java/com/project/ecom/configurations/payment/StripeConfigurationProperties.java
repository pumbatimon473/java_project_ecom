package com.project.ecom.configurations.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stripe")
@Getter
@Setter
public class StripeConfigurationProperties {
    private final Api api = new Api();
    private final Payment payment = new Payment();

    @Getter
    @Setter
    public static class Api {
        private String secretKey;
    }

    @Getter
    @Setter
    public static class Payment {
        private String redirectUrl;
    }
}
