package com.nutricheck.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "zat_aditif")
public class ZatAditif {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_zat")
    private UUID idZat;

    @Column(name = "nama_ilmiah")
    private String namaIlmiah;

    @Column(name = "nama_umum", nullable = false)
    private String namaUmum;

    private String fungsi;

    @Column(name = "tingkat_risiko")
    private String tingkatRisiko;

    @Column(name = "ambang_batas_harian")
    private String ambangBatasHarian;

    public ZatAditif() {}

    public ZatAditif(UUID idZat, String namaIlmiah, String namaUmum, String fungsi, String tingkatRisiko, String ambangBatasHarian) {
        this.idZat = idZat;
        this.namaIlmiah = namaIlmiah;
        this.namaUmum = namaUmum;
        this.fungsi = fungsi;
        this.tingkatRisiko = tingkatRisiko;
        this.ambangBatasHarian = ambangBatasHarian;
    }

    public UUID getIdZat() {
        return idZat;
    }

    public void setIdZat(UUID idZat) {
        this.idZat = idZat;
    }

    public String getNamaIlmiah() {
        return namaIlmiah;
    }

    public void setNamaIlmiah(String namaIlmiah) {
        this.namaIlmiah = namaIlmiah;
    }

    public String getNamaUmum() {
        return namaUmum;
    }

    public void setNamaUmum(String namaUmum) {
        this.namaUmum = namaUmum;
    }

    public String getFungsi() {
        return fungsi;
    }

    public void setFungsi(String fungsi) {
        this.fungsi = fungsi;
    }

    public String getTingkatRisiko() {
        return tingkatRisiko;
    }

    public void setTingkatRisiko(String tingkatRisiko) {
        this.tingkatRisiko = tingkatRisiko;
    }

    public String getAmbangBatasHarian() {
        return ambangBatasHarian;
    }

    public void setAmbangBatasHarian(String ambangBatasHarian) {
        this.ambangBatasHarian = ambangBatasHarian;
    }

    public static class Builder {
        private UUID idZat;
        private String namaIlmiah;
        private String namaUmum;
        private String fungsi;
        private String tingkatRisiko;
        private String ambangBatasHarian;

        public Builder idZat(UUID idZat) {
            this.idZat = idZat;
            return this;
        }

        public Builder namaIlmiah(String namaIlmiah) {
            this.namaIlmiah = namaIlmiah;
            return this;
        }

        public Builder namaUmum(String namaUmum) {
            this.namaUmum = namaUmum;
            return this;
        }

        public Builder fungsi(String fungsi) {
            this.fungsi = fungsi;
            return this;
        }

        public Builder tingkatRisiko(String tingkatRisiko) {
            this.tingkatRisiko = tingkatRisiko;
            return this;
        }

        public Builder ambangBatasHarian(String ambangBatasHarian) {
            this.ambangBatasHarian = ambangBatasHarian;
            return this;
        }

        public ZatAditif build() {
            return new ZatAditif(idZat, namaIlmiah, namaUmum, fungsi, tingkatRisiko, ambangBatasHarian);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
