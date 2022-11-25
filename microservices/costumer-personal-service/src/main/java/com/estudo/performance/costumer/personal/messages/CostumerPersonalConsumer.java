package com.estudo.performance.costumer.personal.messages;

import com.estudo.performance.costumer.personal.service.CostumerPersonalService;
import com.estudo.performance.config.KafkaConfigData;
import com.estudo.performance.config.KafkaConsumerConfigData;
import com.estudo.performance.kafka.client.KafkaAdminClient;
import com.estudo.performance.model.CostumerPersonalRequestAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CostumerPersonalConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CostumerPersonalConsumer.class);

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final KafkaAdminClient kafkaAdminClient;

    private final KafkaConfigData kafkaConfigData;

    private final KafkaConsumerConfigData kafkaConsumerConfigData;

    private final CostumerPersonalService costumerPersonalService;


    public CostumerPersonalConsumer(KafkaListenerEndpointRegistry listenerEndpointRegistry,
                                    KafkaAdminClient adminClient,
                                    KafkaConfigData configData,
                                    KafkaConsumerConfigData consumerConfigData, CostumerPersonalService costumerPersonalService) {
        this.kafkaListenerEndpointRegistry = listenerEndpointRegistry;
        this.kafkaAdminClient = adminClient;
        this.kafkaConfigData = configData;
        this.kafkaConsumerConfigData = consumerConfigData;
        this.costumerPersonalService = costumerPersonalService;
    }

    @EventListener
    public void onAppStarted(ApplicationReadyEvent event) {
        kafkaAdminClient.checkTopicsCreated();
        LOG.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(kafkaListenerEndpointRegistry
                .getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())).start();
    }

    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "costumer-personal-request-topic")
    public void receive(@Payload List<CostumerPersonalRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
                        "sending it to costumer-service: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        messages.forEach(costumerPersonalService::process);

    }
}
