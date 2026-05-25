package com.nutricheck.backend.seeder;

import com.nutricheck.backend.model.*;
import com.nutricheck.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final PenggunaRepository penggunaRepository;
    private final KondisiKesehatanRepository kondisiKesehatanRepository;
    private final ProdukRepository produkRepository;
    private final ZatAditifRepository zatAditifRepository;
    private final PeringatanPersonalisasiRepository peringatanPersonalisasiRepository;

    public DatabaseSeeder(
        PenggunaRepository penggunaRepository,
        KondisiKesehatanRepository kondisiKesehatanRepository,
        ProdukRepository produkRepository,
        ZatAditifRepository zatAditifRepository,
        PeringatanPersonalisasiRepository peringatanPersonalisasiRepository
    ) {
        this.penggunaRepository = penggunaRepository;
        this.kondisiKesehatanRepository = kondisiKesehatanRepository;
        this.produkRepository = produkRepository;
        this.zatAditifRepository = zatAditifRepository;
        this.peringatanPersonalisasiRepository = peringatanPersonalisasiRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (produkRepository.count() > 0) {
            return;
        }

        KondisiKesehatan diabetes = KondisiKesehatan.builder()
            .namaKondisi("Diabetes")
            .jenis("PENYAKIT")
            .build();
        KondisiKesehatan hipertensi = KondisiKesehatan.builder()
            .namaKondisi("Hipertensi")
            .jenis("PENYAKIT")
            .build();
        KondisiKesehatan alergiMsg = KondisiKesehatan.builder()
            .namaKondisi("Alergi MSG")
            .jenis("ALERGI")
            .build();
        KondisiKesehatan alergiPewarna = KondisiKesehatan.builder()
            .namaKondisi("Alergi Pewarna Sintetis")
            .jenis("ALERGI")
            .build();

        diabetes = kondisiKesehatanRepository.save(diabetes);
        hipertensi = kondisiKesehatanRepository.save(hipertensi);
        alergiMsg = kondisiKesehatanRepository.save(alergiMsg);
        alergiPewarna = kondisiKesehatanRepository.save(alergiPewarna);

        ZatAditif msg = ZatAditif.builder()
            .namaIlmiah("Monosodium Glutamate")
            .namaUmum("MSG (Mononatrium Glutamat)")
            .fungsi("Penguat Rasa")
            .tingkatRisiko("WASPADA")
            .ambangBatasHarian("120 mg/kg BB")
            .build();
        ZatAditif tartrazin = ZatAditif.builder()
            .namaIlmiah("Tartrazine CI 19140")
            .namaUmum("Pewarna Tartrazin")
            .fungsi("Pewarna Makanan")
            .tingkatRisiko("BERBAHAYA")
            .ambangBatasHarian("7.5 mg/kg BB")
            .build();
        ZatAditif natriumBenzoat = ZatAditif.builder()
            .namaIlmiah("Sodium Benzoate")
            .namaUmum("Natrium Benzoat")
            .fungsi("Pengawet")
            .tingkatRisiko("WASPADA")
            .ambangBatasHarian("5 mg/kg BB")
            .build();

        msg = zatAditifRepository.save(msg);
        tartrazin = zatAditifRepository.save(tartrazin);
        natriumBenzoat = zatAditifRepository.save(natriumBenzoat);

        PeringatanPersonalisasi p1 = PeringatanPersonalisasi.builder()
            .zatAditif(msg)
            .kondisiKesehatan(alergiMsg)
            .efekNegatif("Menyebabkan sakit kepala, mual, dan sesak napas bagi penderita alergi MSG.")
            .build();
        PeringatanPersonalisasi p2 = PeringatanPersonalisasi.builder()
            .zatAditif(tartrazin)
            .kondisiKesehatan(alergiPewarna)
            .efekNegatif("Dapat memicu reaksi asma dan hiperaktivitas pada anak sensitif.")
            .build();
        PeringatanPersonalisasi p3 = PeringatanPersonalisasi.builder()
            .zatAditif(natriumBenzoat)
            .kondisiKesehatan(hipertensi)
            .efekNegatif("Kandungan natrium dapat meningkatkan retensi air dan memperburuk tekanan darah.")
            .build();

        peringatanPersonalisasiRepository.save(p1);
        peringatanPersonalisasiRepository.save(p2);
        peringatanPersonalisasiRepository.save(p3);

        List<ZatAditif> zatBumbu = new ArrayList<>();
        zatBumbu.add(msg);
        zatBumbu.add(natriumBenzoat);

        Produk bumbuRacik = Produk.builder()
            .idProduk(UUID.fromString("ea3a0720-68e6-4313-a7e9-3844da694f1e"))
            .barcode("089686386219")
            .namaProduk("BUMBU RACIK TUMIS")
            .kategori("Bumbu Dapur")
            .harga(3500.0)
            .satuan("PCS")
            .gambarProduk("https://images.unsplash.com/photo-1596040033229-a9821ebd058d?w=300")
            .komposisiZatAditif(zatBumbu)
            .build();

        List<ZatAditif> zatMie = new ArrayList<>();
        zatMie.add(msg);
        zatMie.add(tartrazin);

        Produk sarimi = Produk.builder()
            .idProduk(UUID.fromString("112a323c-f226-4f81-a2fa-8dd2d099b92b"))
            .barcode("089686017755")
            .namaProduk("SARIMI ISI2 KOYA JERUK")
            .kategori("Makanan Instan")
            .harga(3500.0)
            .satuan("PCS")
            .gambarProduk("https://images.unsplash.com/photo-1612966608967-302a874b0229?w=300")
            .komposisiZatAditif(zatMie)
            .build();

        List<ZatAditif> zatIndomie = new ArrayList<>();
        zatIndomie.add(msg);
        zatIndomie.add(tartrazin);

        Produk indomie = Produk.builder()
            .idProduk(UUID.fromString("6f8d070b-9dfd-4ba4-9721-c5bfae18374d"))
            .barcode("089686010824")
            .namaProduk("INDOMIE MI INSTAN GORENG")
            .kategori("Makanan Instan")
            .harga(3100.0)
            .satuan("PCS")
            .gambarProduk("https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300")
            .komposisiZatAditif(zatIndomie)
            .build();

        produkRepository.save(bumbuRacik);
        produkRepository.save(sarimi);
        produkRepository.save(indomie);

        List<KondisiKesehatan> kondisiBudi = new ArrayList<>();
        kondisiBudi.add(alergiMsg);
        kondisiBudi.add(hipertensi);

        Pengguna budi = Pengguna.builder()
            .nama("Budi Santoso")
            .email("budi@mail.com")
            .usia(28)
            .jenisKelamin("Laki-laki")
            .beratBadan(70.0)
            .tinggiBadan(172.0)
            .kondisiKesehatan(kondisiBudi)
            .build();

        penggunaRepository.save(budi);
    }
}
