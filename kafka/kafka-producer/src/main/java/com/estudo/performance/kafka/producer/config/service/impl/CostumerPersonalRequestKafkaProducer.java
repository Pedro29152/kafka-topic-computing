package com.estudo.performance.kafka.producer.config.service.impl;

import com.estudo.performance.kafka.producer.config.service.KafkaProducerCallback;
import com.estudo.performance.model.CostumerPersonalRequestAvroModel;
import com.estudo.performance.kafka.producer.config.service.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PreDestroy;

@Service
public class CostumerPersonalRequestKafkaProducer implements KafkaProducer<String, CostumerPersonalRequestAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(CostumerPersonalRequestKafkaProducer.class);

    private final KafkaTemplate<String, CostumerPersonalRequestAvroModel> kafkaTemplate;

    private final KafkaProducerCallback kafkaProducerCallBack;

    public CostumerPersonalRequestKafkaProducer(KafkaTemplate template, KafkaProducerCallback kafkaProducerCallback) {
        this.kafkaTemplate = template;
        this.kafkaProducerCallBack = kafkaProducerCallback;
    }

    public void send(String topicName, String key, CostumerPersonalRequestAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<String, CostumerPersonalRequestAvroModel>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
        kafkaProducerCallBack.addCallback(topicName, message, kafkaResultFuture);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }


}
