package kmve.afw.managerpassword.mapper;

import javax.annotation.processing.Generated;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.model.Record;
import kmve.afw.managerpassword.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T21:27:55+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class RecordMapperImpl implements RecordMapper {

    @Override
    public Record getFromDTO(RecordInfoDTO recordInfoDTO) {
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
    public RecordInfoDTO getFromModel(Record record) {
        if ( record == null ) {
            return null;
        }

        RecordInfoDTO recordInfoDTO = new RecordInfoDTO();

        recordInfoDTO.setName( record.getName() );
        recordInfoDTO.setPassword( record.getPassword() );
        recordInfoDTO.setUrl( record.getUrl() );

        return recordInfoDTO;
    }

    @Override
    public void getFromModelForFull(RecordDTOWithUser recordDTOWithUser, Record record) {
        if ( recordDTOWithUser == null ) {
            return;
        }

        if ( record.getUser() == null ) {
            record.setUser( new User() );
        }
        recordDTOWithUserToUser( recordDTOWithUser, record.getUser() );
        record.setName( recordDTOWithUser.getName() );
        record.setPassword( recordDTOWithUser.getPassword() );
        record.setUrl( recordDTOWithUser.getUrl() );
    }

    @Override
    public Record getFromDTOFull(RecordDTOWithUser recordDTOWithUser) {
        if ( recordDTOWithUser == null ) {
            return null;
        }

        Record record = new Record();

        record.setUser( recordDTOWithUserToUser1( recordDTOWithUser ) );
        record.setName( recordDTOWithUser.getName() );
        record.setPassword( recordDTOWithUser.getPassword() );
        record.setUrl( recordDTOWithUser.getUrl() );

        return record;
    }

    @Override
    public RecordDTOWithUser getFromModelForDTOFull(Record record) {
        if ( record == null ) {
            return null;
        }

        RecordDTOWithUser recordDTOWithUser = new RecordDTOWithUser();

        recordDTOWithUser.setUser( recordUserId( record ) );
        recordDTOWithUser.setName( record.getName() );
        recordDTOWithUser.setPassword( record.getPassword() );
        recordDTOWithUser.setUrl( record.getUrl() );

        return recordDTOWithUser;
    }

    protected void recordDTOWithUserToUser(RecordDTOWithUser recordDTOWithUser, User mappingTarget) {
        if ( recordDTOWithUser == null ) {
            return;
        }

        mappingTarget.setId( recordDTOWithUser.getUser() );
    }

    protected User recordDTOWithUserToUser1(RecordDTOWithUser recordDTOWithUser) {
        if ( recordDTOWithUser == null ) {
            return null;
        }

        User user = new User();

        user.setId( recordDTOWithUser.getUser() );

        return user;
    }

    private long recordUserId(Record record) {
        if ( record == null ) {
            return 0L;
        }
        User user = record.getUser();
        if ( user == null ) {
            return 0L;
        }
        long id = user.getId();
        return id;
    }
}
