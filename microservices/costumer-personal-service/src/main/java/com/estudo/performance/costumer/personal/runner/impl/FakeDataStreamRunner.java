package com.estudo.performance.costumer.personal.runner.impl;

import com.estudo.performance.costumer.personal.runner.StreamRunner;
import com.estudo.performance.kafka.producer.config.service.impl.CostumerPersonalRequestKafkaProducer;
import com.estudo.performance.model.CostumerPersonalRequestAvroModel;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class FakeDataStreamRunner implements StreamRunner {

    @Value("${app.create-fake-request}")
    private Boolean createFakeRequest;

    @Value("${app.sleep-time-ms}")
    private Long sleepTimeMs;
    private static final Logger LOG = LoggerFactory.getLogger(FakeDataStreamRunner.class);

    private final CostumerPersonalRequestKafkaProducer costumerPersonalRequestProducer;

    public FakeDataStreamRunner(CostumerPersonalRequestKafkaProducer costumerPersonalRequestProducer) {
        this.costumerPersonalRequestProducer = costumerPersonalRequestProducer;
    }

    @Override
    public void start() {
        if (createFakeRequest)
            simulateStream(this.sleepTimeMs);
    }

    private void simulateStream(long sleepTimeMs) {
        Executors.newSingleThreadExecutor().submit(() -> {
            Faker faker = new Faker();
            try {
                while (true) {
                    String cpf = faker.cpf().valid(false);
                    CostumerPersonalRequestAvroModel request = CostumerPersonalRequestAvroModel.newBuilder().setCpf(cpf).build();
                    String topicName = "costumer-personal-request-topic";
                    costumerPersonalRequestProducer.send(topicName, cpf, request);
                    sleep(sleepTimeMs);
                }
            } catch (Exception e) {
                LOG.error("Error creating DATA-FAKE!", e);
            }
        });
    }

    private void sleep(long sleepTimeMs) throws Exception {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new Exception("Error while sleeping for waiting new DATA-FAKE to create!!");
        }
    }
}
