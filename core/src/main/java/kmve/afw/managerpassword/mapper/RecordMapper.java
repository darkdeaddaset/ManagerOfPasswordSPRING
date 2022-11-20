package kmve.afw.managerpassword.mapper;

import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.model.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    Record getFromDTO (RecordInfoDTO recordInfoDTO);
    RecordInfoDTO getFromModel(Record record);

    @Mapping(source = "user", target = "user.id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "password", target = "password")
    void getFromModelForFull(RecordDTOWithUser recordDTOWithUser, @MappingTarget Record record);

    @Mapping(source = "user", target = "user.id")
    Record getFromDTOFull(RecordDTOWithUser recordDTOWithUser);
    @Mapping(source = "user.id", target = "user")
    RecordDTOWithUser getFromModelForDTOFull(Record record);
}
