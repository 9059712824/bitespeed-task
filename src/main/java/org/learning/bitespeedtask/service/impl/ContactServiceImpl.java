package org.learning.bitespeedtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.learning.bitespeedtask.dto.ContactInfoResponseDto;
import org.learning.bitespeedtask.dto.RequestDto;
import org.learning.bitespeedtask.entity.Contact;
import org.learning.bitespeedtask.entity.LinkPrecedenceStatus;
import org.learning.bitespeedtask.repository.ContactRepository;
import org.learning.bitespeedtask.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<ContactInfoResponseDto> identify(RequestDto requestDto) {

        List<Contact> contactsByEmail = requestDto.getEmail() == null ? Collections.emptyList() : contactRepository.getByEmail(requestDto.getEmail());
        List<Contact> contactsByPhoneNumber = requestDto.getPhoneNumber() == null ? Collections.emptyList() : contactRepository.getByPhoneNumber(requestDto.getPhoneNumber());

        List<Contact> allContacts = new ArrayList<>();
        allContacts.addAll(contactsByEmail);
        allContacts.addAll(contactsByPhoneNumber);

        if (allContacts.isEmpty()) {
            allContacts.add(contactRepository.save(Contact.builder()
                    .id((int) (contactRepository.count() + 1))
                    .email(requestDto.getEmail())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .linkPrecedence(LinkPrecedenceStatus.PRIMARY)
                    .build()));
        }
        allContacts.addAll(processContacts(allContacts));

        int primaryId = 0;
        Set<Integer> secondaryIds = new HashSet<>();
        for (Contact contact : allContacts) {
            if (contact.getLinkPrecedence().equals(LinkPrecedenceStatus.PRIMARY)) {
                primaryId = contact.getId();
            }
            if (contact.getLinkPrecedence().equals(LinkPrecedenceStatus.SECONDARY)) {
                secondaryIds.add(contact.getId());
            }
        }

        Set<String> emailSet = new HashSet<>();
        Set<String> phoneNumberSet = new HashSet<>();
        allContacts.forEach(contact -> {
            emailSet.add(contact.getEmail());
            phoneNumberSet.add(contact.getPhoneNumber());
        });
        allContacts.forEach(contact -> {
            emailSet.add(contact.getEmail());
            phoneNumberSet.add(contact.getPhoneNumber());
        });

        ContactInfoResponseDto contactInfoResponse = ContactInfoResponseDto.builder()
                .emails(new ArrayList<>(emailSet))
                .phoneNumbers(new ArrayList<>(phoneNumberSet))
                .primaryContactId(primaryId)
                .secondaryContactIds(new ArrayList<>(secondaryIds))
                .build();

        return Collections.singletonList(contactInfoResponse);
    }

    private List<Contact> processContacts(List<Contact> contacts) {
        List<Contact> processedContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getLinkPrecedence().equals(LinkPrecedenceStatus.PRIMARY)) {
                var linkedContacts = contactRepository.getByLinkedId(contact.getId());
                processedContacts.addAll(linkedContacts);
            }
            if (contact.getLinkPrecedence().equals(LinkPrecedenceStatus.SECONDARY)) {
                var linkedContacts = contactRepository.findById(contact.getLinkedId());
                linkedContacts.ifPresent(processedContacts::add);
            }
        }
        return processedContacts;
    }
}
