package com.nutricheck.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hasil_analisis")
public class HasilAnalisis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_analisis")
    private UUID idAnalisis;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_produk", nullable = false)
    private Produk produk;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "waktu_pemindaian", nullable = false)
    private LocalDateTime waktuPemindaian;

    @Column(name = "skor_kesehatan")
    private int skorKesehatan;

    private String rekomendasi;

    public HasilAnalisis() {}

    public HasilAnalisis(UUID idAnalisis, Pengguna pengguna, Produk produk, LocalDateTime createdAt, LocalDateTime waktuPemindaian, int skorKesehatan, String rekomendasi) {
        this.idAnalisis = idAnalisis;
        this.pengguna = pengguna;
        this.produk = produk;
        this.createdAt = createdAt;
        this.waktuPemindaian = waktuPemindaian;
        this.skorKesehatan = skorKesehatan;
        this.rekomendasi = rekomendasi;
    }

    public UUID getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(UUID idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public void setPengguna(Pengguna pengguna) {
        this.pengguna = pengguna;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getWaktuPemindaian() {
        return waktuPemindaian;
    }

    public void setWaktuPemindaian(LocalDateTime waktuPemindaian) {
        this.waktuPemindaian = waktuPemindaian;
    }

    public int getSkorKesehatan() {
        return skorKesehatan;
    }

    public void setSkorKesehatan(int skorKesehatan) {
        this.skorKesehatan = skorKesehatan;
    }

    public String getRekomendasi() {
        return rekomendasi;
    }

    public void setRekomendasi(String rekomendasi) {
        this.rekomendasi = rekomendasi;
    }

    public static class Builder {
        private UUID idAnalisis;
        private Pengguna pengguna;
        private Produk produk;
        private LocalDateTime createdAt;
        private LocalDateTime waktuPemindaian;
        private int skorKesehatan;
        private String rekomendasi;

        public Builder idAnalisis(UUID idAnalisis) {
            this.idAnalisis = idAnalisis;
            return this;
        }

        public Builder pengguna(Pengguna pengguna) {
            this.pengguna = pengguna;
            return this;
        }

        public Builder produk(Produk produk) {
            this.produk = produk;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder waktuPemindaian(LocalDateTime waktuPemindaian) {
            this.waktuPemindaian = waktuPemindaian;
            return this;
        }

        public Builder skorKesehatan(int skorKesehatan) {
            this.skorKesehatan = skorKesehatan;
            return this;
        }

        public Builder rekomendasi(String rekomendasi) {
            this.rekomendasi = rekomendasi;
            return this;
        }

        public HasilAnalisis build() {
            return new HasilAnalisis(idAnalisis, pengguna, produk, createdAt, waktuPemindaian, skorKesehatan, rekomendasi);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
