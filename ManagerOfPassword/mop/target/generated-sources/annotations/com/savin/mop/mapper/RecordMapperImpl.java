package com.savin.mop.mapper;

import com.savin.mop.dto.record.RecordDTOWithReferenceWithUser;
import com.savin.mop.dto.record.RecordInfoDTO;
import com.savin.mop.dto.record.RecordNameDTO;
import com.savin.mop.model.Record;
import com.savin.mop.model.User;
import com.savin.mop.model.enums.Roles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-18T16:48:11+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class RecordMapperImpl implements RecordMapper {

    @Override
    public RecordNameDTO getFromRecordNameDTO(Record record) {
        if ( record == null ) {
            return null;
        }

        String name = null;

        name = record.getName();

        RecordNameDTO recordNameDTO = new RecordNameDTO( name );

        return recordNameDTO;
    }

    @Override
    public Record getFromRecordInfoDTO(RecordInfoDTO recordInfoDTO) {
        if ( recordInfoDTO == null ) {
            return null;
        }

        Record record = new Record();

        record.setName( recordInfoDTO.getName() );
        record.setPassword( recordInfoDTO.getPassword() );
        record.setUrl( recordInfoDTO.getUrl() );

        return record;
    }

    @Override
    public RecordInfoDTO getFromRecord(Record record) {
        if ( record == null ) {
            return null;
        }

        String name = null;
        String password = null;
        String url = null;

        name = record.getName();
        password = record.getPassword();
        url = record.getUrl();

        RecordInfoDTO recordInfoDTO = new RecordInfoDTO( name, password, url );

        return recordInfoDTO;
    }

    @Override
    public Record getFromRecordWithUserDTO(RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser) {
        if ( recordDTOWithReferenceWithUser == null ) {
            return null;
        }

        Record record = new Record();

        record.setUser( userToUser( recordDTOWithReferenceWithUser.getUser() ) );
        record.setName( recordDTOWithReferenceWithUser.getName() );
        record.setPassword( recordDTOWithReferenceWithUser.getPassword() );
        record.setUrl( recordDTOWithReferenceWithUser.getUrl() );

        return record;
    }

    @Override
    public RecordDTOWithReferenceWithUser getFromRecordWithUser(Record record) {
        if ( record == null ) {
            return null;
        }

        String name = null;
        String password = null;
        String url = null;
        User user = null;

        name = record.getName();
        password = record.getPassword();
        url = record.getUrl();
        user = record.getUser();

        RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser = new RecordDTOWithReferenceWithUser( name, password, url, user );

        return recordDTOWithReferenceWithUser;
    }

    @Override
    public void updateFull(RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser, Record record) {
        if ( recordDTOWithReferenceWithUser == null ) {
            return;
        }

        record.setName( recordDTOWithReferenceWithUser.getName() );
        record.setPassword( recordDTOWithReferenceWithUser.getPassword() );
        record.setUrl( recordDTOWithReferenceWithUser.getUrl() );
        record.setUser( recordDTOWithReferenceWithUser.getUser() );
    }

    protected User userToUser(User user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setId( user.getId() );
        user1.setName( user.getName() );
        user1.setPassword( user.getPassword() );
        user1.setDepartment( user.getDepartment() );
        Set<Record> set = user.getRecords();
        if ( set != null ) {
            user1.setRecords( new LinkedHashSet<Record>( set ) );
        }
        Set<Roles> set1 = user.getRoles();
        if ( set1 != null ) {
            user1.setRoles( new LinkedHashSet<Roles>( set1 ) );
        }
        Collection<? extends GrantedAuthority> collection = user.getAuthorities();
        if ( collection != null ) {
            user1.setAuthorities( new ArrayList<GrantedAuthority>( collection ) );
        }

        return user1;
    }
}
