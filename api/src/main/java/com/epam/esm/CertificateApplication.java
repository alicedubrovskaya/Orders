package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL_FORMS;

@SpringBootApplication
@EnableHypermediaSupport(type = {HAL, HAL_FORMS})
public class CertificateApplication {
    public static void main(String[] args) {
        SpringApplication.run(CertificateApplication.class, args);
    }
}
