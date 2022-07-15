package com.estudo.performance.costumer.personal.model;

import lombok.*;
import net.datafaker.Address;
import net.datafaker.Faker;

@Data
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @EqualsAndHashCode.Include
    private Long id;
    private String enderecoResidencial;
    private String complemento;
    private String bairro;
    private String municipio;
    private String uf;
    private String CEP;
    private String pais;

    public static Endereco createFake(){
        Faker faker = new Faker();
        Address address = faker.address();
        String streetAddress = address.streetAddress();
        String secondaryAddress = address.secondaryAddress();
        String cityName = address.cityName();
        String stateAbbr = address.stateAbbr();
        String zipCode = address.zipCode();
        String country = address.country();

        return Endereco.builder()
                .CEP(zipCode)
                .enderecoResidencial(streetAddress)
                .complemento(secondaryAddress)
                .municipio(cityName)
                .uf(stateAbbr)
                .pais(country)
                .build();
    }
}
