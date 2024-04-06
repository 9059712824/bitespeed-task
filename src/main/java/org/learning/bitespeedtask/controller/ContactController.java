package org.learning.bitespeedtask.controller;

import lombok.RequiredArgsConstructor;
import org.learning.bitespeedtask.dto.RequestDto;
import org.learning.bitespeedtask.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/identity")
    public ResponseEntity<?> identity(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(contactService.identify(requestDto));
    }
}
