package org.hejia.jrb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
@ComponentScan({"org.hejia.jrb", "org.hejia.common"})
public class ServiceSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }

}
