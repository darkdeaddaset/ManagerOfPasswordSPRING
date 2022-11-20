package kmve.afw.managerpassword.service.abstracts.record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.mapper.RecordMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.repository.RecordRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public abstract class AbstractRecord implements RecordFunction{
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final GeneratePassword generatePassword;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    protected AbstractRecord(RecordRepository recordRepository, RecordMapper recordMapper, GeneratePassword generatePassword, UserRepository userRepository, ObjectMapper objectMapper) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
        this.generatePassword = generatePassword;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    private User generateUser(String login) {
        return userRepository.getUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    @Override
    public ResponseEntity<RecordInfoDTO> getInfoRecord(String name, String login) {
        return recordRepository.getRecordByNameAndUser(name, generateUser(login))
                .map(recordMapper::getFromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
    }

    @Override
    public ResponseEntity<RecordInfoDTO> createRecord(String login, RecordInfoDTO recordInfoDTO) {
        User user = generateUser(login);
        return Optional.of(recordInfoDTO)
                .map(recordMapper::getFromDTO)
                .map(create -> {
                    create.setUser(user);
                    if (recordInfoDTO.getPassword().isBlank() || recordInfoDTO.getPassword().isEmpty()) {
                        create.setPassword(generatePassword.generatePassword(32, true, true));
                    }
                    if (recordInfoDTO.getName().isBlank() || recordInfoDTO.getName().isEmpty()) {
                        return new ResponseEntity<>(recordInfoDTO, HttpStatus.BAD_REQUEST);
                    }
                    recordRepository.save(create);
                    RecordInfoDTO recordInfoDTO1 = recordMapper.getFromModel(create);
                    return new ResponseEntity<>(recordInfoDTO1, HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Неудалось создать запись"));
    }

    @Override
    public ResponseEntity<RecordDTOWithUser> updateRecord(String name,
                                                      RecordDTOWithUser recordDTOWithUser,
                                                      String login) {
        User user = generateUser(login);
        return recordRepository.getRecordByNameAndUser(name, user)
                .map(updateFull -> {
                    if (recordDTOWithUser.getPassword().isBlank() || recordDTOWithUser.getPassword().isEmpty()) {
                        recordDTOWithUser.setPassword(generatePassword.generatePassword(32, true, true));
                    }

                    recordMapper.getFromModelForFull(recordDTOWithUser, updateFull);
                    recordRepository.save(updateFull);

                    return new ResponseEntity<>(recordDTOWithUser, HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Невозможно обновить запись"));
    }

    @Override
    public ResponseEntity<RecordDTOWithUser> partialUpdateRecord(String name, String login, JsonPatch jsonPatch) {
        User user = generateUser(login);
        RecordDTOWithUser recordDTOWithUser = recordRepository.getRecordByNameAndUser(name, user)
                .map(recordMapper::getFromModelForDTOFull)
                .orElseThrow();
        RecordDTOWithUser recordDTOWithUser1;

        try {
            recordDTOWithUser1 = applyPatchRecord(jsonPatch, recordDTOWithUser);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return updateRecord(name, recordDTOWithUser1, login);
    }

    @Override
    public ResponseEntity<String> deleteRecord(String name, String login) {
        User user = generateUser(login);
        return recordRepository.getRecordByNameAndUser(name, user)
                .map(delete -> {
                    recordRepository.delete(delete);
                return new ResponseEntity<>("Запись удалена", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Запись не может быть удалена"));
    }

    public RecordDTOWithUser applyPatchRecord(JsonPatch jsonPatch, RecordDTOWithUser recordDTOWithUser) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(recordDTOWithUser, JsonNode.class));
        return objectMapper.treeToValue(patched, RecordDTOWithUser.class);
    }
}
