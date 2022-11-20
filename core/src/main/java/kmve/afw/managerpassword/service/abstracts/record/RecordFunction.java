package kmve.afw.managerpassword.service.abstracts.record;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import org.springframework.http.ResponseEntity;

public interface RecordFunction {
    ResponseEntity<RecordInfoDTO> getInfoRecord(String name, String login);
    ResponseEntity<RecordInfoDTO> createRecord(String login, RecordInfoDTO recordInfoDTO);
    ResponseEntity<RecordDTOWithUser> updateRecord(String name, RecordDTOWithUser recordDTOWithUser, String login);
    ResponseEntity<RecordDTOWithUser> partialUpdateRecord(String name, String login, JsonPatch jsonPatch);
    ResponseEntity<String> deleteRecord(String name, String login);
}
