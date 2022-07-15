package com.estudo.performance.costumer.personal.mapper;

import com.estudo.performance.costumer.personal.model.CostumerPersonal;
import com.estudo.performance.model.CostumerPersonalAvroModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CostumerPersonalMapper {

    CostumerPersonalMapper MAPPER = Mappers.getMapper(CostumerPersonalMapper.class);


    CostumerPersonalAvroModel toAvroModel(CostumerPersonal entity);

    CostumerPersonal toEntity(CostumerPersonalAvroModel avroModel);
}
