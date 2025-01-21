package com.endgame.service;

import com.endgame.dto.AccessLogMessage;
import com.endgame.dto.LogMessage;
import com.endgame.repository.AccessLogRepository;
import com.endgame.repository.LogMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AccessLogService {

    private AccessLogRepository accessLogRepository;
    private LogMessageRepository logMessageRepository;

    public AccessLogService(@Autowired AccessLogRepository accessLogRepository,
                            @Autowired LogMessageRepository logMessageRepository) {
        this.accessLogRepository = accessLogRepository;
        this.logMessageRepository = logMessageRepository;
    }

    public List<AccessLogMessage> getAccessLog(int businessId,
                                               String method,
                                               String environment) {
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "timestamp"));
        List<AccessLogMessage> accessLogMessages = accessLogRepository.findLastTenAccessLogs("shipments", "api", businessId, environment, method, pageRequest);
        for (AccessLogMessage accessLogMessage : accessLogMessages) {
            log.info("AccessLogMessage: {}", accessLogMessage);
            PageRequest pageRequest2 = PageRequest.of(0, 1,
                    Sort.by(Sort.Direction.DESC, "timestamp"));
            Optional<String> logMessage = logMessageRepository.findByHostAndThreadAndClassNameAndEnvironmentAndTarget(accessLogMessage.getHost(),
                            accessLogMessage.getThread(),
                            "com.arl.mg.filters.PublicApiFilter",
                            environment,
                            accessLogMessage.getTarget(),
                            pageRequest2)
                    .stream()
                    .findFirst()
                    .map(LogMessage::getCorrelationId);
            accessLogMessage.setCorrelationId(logMessage.orElse(null));
        }
        return accessLogMessages;
    }

}
