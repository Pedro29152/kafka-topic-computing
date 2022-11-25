package com.estudo.performance.kafka.producer.config.service.impl;

import com.estudo.performance.kafka.producer.config.service.KafkaProducerCallback;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaProducerDefaultCallback implements KafkaProducerCallback {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerDefaultCallback.class);

    @Override
    public void addCallback(String topicName, SpecificRecordBase message, ListenableFuture kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<SendResult>() {
            @Override
            public void onSuccess(SendResult result) {
                RecordMetadata metadata = result.getRecordMetadata();
                LOG.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOG.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

        });
    }
}
