package com.esprit.tn.candidat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
@Service
public class JobConsumer {
    private final CandidatService candidatService;
    private static final Logger log = LoggerFactory.getLogger(JobConsumer.class);
    public JobConsumer(CandidatService candidatService,ObjectMapper objectMapper) {
        this.candidatService = candidatService;
    }
    // Ecoute la file CANDID_JOB_QUEUE
// Spring convertit automatiquement le JSON en JobDTO grâce au MessageConverter
    @RabbitListener(queues = RabbitMQConfig.CANDID_JOB_QUEUE, containerFactory =
            "rabbitListenerContainerFactory")
    public void receiveJob(JobDTO jobDTO) {
        log.info("JobDTO reçu depuis RabbitMQ : {}", jobDTO);
// Déléguer la logique métier
        candidatService.receiveJobService(jobDTO);
    }
}