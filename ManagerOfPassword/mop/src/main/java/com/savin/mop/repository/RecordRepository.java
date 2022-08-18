package com.savin.mop.repository;

import com.savin.mop.dto.record.RecordInfoDTO;
import com.savin.mop.model.Record;
import com.savin.mop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> getRecordByName(String name);
    Optional<Record> getRecordsByNameAndPassword(String name, String password);
    Optional<Record> getRecordsByNameAndUser(String name, User user);
    List<Record> getRecordsByUser(User user);
    //List<Record> getRecordsByUserId(String name, String password);
}
