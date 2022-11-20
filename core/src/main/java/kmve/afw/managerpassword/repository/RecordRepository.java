package kmve.afw.managerpassword.repository;

import kmve.afw.managerpassword.model.Record;
import kmve.afw.managerpassword.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> getRecordByName(String name);
    Optional<Record> getRecordByNameAndUser(String name, User user);
    List<Record> getRecordByUser(User user);
    @Transactional
    void deleteByUser(User user);
}
