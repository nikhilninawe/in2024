package com.endgame.service;

import com.endgame.dto.IntegrationPayload;
import com.endgame.dto.LogMessage;
import com.endgame.repository.IntegrationRequestRepository;
import com.endgame.repository.LogMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Request360Service {

    private static final Logger logger = LoggerFactory.getLogger(Request360Service.class);

    private IntegrationRequestRepository repository;
    private LogMessageRepository logMessageRepository;

    public Request360Service(@Autowired IntegrationRequestRepository repository,
                             @Autowired LogMessageRepository logMessageRepository) {
        this.repository = repository;
        this.logMessageRepository = logMessageRepository;
    }

    public List<IntegrationPayload> getIntegrationRequest(String nonce) {
        logger.debug("Fetching integration request for nonce: {}", nonce);

        List<IntegrationPayload> integrationPayloads = repository.findByNonce(nonce);
        if(integrationPayloads != null && !integrationPayloads.isEmpty()) {
            logger.info("Found {} integration payloads for nonce: {}", integrationPayloads.size(), nonce);
            return integrationPayloads;
        }

        logger.info("No integration payloads found in repository. Fetching from log messages.");
        integrationPayloads = new ArrayList<>();
        List<LogMessage> logMessages = logMessageRepository.findByCorrelationId(nonce);
        logger.debug("Found {} log messages for nonce: {}", logMessages.size(), nonce);

        for(LogMessage log : logMessages) {
            if(Objects.isNull(log.getMessage())){
                logger.warn("Encountered log message with null message for nonce: {}", nonce);
                continue;
            }
            try{
                IntegrationPayload integrationPayload = IntegrationPayload.createIntegrationPayload(log);
                integrationPayloads.add(integrationPayload);
            } catch (Exception e) {
                logger.error("Error creating IntegrationPayload from LogMessage for nonce: {}", nonce, e);
            }
        }

        logger.info("Returning {} integration payloads for nonce: {}", integrationPayloads.size(), nonce);
        return integrationPayloads;
    }
}