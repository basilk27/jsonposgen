package com.mbsystems.jsonposgen.service;

import com.mbsystems.jsonposgen.model.PosInvoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    @Value("${application.configs.topic.name}")
    private String TOPIC_NAME;

    private KafkaTemplate<String, PosInvoice> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, PosInvoice> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(PosInvoice invoice) {
        log.info(String.format("Producing Invoice No: %s Customer Type: %s",
                invoice.getInvoiceNumber(),
                invoice.getCustomerType()));

        kafkaTemplate.send(TOPIC_NAME, invoice.getStoreID(), invoice);
    }
}
