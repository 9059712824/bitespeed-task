package org.learning.bitespeedtask.bootstrap;

import lombok.RequiredArgsConstructor;
import org.learning.bitespeedtask.entity.Contact;
import org.learning.bitespeedtask.entity.LinkPrecedenceStatus;
import org.learning.bitespeedtask.repository.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final ContactRepository contactRepository;

    @Override
    public void run(String... args) {
        if (contactRepository.count() <= 2) {
            var contact1 = Contact.builder()
                    .id(1)
                    .phoneNumber("123456")
                    .email("lorraine@hillvalley.edu")
                    .linkPrecedence(LinkPrecedenceStatus.PRIMARY)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

            contactRepository.save(contact1);

            var contact2 = Contact.builder()
                    .id(23)
                    .phoneNumber("123456")
                    .email("mcfly@hillvalley.edu")
                    .linkedId(1)
                    .linkPrecedence(LinkPrecedenceStatus.SECONDARY)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

            contactRepository.save(contact2);
        }
    }
}
