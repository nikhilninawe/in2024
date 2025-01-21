package com.endgame.service;

import com.endgame.dto.IntegrationPayload;
import com.endgame.dto.LogMessage;
import com.endgame.repository.IntegrationRequestRepository;
import com.endgame.repository.LogMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class Request360Service {

    private static final Logger logger = LoggerFactory.getLogger(Request360Service.class);

    private LogMessageRepository logMessageRepository;

    public Request360Service(@Autowired LogMessageRepository logMessageRepository) {
        this.logMessageRepository = logMessageRepository;
    }

    public List<IntegrationPayload> getIntegrationRequest(String nonce, String environment) {
        logger.debug("Fetching integration request for nonce: {}", nonce);
        List<IntegrationPayload> integrationPayloads = new ArrayList<>();

        //Extract Payload
        List<LogMessage> logMessages = logMessageRepository.findPayloadByCorrelationId(nonce, environment);
        extracted(logMessages, integrationPayloads, "incoming_payload");

        //Extract Error
        logMessages = logMessageRepository.findByCorrelationIdAndSeverityAndRoleAndEnvironment(nonce, "ERROR", "api", environment);
        extracted(logMessages, integrationPayloads, "error");
        logger.debug("Found {} log messages for nonce: {}", logMessages.size(), nonce);

        //Extract Webhook outgoing payload

        logger.info("Returning {} integration payloads for nonce: {}", integrationPayloads.size(), nonce);
        return integrationPayloads;
    }

    private static void extracted(List<LogMessage> logMessages, List<IntegrationPayload> integrationPayloads, String type) {
        for(LogMessage log : logMessages) {
            if(Objects.isNull(log.getMessage())){
                logger.warn("Encountered log message with null message");
                continue;
            }
            try{
                IntegrationPayload integrationPayload = IntegrationPayload.createIntegrationPayload(log);
                integrationPayload.type = type;
                if(type.equals("incoming_payload")){
                    integrationPayload.payload = integrationPayload.payload.replace("Request payload: {}", "").
                            replace("\"", "'").replace("\n", "");
                }
                integrationPayloads.add(integrationPayload);
            } catch (Exception e) {
                logger.error("Error creating IntegrationPayload from LogMessage", e);
            }
        }
    }

    public List<IntegrationPayload> getIntegrationRequestByBusinessId(int businessId, String environment) {
        logger.debug("Fetching integration request for businessId: {}", businessId);
        List<IntegrationPayload> integrationPayloads = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "timestamp"));
        List<LogMessage> logMessages = logMessageRepository.findLasTenPayload(
                businessId,
                environment,
                pageRequest
        );
        extracted(logMessages, integrationPayloads, "incoming_payload");
        return integrationPayloads;
    }

}