package com.jobscheduler1.controller;

import com.jobscheduler1.model.dto.CallbackRequest;
import com.jobscheduler1.service.CallbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/callbacks")
@RequiredArgsConstructor
public class CallbackController {

    private final CallbackService callbackService;

    @PostMapping
    public ResponseEntity<Void> scheduleCallback(@Valid @RequestBody CallbackRequest callbackRequest) {
        callbackService.scheduleCallback(
                callbackRequest.getMessage(),
                callbackRequest.getDelayMinutes(),
                callbackRequest.getTopic(),
                callbackRequest.getMetadata()
        );
        return ResponseEntity.accepted().build();
    }
}