package com.nutricheck.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "kondisi_kesehatan")
public class KondisiKesehatan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_kondisi")
    private UUID idKondisi;

    @Column(name = "nama_kondisi", nullable = false)
    private String namaKondisi;

    @Column(name = "jenis", nullable = false)
    private String jenis;

    public KondisiKesehatan() {}

    public KondisiKesehatan(UUID idKondisi, String namaKondisi, String jenis) {
        this.idKondisi = idKondisi;
        this.namaKondisi = namaKondisi;
        this.jenis = jenis;
    }

    public UUID getIdKondisi() {
        return idKondisi;
    }

    public void setIdKondisi(UUID idKondisi) {
        this.idKondisi = idKondisi;
    }

    public String getNamaKondisi() {
        return namaKondisi;
    }

    public void setNamaKondisi(String namaKondisi) {
        this.namaKondisi = namaKondisi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public static class Builder {
        private UUID idKondisi;
        private String namaKondisi;
        private String jenis;

        public Builder idKondisi(UUID idKondisi) {
            this.idKondisi = idKondisi;
            return this;
        }

        public Builder namaKondisi(String namaKondisi) {
            this.namaKondisi = namaKondisi;
            return this;
        }

        public Builder jenis(String jenis) {
            this.jenis = jenis;
            return this;
        }

        public KondisiKesehatan build() {
            return new KondisiKesehatan(idKondisi, namaKondisi, jenis);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
