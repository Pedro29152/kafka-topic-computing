package com.estudo.performance.kafka.producer.config.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.Serializable;

public interface KafkaProducerCallback<K extends Serializable, T extends SpecificRecordBase> {

    void addCallback(String topicName, T message, ListenableFuture<SendResult<K, T>> kafkaResultFuture);
}
