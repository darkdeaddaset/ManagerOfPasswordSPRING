package kmve.afw.managerpassword.service;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import org.springframework.http.ResponseEntity;

public interface RecordService {
    ResponseEntity<RecordInfoDTO> getRecordUser(String name, String login);
    ResponseEntity<RecordInfoDTO> createRecordUser(String login, RecordInfoDTO recordInfoDTO);
    ResponseEntity<RecordDTOWithUser> updateRecordUser(String name, RecordDTOWithUser recordDTOWithUser, String login);
    ResponseEntity<RecordDTOWithUser> partialUpdateRecordUser(String name, String login, JsonPatch jsonPatch);
    ResponseEntity<String> deleteRecordUser(String name, String login);
}
