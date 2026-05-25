package com.nutricheck.backend.repository;

import com.nutricheck.backend.model.HasilAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface HasilAnalisisRepository extends JpaRepository<HasilAnalisis, UUID> {
    List<HasilAnalisis> findByPenggunaIdPenggunaOrderByWaktuPemindaianDesc(UUID idPengguna);
}
