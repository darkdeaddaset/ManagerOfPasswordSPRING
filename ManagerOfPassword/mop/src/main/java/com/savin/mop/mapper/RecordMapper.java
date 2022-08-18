package com.savin.mop.mapper;

import com.savin.mop.dto.record.RecordDTOWithReferenceWithUser;
import com.savin.mop.dto.record.RecordInfoDTO;
import com.savin.mop.dto.record.RecordNameDTO;
import com.savin.mop.model.Record;
import com.savin.mop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordNameDTO getFromRecordNameDTO(Record record);
    Record getFromRecordInfoDTO(RecordInfoDTO recordInfoDTO);
    RecordInfoDTO getFromRecord(Record record);
    @Mapping(target = "user.id", source = "user.id")
    Record getFromRecordWithUserDTO(RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser);
    RecordDTOWithReferenceWithUser getFromRecordWithUser(Record record);
    void updateFull(RecordDTOWithReferenceWithUser recordDTOWithReferenceWithUser,
                    @MappingTarget Record record);
}
