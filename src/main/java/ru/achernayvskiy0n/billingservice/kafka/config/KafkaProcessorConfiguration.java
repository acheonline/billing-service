package ru.achernayvskiy0n.billingservice.kafka.config;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.RequestMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Configuration
public class KafkaProcessorConfiguration {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${billing-service.transport.topics.account.create}")
    private String accountCreateTopic;

    @Value("${billing-service.transport.topics.order.request}")
    private String billingRequestTopic;

    @Bean("accountCreateConsumerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, AccountCreationMessage>
    accountCreatKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AccountCreationMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientIdReceiver());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, AccountCreationMessage> clientIdReceiver() {
        return createConsumerFactory(AccountCreationMessage.class, accountCreateTopic);
    }

    ////// -  replyRequestTemplate with Order Service
    @Bean
    public ConsumerFactory<String, RequestMessage> requestConsumerFactory() {
        return createConsumerFactory(RequestMessage.class, billingRequestTopic);
    }

    @Bean("requestListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, RequestMessage>> requestListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RequestMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(requestConsumerFactory());
        factory.setReplyTemplate(replyTemplate());
        return factory;
    }

    @Bean
    public ProducerFactory<String, ResponseMessage> replyProducerFactory() {
        var producer = kafkaProperties.getProducer();

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producer.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, producer.getClientId());
        props.put(ProducerConfig.ACKS_CONFIG, producer.getAcks());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonSerializer.class);
        props.put(
                org.springframework.kafka.support.serializer.JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, ResponseMessage> replyTemplate() {
        return new KafkaTemplate<>(replyProducerFactory());
    }

    @SneakyThrows
    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> valueType, String topic) {
        Map<String, Object> props = new HashMap<>();
        var consumer = kafkaProperties.getConsumer();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumer.getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumer.getClientId() + "-" + topic);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, valueType);

        return new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new JsonDeserializer<>(valueType));
    }
}
