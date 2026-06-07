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
            .idZat(UUID.randomUUID())
            .namaIlmiah("Monosodium Glutamate")
            .namaUmum("MSG (Mononatrium Glutamat)")
            .fungsi("Penguat Rasa")
            .tingkatRisiko("WASPADA")
            .ambangBatasHarian("120 mg/kg BB")
            .build();
        ZatAditif tartrazin = ZatAditif.builder()
            .idZat(UUID.randomUUID())
            .namaIlmiah("Tartrazine CI 19140")
            .namaUmum("Pewarna Tartrazin")
            .fungsi("Pewarna Makanan")
            .tingkatRisiko("BERBAHAYA")
            .ambangBatasHarian("7.5 mg/kg BB")
            .build();
        ZatAditif natriumBenzoat = ZatAditif.builder()
            .idZat(UUID.randomUUID())
            .namaIlmiah("Sodium Benzoate")
            .namaUmum("Natrium Benzoat")
            .fungsi("Pengawet")
            .tingkatRisiko("WASPADA")
            .ambangBatasHarian("5 mg/kg BB")
            .build();
        ZatAditif aspartam = ZatAditif.builder()
            .idZat(UUID.randomUUID())
            .namaIlmiah("Aspartame")
            .namaUmum("Aspartam (Pemanis Buatan)")
            .fungsi("Pemanis Buatan")
            .tingkatRisiko("WASPADA")
            .ambangBatasHarian("40 mg/kg BB")
            .build();
        ZatAditif sunsetYellow = ZatAditif.builder()
            .idZat(UUID.randomUUID())
            .namaIlmiah("Sunset Yellow FCF CI 15985")
            .namaUmum("Pewarna Sunset Yellow")
            .fungsi("Pewarna Makanan")
            .tingkatRisiko("BERBAHAYA")
            .ambangBatasHarian("4 mg/kg BB")
            .build();
        ZatAditif kaliumSorbat = ZatAditif.builder()
            .idZat(UUID.randomUUID())
            .namaIlmiah("Potassium Sorbate")
            .namaUmum("Kalium Sorbat")
            .fungsi("Pengawet")
            .tingkatRisiko("WASPADA")
            .ambangBatasHarian("25 mg/kg BB")
            .build();

        msg = zatAditifRepository.save(msg);
        tartrazin = zatAditifRepository.save(tartrazin);
        natriumBenzoat = zatAditifRepository.save(natriumBenzoat);
        aspartam = zatAditifRepository.save(aspartam);
        sunsetYellow = zatAditifRepository.save(sunsetYellow);
        kaliumSorbat = zatAditifRepository.save(kaliumSorbat);

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
        PeringatanPersonalisasi p4 = PeringatanPersonalisasi.builder()
            .zatAditif(sunsetYellow)
            .kondisiKesehatan(alergiPewarna)
            .efekNegatif("Dapat memicu gatal-gatal, ruam kulit, dan asma pada penderita alergi pewarna.")
            .build();
        PeringatanPersonalisasi p5 = PeringatanPersonalisasi.builder()
            .zatAditif(aspartam)
            .kondisiKesehatan(diabetes)
            .efekNegatif("Konsumsi pemanis buatan berlebih dapat memengaruhi respons glukosa darah penderita Diabetes.")
            .build();

        peringatanPersonalisasiRepository.save(p1);
        peringatanPersonalisasiRepository.save(p2);
        peringatanPersonalisasiRepository.save(p3);
        peringatanPersonalisasiRepository.save(p4);
        peringatanPersonalisasiRepository.save(p5);

        List<ZatAditif> aditifMie = List.of(msg, tartrazin);
        List<ZatAditif> aditifSoda = List.of(natriumBenzoat, sunsetYellow);
        List<ZatAditif> aditifBumbu = List.of(msg, natriumBenzoat);
        List<ZatAditif> aditifKecap = List.of(natriumBenzoat);
        List<ZatAditif> aditifSnack = List.of(msg);
        List<ZatAditif> aditifManis = List.of(aspartam);
        List<ZatAditif> aditifSusu = List.of(kaliumSorbat);
        List<ZatAditif> aditifBersih = List.of();

        List<Produk> listProduk = List.of(
            Produk.builder()
                .idProduk(UUID.fromString("ea3a0720-68e6-4313-a7e9-3844da694f1e"))
                .barcode("089686386219")
                .namaProduk("BUMBU RACIK TUMIS")
                .kategori("Bumbu Dapur")
                .harga(3500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1596040033229-a9821ebd058d?w=300")
                .komposisiZatAditif(aditifBumbu)
                .build(),
            Produk.builder()
                .idProduk(UUID.fromString("112a323c-f226-4f81-a2fa-8dd2d099b92b"))
                .barcode("089686017755")
                .namaProduk("SARIMI ISI2 KOYA JERUK")
                .kategori("Makanan Instan")
                .harga(3500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1612966608967-302a874b0229?w=300")
                .komposisiZatAditif(aditifMie)
                .build(),
            Produk.builder()
                .idProduk(UUID.fromString("6f8d070b-9dfd-4ba4-9721-c5bfae18374d"))
                .barcode("5285000390602")
                .namaProduk("INDOMIE MI INSTAN GORENG")
                .kategori("Makanan Instan")
                .harga(3100.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300")
                .komposisiZatAditif(aditifMie)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("089686010183")
                .namaProduk("INDOMIE RASA SOTO MIE")
                .kategori("Makanan Instan")
                .harga(3100.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1552611052-33e04de081de?w=300")
                .komposisiZatAditif(aditifMie)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("089686043136")
                .namaProduk("POP MIE RASA BASO")
                .kategori("Makanan Instan")
                .harga(5000.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1585032226651-759b368d7246?w=300")
                .komposisiZatAditif(aditifBumbu)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8992696000018")
                .namaProduk("TEH BOTOL SOSRO KOTAK 250ML")
                .kategori("Minuman")
                .harga(3000.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8992735110050")
                .namaProduk("SILVERQUEEN MILK CHOCOLATE 62G")
                .kategori("Camilan")
                .harga(16500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1511381939415-e44015466834?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8992696404434")
                .namaProduk("ULTRA MILK COKELAT 250ML")
                .kategori("Minuman")
                .harga(6500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1563636619-e9143da7973b?w=300")
                .komposisiZatAditif(aditifSusu)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8996001300057")
                .namaProduk("KOPIKO CANDY PACK")
                .kategori("Camilan")
                .harga(8500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1599599810769-bcde5a160d32?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8996001353275")
                .namaProduk("BENG-BENG CHOCOLATE")
                .kategori("Camilan")
                .harga(2500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1581798459219-318e76aecc7b?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("089686003291")
                .namaProduk("CHITATO SAPI PANGGANG 68G")
                .kategori("Camilan")
                .harga(12500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1566478989037-eec170784d20?w=300")
                .komposisiZatAditif(aditifSnack)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8996001301078")
                .namaProduk("ROMA KELAPA 300G")
                .kategori("Camilan")
                .harga(11000.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1549476464-37392f717541?w=300")
                .komposisiZatAditif(aditifSusu)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8991002100806")
                .namaProduk("KOPI KAPAL API SPESIAL MIX")
                .kategori("Minuman")
                .harga(2000.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8998007534208")
                .namaProduk("COCA-COLA 390ML")
                .kategori("Minuman")
                .harga(5500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=300")
                .komposisiZatAditif(aditifSoda)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8886008101037")
                .namaProduk("AQUA AIR MINERAL 600ML")
                .kategori("Minuman")
                .harga(3500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1608889175123-8ec330b86f84?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8999999036929")
                .namaProduk("BANGO KECAP MANIS")
                .kategori("Bumbu Dapur")
                .harga(8500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1471193945509-9ad0617afabf?w=300")
                .komposisiZatAditif(aditifKecap)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8998866200216")
                .namaProduk("ABC KECAP MANIS 135ML")
                .kategori("Bumbu Dapur")
                .harga(7500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1607305387299-a3d9611cd46f?w=300")
                .komposisiZatAditif(aditifKecap)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8993166000014")
                .namaProduk("YAKULT")
                .kategori("Minuman")
                .harga(2500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1571244856353-fb0e3b18d29b?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8992696403161")
                .namaProduk("BEAR BRAND 189ML")
                .kategori("Minuman")
                .harga(10500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1550583724-b2692b85b150?w=300")
                .komposisiZatAditif(aditifBersih)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8994784001157")
                .namaProduk("LUWAK WHITE KOFFIE")
                .kategori("Minuman")
                .harga(13500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1541167760496-1628856ab772?w=300")
                .komposisiZatAditif(aditifManis)
                .build(),
            Produk.builder()
                .idProduk(UUID.randomUUID())
                .barcode("8998007534215")
                .namaProduk("FANTA ORANGE 390ML")
                .kategori("Minuman")
                .harga(5500.0)
                .satuan("PCS")
                .gambarProduk("https://images.unsplash.com/photo-1624517452488-04869289c4ca?w=300")
                .komposisiZatAditif(aditifSoda)
                .build()
        );

        produkRepository.saveAll(listProduk);

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
