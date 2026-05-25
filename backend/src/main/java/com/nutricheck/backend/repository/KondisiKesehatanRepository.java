package com.nutricheck.backend.repository;

import com.nutricheck.backend.model.KondisiKesehatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface KondisiKesehatanRepository extends JpaRepository<KondisiKesehatan, UUID> {
    Optional<KondisiKesehatan> findByNamaKondisi(String namaKondisi);
}
