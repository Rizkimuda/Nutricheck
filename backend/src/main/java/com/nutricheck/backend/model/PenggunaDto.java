package com.nutricheck.backend.model;

import java.util.List;
import java.util.UUID;

public class PenggunaDto {
    private String nama;
    private String email;
    private int usia;
    private String jenisKelamin;
    private double beratBadan;
    private double tinggiBadan;
    private List<UUID> kondisiKesehatanIds;

    public PenggunaDto() {}

    public PenggunaDto(String nama, String email, int usia, String jenisKelamin, double beratBadan, double tinggiBadan, List<UUID> kondisiKesehatanIds) {
        this.nama = nama;
        this.email = email;
        this.usia = usia;
        this.jenisKelamin = jenisKelamin;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.kondisiKesehatanIds = kondisiKesehatanIds;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public List<UUID> getKondisiKesehatanIds() {
        return kondisiKesehatanIds;
    }

    public void setKondisiKesehatanIds(List<UUID> kondisiKesehatanIds) {
        this.kondisiKesehatanIds = kondisiKesehatanIds;
    }

    public static class Builder {
        private String nama;
        private String email;
        private int usia;
        private String jenisKelamin;
        private double beratBadan;
        private double tinggiBadan;
        private List<UUID> kondisiKesehatanIds;

        public Builder nama(String nama) {
            this.nama = nama;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder usia(int usia) {
            this.usia = usia;
            return this;
        }

        public Builder jenisKelamin(String jenisKelamin) {
            this.jenisKelamin = jenisKelamin;
            return this;
        }

        public Builder beratBadan(double beratBadan) {
            this.beratBadan = beratBadan;
            return this;
        }

        public Builder tinggiBadan(double tinggiBadan) {
            this.tinggiBadan = tinggiBadan;
            return this;
        }

        public Builder kondisiKesehatanIds(List<UUID> kondisiKesehatanIds) {
            this.kondisiKesehatanIds = kondisiKesehatanIds;
            return this;
        }

        public PenggunaDto build() {
            return new PenggunaDto(nama, email, usia, jenisKelamin, beratBadan, tinggiBadan, kondisiKesehatanIds);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
