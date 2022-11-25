# Getting Started

Para criar a imagem docker execute o comando:

```
mvn clean install
```

Após a imagem criada execute o comando (de detro da pasta docker-compose)

```
docker-compose up -d
```

Para verificar se os containers foram instalados execute o comando

```
docker-compose ps
```

O sistema vai criar 2 tópicos:

costumer-personal-request-topic - Este tópico vai receber as mensagens de entrada (A chave deve ser string)

costumer-personal-response-topic  - Este tópico ter a mensagem de resposta.

As mensagens segue o padrão do Avro Record

Nesta pasta estão os arquivos com os modelos dos schemas: kafka/kafka-model/src/main/resources/avro


Have a fun!!!!
