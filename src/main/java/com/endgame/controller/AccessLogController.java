package com.endgame.controller;

import com.endgame.dto.AccessLogMessage;
import com.endgame.dto.IntegrationPayload;
import com.endgame.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://9406-2405-201-301d-e85d-6912-a330-d924-84f9.ngrok-free.app/",
        "https://stage-swift.turvo.net/"})
public class AccessLogController {

    @Autowired private AccessLogService accessLogService;

    @GetMapping("/access_log_by_businessId")
    public List<AccessLogMessage> getAccessLogByBusinessId(@RequestParam int businessId,
                                                           @RequestParam String method,
                                                           @RequestParam String environment) {
        return accessLogService.getAccessLog(businessId, method, environment);
    }
}
