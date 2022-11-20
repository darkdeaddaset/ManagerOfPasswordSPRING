package kmve.afw.managerpassword.repository;

import kmve.afw.managerpassword.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);
    Optional<User> getUserByLoginAndPassword(String login, String password);
    @Query("select id from User where login = :login")
    Long getUserId(@Param("login") String login);
}
