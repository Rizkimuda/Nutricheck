package com.nutricheck.backend.repository;

import com.nutricheck.backend.model.ZatAditif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface ZatAditifRepository extends JpaRepository<ZatAditif, UUID> {
    Optional<ZatAditif> findByNamaUmumIgnoreCase(String namaUmum);
}
