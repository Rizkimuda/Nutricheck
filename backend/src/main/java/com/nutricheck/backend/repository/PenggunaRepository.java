package com.nutricheck.backend.repository;

import com.nutricheck.backend.model.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, UUID> {
    Optional<Pengguna> findByEmail(String email);
}
