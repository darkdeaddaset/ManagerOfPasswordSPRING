package com.savin.mop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.savin.mop.dto.users.NewUserDTO;
import com.savin.mop.dto.users.UserDTO;
import com.savin.mop.dto.users.UserDTOAndListOfRecords;
import com.savin.mop.dto.users.UserDTOFull;
import com.savin.mop.mapper.UserMapper;
import com.savin.mop.model.User;
import com.savin.mop.model.enums.Roles;
import com.savin.mop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserService {
    private GeneratePassword generatePassword;
    private UserRepository userRepository;
    private UserMapper mapper;
    private ObjectMapper objectMapper;

    public UserDTOFull currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserFullInfoForAdmin(authentication.getName());
    }

    public User currentUserForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByName(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public ResponseEntity setRoleForUser(String name, String role){
        return userRepository.getUserByName(name)
                .map(setRoles ->{
                    if (role.equals("ADMIN") || role.equals("admin")){
                        setRoles.setRoles(roleAdmin());
                    } else {
                        setRoles.setRoles(roleUser());
                    }
                    userRepository.save(setRoles);
                    return new ResponseEntity("Роль для пользователя установлена", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    private Set<Roles> roleAdmin(){
        Set<Roles> rolesSet = new HashSet<>();
        rolesSet.add(Roles.ADMIN);

        return rolesSet;
    }

    private Set<Roles> roleUser(){
        Set<Roles> rolesSet = new HashSet<>();
        rolesSet.add(Roles.USER);

        return rolesSet;
    }

    public List<UserDTO> getAll(){
        return userRepository.findAll()
                .stream()
                .map(mapper::getFromUser)
                .collect(Collectors.toList());
    }

    public UserDTOAndListOfRecords getUserAndListOfRecords(String name){
        return userRepository.getUserByName(name)
                .map(mapper::getFromUserAndList)
                .orElseThrow(() -> new EntityNotFoundException("Нет пользователя с такими данными"));
    }

    //Для пользователей
    public UserDTOFull getUserFullInfo(UserDTO userDTO){
        return userRepository.getUserByNameAndPassword(userDTO.getName(), userDTO.getPassword())
                .map(mapper::getFromUserFull)
                .orElseThrow(() -> new EntityNotFoundException("Нет пользователя с такими данными"));
    }

    public UserDTOFull getUserFullInfoForAdmin(String name){
        return userRepository.getUserByName(name)
                .map(mapper::getFromUserFull)
                .orElseThrow(() -> new EntityNotFoundException("Нет пользователя с таким именем"));
    }

    public ResponseEntity saveNewUser(NewUserDTO newUserDTO){
        return Optional.of(newUserDTO)
                .map(mapper::createNewUser)
                .map(saveNew -> {
                    if (saveNew.getPassword() == null || saveNew.getPassword().isEmpty() || saveNew.getPassword().isBlank()) {
                        saveNew.setPassword(generatePassword.generatePassword(30, true, true));
                    }
                    userRepository.save(saveNew);
                    return new ResponseEntity("Пользователь создан", HttpStatus.OK);})
                .orElseThrow(() -> new RuntimeException("Пользователь не может быть создан"));
    }

    public ResponseEntity<UserDTOFull> updateFullUser(String nameUser, UserDTOFull userDTOFull){
        return userRepository.getUserByName(nameUser)
                .map(update -> {
                    if (userDTOFull.getPassword() == null || userDTOFull.getPassword().isEmpty() || userDTOFull.getPassword().isBlank()){
                        userDTOFull.setPassword(generatePassword.generatePassword(30, true, true));
                    }
                    mapper.updateFull(userDTOFull, update);
                    userRepository.save(update);
                    return ResponseEntity.ok(userDTOFull);})
                .orElseThrow(() -> new EntityNotFoundException("Данные невозможно обновить"));
    }

    public ResponseEntity<UserDTOFull> partialUpdateUser(String nameUser, JsonPatch patch){
       UserDTOFull user = userRepository.getUserByName(nameUser)
               .map(mapper::getFromUserFull)
               .orElseThrow();
       UserDTOFull userDTO;
        try {
            userDTO = applyPatchUser(patch, user);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return updateFullUser(nameUser, userDTO);
    }

    public ResponseEntity deleteUser(String name){
        return userRepository.getUserByName(name)
                .map(userForDelete -> {userRepository.delete(userForDelete);
                    return new ResponseEntity("Пользователь удален", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не может быть удален"));
    }

    public Optional<User> getUserByName(String name){
        return userRepository.getUserByName(name);
    }

    private UserDTOFull applyPatchUser(JsonPatch patch, UserDTOFull targetUser) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        UserDTOFull user = objectMapper.treeToValue(patched, UserDTOFull.class);
        return user;
    }
}
