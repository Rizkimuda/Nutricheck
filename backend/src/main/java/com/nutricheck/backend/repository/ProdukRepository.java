package com.nutricheck.backend.repository;

import com.nutricheck.backend.model.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, UUID> {
    Optional<Produk> findByBarcode(String barcode);
    List<Produk> findByNamaProdukContainingIgnoreCase(String namaProduk);
}
