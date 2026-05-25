package com.nutricheck.backend.repository;

import com.nutricheck.backend.model.KondisiKesehatan;
import com.nutricheck.backend.model.PeringatanPersonalisasi;
import com.nutricheck.backend.model.ZatAditif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PeringatanPersonalisasiRepository extends JpaRepository<PeringatanPersonalisasi, UUID> {
    List<PeringatanPersonalisasi> findByZatAditifInAndKondisiKesehatanIn(
        List<ZatAditif> zatAditif,
        List<KondisiKesehatan> kondisiKesehatan
    );
}
