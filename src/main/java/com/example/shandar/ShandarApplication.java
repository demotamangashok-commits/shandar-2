package com.example.shandar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ShandarApplication {

    public static void main(String[] args) {
        // We use the Builder to turn off headless mode so the Print Dialog can open!
        new SpringApplicationBuilder(ShandarApplication.class)
                .headless(false)
                .run(args);
    }
}