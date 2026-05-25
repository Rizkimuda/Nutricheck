package com.nutricheck.backend.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "produk")
public class Produk {
    @Id
    @Column(name = "id_produk")
    private UUID idProduk;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(name = "nama_produk", nullable = false)
    private String namaProduk;

    private String kategori;

    private double harga;

    private String satuan;

    @Column(name = "gambar_produk")
    private String gambarProduk;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "komposisi_produk",
        joinColumns = @JoinColumn(name = "id_produk"),
        inverseJoinColumns = @JoinColumn(name = "id_zat")
    )
    private List<ZatAditif> komposisiZatAditif;

    public Produk() {}

    public Produk(UUID idProduk, String barcode, String namaProduk, String kategori, double harga, String satuan, String gambarProduk, List<ZatAditif> komposisiZatAditif) {
        this.idProduk = idProduk;
        this.barcode = barcode;
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.harga = harga;
        this.satuan = satuan;
        this.gambarProduk = gambarProduk;
        this.komposisiZatAditif = komposisiZatAditif;
    }

    public UUID getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(UUID idProduk) {
        this.idProduk = idProduk;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getGambarProduk() {
        return gambarProduk;
    }

    public void setGambarProduk(String gambarProduk) {
        this.gambarProduk = gambarProduk;
    }

    public List<ZatAditif> getKomposisiZatAditif() {
        return komposisiZatAditif;
    }

    public void setKomposisiZatAditif(List<ZatAditif> komposisiZatAditif) {
        this.komposisiZatAditif = komposisiZatAditif;
    }

    public static class Builder {
        private UUID idProduk;
        private String barcode;
        private String namaProduk;
        private String kategori;
        private double harga;
        private String satuan;
        private String gambarProduk;
        private List<ZatAditif> komposisiZatAditif;

        public Builder idProduk(UUID idProduk) {
            this.idProduk = idProduk;
            return this;
        }

        public Builder barcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder namaProduk(String namaProduk) {
            this.namaProduk = namaProduk;
            return this;
        }

        public Builder kategori(String kategori) {
            this.kategori = kategori;
            return this;
        }

        public Builder harga(double harga) {
            this.harga = harga;
            return this;
        }

        public Builder satuan(String satuan) {
            this.satuan = satuan;
            return this;
        }

        public Builder gambarProduk(String gambarProduk) {
            this.gambarProduk = gambarProduk;
            return this;
        }

        public Builder komposisiZatAditif(List<ZatAditif> komposisiZatAditif) {
            this.komposisiZatAditif = komposisiZatAditif;
            return this;
        }

        public Produk build() {
            return new Produk(idProduk, barcode, namaProduk, kategori, harga, satuan, gambarProduk, komposisiZatAditif);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
