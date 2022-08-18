package com.savin.mop.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.savin.mop.dto.department.DepartmentDTO;
import com.savin.mop.dto.department.DepartmentDTOAndListOfUsers;
import com.savin.mop.dto.record.RecordDTOWithReferenceWithUser;
import com.savin.mop.dto.record.RecordInfoDTO;
import com.savin.mop.dto.users.NewUserDTO;
import com.savin.mop.dto.users.UserDTO;
import com.savin.mop.dto.users.UserDTOAndListOfRecords;
import com.savin.mop.dto.users.UserDTOFull;
import com.savin.mop.service.DepartmentService;
import com.savin.mop.service.RecordService;
import com.savin.mop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private UserService userService;
    private RecordService recordService;
    private DepartmentService departmentService;

    @Operation(summary = "Дать роль указанному пользователю")
    @GetMapping("/set-role/{name}-{role}")
    public ResponseEntity setRoleAdmin(@PathVariable(value = "name") String name,
                                       @PathVariable(value = "role") String role){
        return userService.setRoleForUser(name, role);
    }

    @Operation(summary = "Получение данных о пользователе по имени")
    @GetMapping("/get-user/{name}")
    public UserDTOFull getUserInfo(@PathVariable(value = "name") String name){
        return userService.getUserFullInfoForAdmin(name);
    }

    @Operation(summary = "Получение коротой сводки о всех пользователях")
    @GetMapping("/all-users")
    public List<UserDTO> getAllUsers(){
        return userService.getAll();
    }

    @Operation(summary = "Получения информации о конкретном отделе")
    @GetMapping("/get-department/{name}")
    public DepartmentDTOAndListOfUsers getDepartmentInfo(@PathVariable(value = "name") String name){
        return departmentService.getDepartmentInfo(name);
    }

    @Operation(summary = "Получение короткой сводки о всех отделах")
    @GetMapping("/all-departments")
    public List<DepartmentDTO> getAllDepartments(){
        return departmentService.getDepartmentsAll();
    }

    @Operation(summary = "Создание нового отдела")
    @PostMapping("/create-department")
    public ResponseEntity createDepartment(@RequestBody DepartmentDTO departmentDTO){
        return departmentService.createNewDepartment(departmentDTO);
    }

    @Operation(summary = "Полное изменение отдела")
    @PutMapping("/update-full-department/{nameDepartment}")
    public DepartmentDTO updateFullDepartment(@PathVariable(name = "nameDepartment") String nameDepartment,
                                               @RequestBody DepartmentDTO updateFull){
        return departmentService.fullUpdateDepartment(nameDepartment, updateFull);
    }

    @Operation(summary = "Частичное обновление отдела")
    @PatchMapping(path = "/update-department-rename/{oldName}", consumes = "application/json-patch+json")
    public DepartmentDTO RenameDepartment(@PathVariable(name = "oldName") String oldName, @RequestBody JsonPatch patch){
        return departmentService.partialUpdate(oldName, patch);
    }

    @Operation(summary = "Удаление отдела")
    @DeleteMapping("/delete-department")
    public ResponseEntity deleteDepartment(@RequestBody DepartmentDTO departmentDTO){
        return departmentService.deleteDepartment(departmentDTO);
    }

    @Operation(summary = "Информация об указанном пользователе со всеми его записями")
    @GetMapping("/get-user-with-record/{name}")
    public UserDTOAndListOfRecords getUserInfoAdmin(@PathVariable(name = "name") String name){
        return userService.getUserAndListOfRecords(name);
    }

    @Operation(summary = "Полная информация об указанном пользователе")
    @GetMapping("/get-user-full-info/{name}")
    public UserDTOFull getUserInfoFull(@PathVariable(name = "name") String name){
        return userService.getUserFullInfoForAdmin(name);
    }

    @Operation(summary = "Создание нового пользователя")
    @PostMapping("/create-new-user")
    public ResponseEntity createNewUser(@RequestBody NewUserDTO newUserDTO){
        return userService.saveNewUser(newUserDTO);
    }

    @Operation(summary = "Полное изменение пользователя")
    @PutMapping("/update-full-user/{name}")
    public ResponseEntity<UserDTOFull> updateFullUser(@PathVariable(name = "name") String name,
                                                      @RequestBody UserDTOFull userDTOFull){
        return userService.updateFullUser(name, userDTOFull);
    }

    @Operation(summary = "Частичное изменение пользователя")
    @PatchMapping("/update-user/{name}")
    public ResponseEntity<UserDTOFull> updatePartialUser(@PathVariable(name = "name") String name,
                                                         @RequestBody JsonPatch jsonPatch){
        return userService.partialUpdateUser(name, jsonPatch);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/delete-user/{name}")
    public ResponseEntity deleteUser(@PathVariable(name = "name") String name){
        return userService.deleteUser(name);
    }

    @Operation(summary = "Получения данных о записи")
    @GetMapping("/get-record/{name}")
    public RecordInfoDTO getRecordInfo(@PathVariable(name = "name") String name){
        return recordService.getRecord(name);
    }

    @Operation(summary = "Получение всех записей")
    @GetMapping("/all-records")
    public List<RecordInfoDTO> getAllRecords(){
        return recordService.getRecordsAllOfUser();
    }

    @Operation(summary = "Создание записи")
    @PostMapping("/create-record")
    public ResponseEntity createRecord(@RequestBody RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser){
        return recordService.saveNewRecord(recordDTOWithReferenceWithUser);
    }

    @Operation(summary = "Полное изменение записи")
    @PutMapping("/update-full-record/{name}")
    public ResponseEntity updateFullRecord(@RequestBody RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser){
        return recordService.updateFullRecord(recordDTOWithReferenceWithUser);
    }

    @Operation(summary = "Частичное изменение записи")
    @PatchMapping("/update-record/{name}")
    public ResponseEntity partialUpdateRecord(@PathVariable(name = "name") String name,
                                              @RequestBody JsonPatch patch){
        return recordService.partialUpdate(name, patch);
    }

    @Operation(summary = "Удаление записи")
    @DeleteMapping("/delete-record/{name}")
    public ResponseEntity deleteRecord(@PathVariable(name = "name") String name){
        return recordService.deleteRecord(name);
    }
}