package com.nutricheck.backend.controller;

import com.nutricheck.backend.model.*;
import com.nutricheck.backend.repository.*;
import com.nutricheck.backend.service.AnalisisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NutriCheckController {
    private final PenggunaRepository penggunaRepository;
    private final KondisiKesehatanRepository kondisiKesehatanRepository;
    private final ProdukRepository produkRepository;
    private final HasilAnalisisRepository hasilAnalisisRepository;
    private final AnalisisService analisisService;

    public NutriCheckController(
        PenggunaRepository penggunaRepository,
        KondisiKesehatanRepository kondisiKesehatanRepository,
        ProdukRepository produkRepository,
        HasilAnalisisRepository hasilAnalisisRepository,
        AnalisisService analisisService
    ) {
        this.penggunaRepository = penggunaRepository;
        this.kondisiKesehatanRepository = kondisiKesehatanRepository;
        this.produkRepository = produkRepository;
        this.hasilAnalisisRepository = hasilAnalisisRepository;
        this.analisisService = analisisService;
    }

    @GetMapping("/pengguna")
    public ResponseEntity<Pengguna> getPengguna() {
        List<Pengguna> semua = penggunaRepository.findAll();
        if (semua.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(semua.get(0));
    }

    @PutMapping("/pengguna")
    public ResponseEntity<Pengguna> updatePengguna(@RequestBody PenggunaDto dto) {
        List<Pengguna> semua = penggunaRepository.findAll();
        if (semua.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pengguna pengguna = semua.get(0);
        pengguna.setNama(dto.getNama());
        pengguna.setEmail(dto.getEmail());
        pengguna.setUsia(dto.getUsia());
        pengguna.setJenisKelamin(dto.getJenisKelamin());
        pengguna.setBeratBadan(dto.getBeratBadan());
        pengguna.setTinggiBadan(dto.getTinggiBadan());

        List<KondisiKesehatan> kondisi = new ArrayList<>();
        if (dto.getKondisiKesehatanIds() != null) {
            kondisi = kondisiKesehatanRepository.findAllById(dto.getKondisiKesehatanIds());
        }
        pengguna.setKondisiKesehatan(kondisi);
        
        Pengguna updated = penggunaRepository.save(pengguna);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/kondisi-kesehatan")
    public ResponseEntity<List<KondisiKesehatan>> getAllKondisiKesehatan() {
        return ResponseEntity.ok(kondisiKesehatanRepository.findAll());
    }

    @GetMapping("/produk/search")
    public ResponseEntity<List<Produk>> searchProduk(@RequestParam("q") String query) {
        return ResponseEntity.ok(produkRepository.findByNamaProdukContainingIgnoreCase(query));
    }

    @GetMapping("/produk/barcode/{barcode}")
    public ResponseEntity<AnalisisResultDto> scanBarcode(
            @PathVariable("barcode") String barcode,
            @RequestParam(value = "userId", required = false) UUID userId) {
        
        Produk produk = produkRepository.findByBarcode(barcode)
            .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        Pengguna pengguna;
        if (userId != null) {
            pengguna = penggunaRepository.findById(userId)
                .orElseGet(() -> penggunaRepository.findAll().get(0));
        } else {
            pengguna = penggunaRepository.findAll().get(0);
        }

        AnalisisResultDto hasil = analisisService.lakukanAnalisis(pengguna, produk);
        return ResponseEntity.ok(hasil);
    }

    @GetMapping("/riwayat/{userId}")
    public ResponseEntity<List<HasilAnalisis>> getRiwayat(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(hasilAnalisisRepository.findByPenggunaIdPenggunaOrderByWaktuPemindaianDesc(userId));
    }
}
