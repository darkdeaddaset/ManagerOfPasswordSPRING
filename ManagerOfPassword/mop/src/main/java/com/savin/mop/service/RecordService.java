package com.savin.mop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.savin.mop.dto.record.RecordDTOWithReferenceWithUser;
import com.savin.mop.dto.record.RecordInfoDTO;
import com.savin.mop.dto.record.RecordNameDTO;
import com.savin.mop.mapper.RecordMapper;
import com.savin.mop.model.User;
import com.savin.mop.repository.RecordRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecordService {
    private GeneratePassword generatePassword;
    private RecordRepository recordRepository;
    private UserService userService;
    private RecordMapper mapper;
    private ObjectMapper objectMapper;

    public RecordInfoDTO getRecord(String nameOfRecord){
        return recordRepository.getRecordByName(nameOfRecord)
                .map(mapper::getFromRecord)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
    }

    public List<RecordInfoDTO> getRecordsAllOfUser(){
        return recordRepository.findAll()
                .stream()
                .map(mapper::getFromRecord)
                .collect(Collectors.toList());
    }

    //For admin
    public ResponseEntity saveNewRecord(RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser){
        return Optional.of(recordDTOWithReferenceWithUser)
                .map(mapper::getFromRecordWithUserDTO)
                .map(saveNewRecord -> {
                    if (recordDTOWithReferenceWithUser.getPassword() == null ||
                    recordDTOWithReferenceWithUser.getPassword().isEmpty() ||
                    recordDTOWithReferenceWithUser.getPassword().isBlank()){
                        saveNewRecord.setPassword(generatePassword.generatePassword(30, true, true));
                    }
                    recordRepository.save(saveNewRecord);
                    return new ResponseEntity("Запись создана и сохранена", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Запись не может быть создана"));
    }

    //For admin
    public ResponseEntity updateFullRecord(RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser){
        return recordRepository.getRecordsByNameAndPassword(recordDTOWithReferenceWithUser.getName(), recordDTOWithReferenceWithUser.getPassword())
                //.map(mapper::getFromRecordWithUser)
                .map(update -> {
                    if (recordDTOWithReferenceWithUser.getPassword() == null ||
                    recordDTOWithReferenceWithUser.getPassword().isEmpty() ||
                    recordDTOWithReferenceWithUser.getPassword().isBlank()){
                        recordDTOWithReferenceWithUser.setPassword(generatePassword.generatePassword(30, true, true));
                    }
                    mapper.updateFull(recordDTOWithReferenceWithUser, update);
                    recordRepository.save(update);
                    return ResponseEntity.ok(update);})
                .orElseThrow(() -> new EntityNotFoundException("Запись не может быть обновлена"));
    }

    //For admin
    public ResponseEntity<RecordDTOWithReferenceWithUser> partialUpdate(String name, JsonPatch patch){
        RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser = recordRepository.getRecordByName(name)
                .map(mapper::getFromRecordWithUser)
                .orElseThrow();
        RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser1;
        try {
            recordDTOWithReferenceWithUser1 = applyPatchJson(patch, recordDTOWithReferenceWithUser);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return updateFullRecord(recordDTOWithReferenceWithUser1);
    }

    //For admin
    public ResponseEntity deleteRecord(String name){
        return recordRepository.getRecordByName(name)
                .map(delete -> {recordRepository.delete(delete);
                    return new ResponseEntity("Запись удалена", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Запись не может быть удалена"));
    }

    private RecordDTOWithReferenceWithUser applyPatchJson(JsonPatch patch,
                                                          RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser)
            throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(patch, JsonNode.class));
        return objectMapper.treeToValue(patched, RecordDTOWithReferenceWithUser.class);
    }

    private User getCurrentIdOfUser(){
        return userService.currentUserForUser();
    }

    //For users
    public ResponseEntity saveNewRecord(RecordInfoDTO recordInfoDTO){
        return Optional.of(recordInfoDTO)
                .map(mapper::getFromRecordInfoDTO)
                .map(saveNewRecord -> {
                    saveNewRecord.setUser(getCurrentIdOfUser());
                    if (recordInfoDTO.getPassword() == null ||
                        recordInfoDTO.getPassword().isEmpty() ||
                        recordInfoDTO.getPassword().isBlank()){
                    saveNewRecord.setPassword(generatePassword.generatePassword(30, true, true));
                }
                    recordRepository.save(saveNewRecord);
                    return new ResponseEntity("Запись создана и сохранена", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Запись не может быть создана"));
    }

    public RecordInfoDTO getRecordForUser(String name){
        return recordRepository.getRecordsByNameAndUser(name, getCurrentIdOfUser())
                .map(mapper::getFromRecord)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
    }

    public List<RecordNameDTO> getAllRecordOfUser(){
        return recordRepository.getRecordsByUser(getCurrentIdOfUser())
                .stream()
                .map(mapper::getFromRecordNameDTO)
                .collect(Collectors.toList());
    }
}
