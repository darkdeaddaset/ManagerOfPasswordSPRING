package kmve.afw.managerpassword.controller;

import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.model.enums.Role;
import kmve.afw.managerpassword.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users-all")
    public List<UserDTO> getAllUserForAdmin() {
        return adminService.getAllUserForAdmin();
    }

    @GetMapping("/users-all-full")
    public List<UserDTOFull> getAllUserFullForAdmin() {
        return adminService.getAllUserFullForAdmin();
    }

    @GetMapping("/records-all")
    public List<RecordInfoDTO> getAllRecordsForAdmin() {
        return adminService.getAllRecordsForAdmin();
    }

    @GetMapping("/records-all-full")
    public List<RecordDTOWithUser> getAllRecordFullForAdmin() {
        return adminService.getAllRecordsFullForAdmin();
    }

    @GetMapping("/role/{login}/{role}")
    public ResponseEntity<String> updateRole(@PathVariable("login") String login, @PathVariable("role") String role) {
        return adminService.setRoleForUser(login, role);
    }

    @GetMapping("/user/{login}")
    public UserDTO getUserForAdmin(@PathVariable("login") String login) {
        return adminService.getUserForAdmin(login);
    }

    @GetMapping("/user-full/{login}")
    public UserDTOFull getUserFullForAdmin(@PathVariable("login") String login) {
        return adminService.getUserFullForAdmin(login);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUserForAdmin(@RequestBody UserDTO userDTO) {
        return adminService.createUserForAdmin(userDTO);
    }

    @DeleteMapping("/delete-user/{login}")
    public ResponseEntity<String> deleteUserForAdmin(@PathVariable("login") String login) {
        return adminService.deleteUserForAdmin(login);
    }

    @DeleteMapping("/delete-department/{name}")
    public ResponseEntity<String> deleteDepartmentForAdmin(@PathVariable("name") String name) {
        return adminService.deleteDepartmentForAdmin(name);
    }

    @DeleteMapping("/delete-record/{name}")
    public ResponseEntity<String> deleteRecordForAdmin(@PathVariable("name") String name) {
        return adminService.deleteRecordForAdmin(name);
    }
}