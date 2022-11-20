package kmve.afw.managerpassword.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.mapper.RecordMapper;
import kmve.afw.managerpassword.repository.RecordRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.service.abstracts.record.AbstractRecord;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends AbstractRecord implements RecordService {
    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper, GeneratePassword generatePassword, UserRepository userRepository, ObjectMapper objectMapper) {
        super(recordRepository, recordMapper, generatePassword, userRepository, objectMapper);
    }

    @Override
    public ResponseEntity<RecordInfoDTO> getRecordUser(String name, String login) {
        return getInfoRecord(name, login);
    }

    @Override
    public ResponseEntity<RecordInfoDTO> createRecordUser(String login, RecordInfoDTO recordInfoDTO) {
        return createRecord(login, recordInfoDTO);
    }

    @Override
    public ResponseEntity<RecordDTOWithUser> updateRecordUser(String name, RecordDTOWithUser recordDTOWithUser, String login) {
        return updateRecord(name, recordDTOWithUser, login);
    }

    @Override
    public ResponseEntity<RecordDTOWithUser> partialUpdateRecordUser(String name, String login, JsonPatch jsonPatch) {
        return partialUpdateRecord(name, login, jsonPatch);
    }

    @Override
    public ResponseEntity<String> deleteRecordUser(String name, String login) {
        return deleteRecord(name, login);
    }
}
