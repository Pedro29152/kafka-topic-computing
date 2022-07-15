package com.estudo.performance.costumer.personal;

import com.estudo.performance.costumer.personal.init.StreamInitializer;
import com.estudo.performance.costumer.personal.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.estudo.performance"})
public class CostumerPersonalServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CostumerPersonalServiceApplication.class);

    private final StreamInitializer streamInitializer;
    private final StreamRunner streamRunner;

    public CostumerPersonalServiceApplication(StreamInitializer streamInitializer, StreamRunner streamRunner) {
        this.streamInitializer = streamInitializer;
        this.streamRunner = streamRunner;
    }


    public static void main(String[] args) {
        SpringApplication.run(CostumerPersonalServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("App starts...");
        streamInitializer.init();
        streamRunner.start();

    }
}
