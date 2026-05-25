package com.nutricheck.backend.service;

import com.nutricheck.backend.model.*;
import com.nutricheck.backend.repository.HasilAnalisisRepository;
import com.nutricheck.backend.repository.PeringatanPersonalisasiRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnalisisService {
    private final PeringatanPersonalisasiRepository peringatanPersonalisasiRepository;
    private final HasilAnalisisRepository hasilAnalisisRepository;

    public AnalisisService(
        PeringatanPersonalisasiRepository peringatanPersonalisasiRepository,
        HasilAnalisisRepository hasilAnalisisRepository
    ) {
        this.peringatanPersonalisasiRepository = peringatanPersonalisasiRepository;
        this.hasilAnalisisRepository = hasilAnalisisRepository;
    }

    public AnalisisResultDto lakukanAnalisis(Pengguna pengguna, Produk produk) {
        List<ZatAditif> komposisi = produk.getKomposisiZatAditif();
        List<KondisiKesehatan> kondisi = pengguna.getKondisiKesehatan();
        List<String> peringatanMedis = new ArrayList<>();
        int skor = 100;

        if (komposisi != null && !komposisi.isEmpty()) {
            for (ZatAditif zat : komposisi) {
                if ("BERBAHAYA".equalsIgnoreCase(zat.getTingkatRisiko())) {
                    skor -= 15;
                } else if ("WASPADA".equalsIgnoreCase(zat.getTingkatRisiko())) {
                    skor -= 5;
                }
            }

            if (kondisi != null && !kondisi.isEmpty()) {
                List<PeringatanPersonalisasi> cocok = peringatanPersonalisasiRepository
                    .findByZatAditifInAndKondisiKesehatanIn(komposisi, kondisi);
                
                for (PeringatanPersonalisasi p : cocok) {
                    skor -= 25;
                    peringatanMedis.add(p.getZatAditif().getNamaUmum() + " berisiko bagi penderita " 
                        + p.getKondisiKesehatan().getNamaKondisi() + ": " + p.getEfekNegatif());
                }
            }
        }

        if (skor < 10) {
            skor = 10;
        }

        String statusRisiko;
        String rekomendasi;

        if (skor >= 75) {
            statusRisiko = "Aman";
            rekomendasi = "Produk ini aman untuk dikonsumsi sesuai batas wajar.";
        } else if (skor >= 45) {
            statusRisiko = "Waspada";
            rekomendasi = "Konsumsi produk ini dengan hati-hati. Mengandung zat aditif tingkat sedang.";
        } else {
            statusRisiko = "Berisiko";
            if (!peringatanMedis.isEmpty()) {
                rekomendasi = "Sangat tidak disarankan! Produk mengandung zat aditif yang berbahaya bagi kondisi kesehatan Anda.";
            } else {
                rekomendasi = "Sangat tidak disarankan! Produk mengandung banyak zat aditif berisiko tinggi.";
            }
        }

        HasilAnalisis hasil = HasilAnalisis.builder()
            .pengguna(pengguna)
            .produk(produk)
            .createdAt(LocalDateTime.now())
            .waktuPemindaian(LocalDateTime.now())
            .skorKesehatan(skor)
            .rekomendasi(rekomendasi)
            .build();

        hasil = hasilAnalisisRepository.save(hasil);

        return AnalisisResultDto.builder()
            .idAnalisis(hasil.getIdAnalisis())
            .produk(produk)
            .skorKesehatan(skor)
            .statusRisiko(statusRisiko)
            .rekomendasi(rekomendasi)
            .waktuPemindaian(hasil.getWaktuPemindaian())
            .peringatanMedis(peringatanMedis)
            .build();
    }
}
