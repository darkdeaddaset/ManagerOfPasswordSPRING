package kmve.afw.managerpassword.controller;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getInfo() {
        return userService.getInfo();
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTOFull> updateFull(@RequestBody UserDTOFull userDTOFull) {
        return userService.updateFull(userDTOFull);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTOFull> partialUpdate(@RequestBody JsonPatch jsonPatch) {
        return userService.partialUpdate(jsonPatch);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> delete() {
        return userService.delete();
    }

    @GetMapping("/department/{name}")
    public ResponseEntity<DepartmentDTOFull> getInfoDepartment(@PathVariable String name) {
        return userService.getInfoDepartment(name);
    }

    @PostMapping("/department")
    public ResponseEntity<DepartmentDTOFull> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return userService.createDepartment(departmentDTO);
    }

    @PutMapping("/department/{name}")
    public ResponseEntity<DepartmentDTOFull> updateFullDepartment(@PathVariable("name") String name,
                                                                  @RequestBody DepartmentDTOFull departmentDTOFull) {
        return userService.updateFullDepartment(departmentDTOFull, name);
    }

    @PatchMapping("/department/{name}")
    ResponseEntity<DepartmentDTOFull> partialUpdateDepartment(@PathVariable("name") String name,
                                                              @RequestBody JsonPatch jsonPatch) {
        return userService.partialUpdateDepartment(name, jsonPatch);
    }

    @DeleteMapping("/department/{name}")
    ResponseEntity<String> deleteDepartment(@PathVariable String name) {
        return userService.deleteDepartment(name);
    }

    @GetMapping("/record/{name}")
    ResponseEntity<RecordInfoDTO> getRecordUser(@PathVariable String name) {
        return userService.getRecordUser(name);
    }

    @PostMapping("/record")
    ResponseEntity<RecordInfoDTO> createRecordUser(@RequestBody RecordInfoDTO recordInfoDTO) {
        return userService.createRecordUser(recordInfoDTO);
    }

    @PutMapping("/record/{name}")
    ResponseEntity<RecordDTOWithUser> updateRecordUser(@PathVariable String name,
                                                   @RequestBody RecordDTOWithUser recordDTOWithUser) {
        return userService.updateRecordUser(name, recordDTOWithUser);
    }

    @PatchMapping("/record/{name}")
    ResponseEntity<RecordDTOWithUser> partialUpdateRecordUser(@PathVariable String name,
                                                          @RequestBody JsonPatch jsonPatch) {
        return userService.partialUpdateRecordUser(name, jsonPatch);
    }

    @DeleteMapping("/record/{name}")
    ResponseEntity<String> deleteRecordUser(@PathVariable String name) {
        return userService.deleteRecordUser(name);
    }
}
