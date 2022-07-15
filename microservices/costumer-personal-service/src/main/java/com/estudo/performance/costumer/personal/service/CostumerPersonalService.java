package com.estudo.performance.costumer.personal.service;

import com.estudo.performance.costumer.personal.mapper.CostumerPersonalMapper;
import com.estudo.performance.costumer.personal.model.CostumerPersonal;
import com.estudo.performance.kafka.producer.config.service.KafkaProducer;
import com.estudo.performance.model.CostumerPersonalAvroModel;
import com.estudo.performance.model.CostumerPersonalRequestAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CostumerPersonalService {

    private static final Logger LOG = LoggerFactory.getLogger(CostumerPersonalService.class);

    private final KafkaProducer<String, CostumerPersonalAvroModel> kafkaProducer;

    public CostumerPersonalService(KafkaProducer<String, CostumerPersonalAvroModel> kafkaProducer) {
        this.kafkaProducer = kafkaProducer  ;
    }

    public CostumerPersonal getCostumerPersonal(String cpf){
        return CostumerPersonal.createFake(cpf);
    }

    public void process(CostumerPersonalRequestAvroModel model){
        CostumerPersonal costumerPersonal = this.getCostumerPersonal(model.getCpf());
        CostumerPersonalAvroModel avroModel = CostumerPersonalMapper.MAPPER.toAvroModel(costumerPersonal);
        LOG.info(avroModel.toString());
        kafkaProducer.send("costumer-personal-response-topic", model.getCpf(), avroModel);

    }
}
