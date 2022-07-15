package com.estudo.performance.costumer.personal.model;

import lombok.*;
import net.datafaker.Faker;

@Data
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class CostumerPersonal {

    @EqualsAndHashCode.Include
    private String cpf;
    private String nomeCompleto;
    private String nomeSocial;
    private String dataNascimento;
    private Integer estadoCivil;
    private String sexo;
    private String nacionalidade;
    private Endereco endereco;

    public static  CostumerPersonal createFake(String cpf){
        Faker faker = new Faker();

        String fullName = faker.name().fullName();
        String firstName = faker.name().firstName();
        String birthday = faker.date().birthday("dd/MM/yyyy");
        String gender = faker.gender().binaryTypes();
        String country = faker.nation().isoCountry();

        return CostumerPersonal.builder()
                .cpf(cpf)
                .nomeCompleto(fullName)
                .nomeSocial(firstName)
                .dataNascimento(birthday)
                .sexo(gender)
                .nacionalidade(country)
                .estadoCivil(0)
                .endereco(Endereco.createFake())
                .build();
    }
}
