package ecc.mx.autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecc.mx.autenticacion.models.LUser;
@Repository
public interface UserRepository extends JpaRepository<LUser, Long> {
    Optional<LUser> findByUsername(String username);
}
