package com.estudo.performance.kafka.producer.config.service.impl;

import com.estudo.performance.kafka.producer.config.service.KafkaProducer;
import com.estudo.performance.kafka.producer.config.service.KafkaProducerCallback;
import com.estudo.performance.model.CostumerPersonalAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PreDestroy;

@Service
public class CostumerPersonalKafkaProducer implements KafkaProducer<String, CostumerPersonalAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(CostumerPersonalKafkaProducer.class);

    private final KafkaTemplate<String, CostumerPersonalAvroModel> kafkaTemplate;

    private final KafkaProducerCallback kafkaProducerCallBack;

    public CostumerPersonalKafkaProducer(KafkaTemplate template, KafkaProducerCallback kafkaProducerCallBack) {
        this.kafkaTemplate = template;
        this.kafkaProducerCallBack = kafkaProducerCallBack;

    }

    @Override
    public void send(String topicName, String key, CostumerPersonalAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<String, CostumerPersonalAvroModel>> kafkaResultFuture =  kafkaTemplate.send(topicName, key, message);
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
