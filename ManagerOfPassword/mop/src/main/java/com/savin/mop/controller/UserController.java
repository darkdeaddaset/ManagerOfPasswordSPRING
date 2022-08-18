package com.savin.mop.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.savin.mop.dto.record.RecordDTOWithReferenceWithUser;
import com.savin.mop.dto.record.RecordInfoDTO;
import com.savin.mop.dto.record.RecordNameDTO;
import com.savin.mop.dto.users.UserDTOFull;
import com.savin.mop.service.RecordService;
import com.savin.mop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private RecordService recordService;

    @Operation(summary = "Информация об пользователе")
    @GetMapping("/about-me")
    public UserDTOFull getInfo(){
        return userService.currentUser();
    }

    @Operation(summary = "Получить запись")
    @GetMapping("/record/{name}")
    public RecordInfoDTO getRecord(@PathVariable(name = "name") String name){
        return recordService.getRecordForUser(name);
    }

    @Operation(summary = "Список записей пользователя")
    @GetMapping("/all-records")
    public List<RecordNameDTO> getRecordsAll(){
        return recordService.getAllRecordOfUser();
    }

    @Operation(summary = "Создание записи")
    @PostMapping("/create-record")
    public ResponseEntity createRecord(@RequestBody RecordInfoDTO recordInfoDTO){
        return recordService.saveNewRecord(recordInfoDTO);
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
}