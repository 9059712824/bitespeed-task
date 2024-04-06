package org.learning.bitespeedtask.service;

import org.learning.bitespeedtask.dto.ContactInfoResponseDto;
import org.learning.bitespeedtask.dto.RequestDto;

import java.util.List;

public interface ContactService {
    List<ContactInfoResponseDto> identify(RequestDto requestDto);
}
