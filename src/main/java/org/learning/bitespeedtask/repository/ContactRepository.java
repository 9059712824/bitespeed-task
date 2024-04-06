package org.learning.bitespeedtask.repository;

import org.learning.bitespeedtask.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> getByEmail(String email);

    @Query(value = "select * from contact where phone_number = :phoneNumber ", nativeQuery = true)
    List<Contact> getByPhoneNumber(String phoneNumber);

    List<Contact> getByLinkedId(int linkedId);
}
