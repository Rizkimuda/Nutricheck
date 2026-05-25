package com.nutricheck.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AnalisisResultDto {
    private UUID idAnalisis;
    private Produk produk;
    private int skorKesehatan;
    private String statusRisiko;
    private String rekomendasi;
    private LocalDateTime waktuPemindaian;
    private List<String> peringatanMedis;

    public AnalisisResultDto() {}

    public AnalisisResultDto(UUID idAnalisis, Produk produk, int skorKesehatan, String statusRisiko, String rekomendasi, LocalDateTime waktuPemindaian, List<String> peringatanMedis) {
        this.idAnalisis = idAnalisis;
        this.produk = produk;
        this.skorKesehatan = skorKesehatan;
        this.statusRisiko = statusRisiko;
        this.rekomendasi = rekomendasi;
        this.waktuPemindaian = waktuPemindaian;
        this.peringatanMedis = peringatanMedis;
    }

    public UUID getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(UUID idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public int getSkorKesehatan() {
        return skorKesehatan;
    }

    public void setSkorKesehatan(int skorKesehatan) {
        this.skorKesehatan = skorKesehatan;
    }

    public String getStatusRisiko() {
        return statusRisiko;
    }

    public void setStatusRisiko(String statusRisiko) {
        this.statusRisiko = statusRisiko;
    }

    public String getRekomendasi() {
        return rekomendasi;
    }

    public void setRekomendasi(String rekomendasi) {
        this.rekomendasi = rekomendasi;
    }

    public LocalDateTime getWaktuPemindaian() {
        return waktuPemindaian;
    }

    public void setWaktuPemindaian(LocalDateTime waktuPemindaian) {
        this.waktuPemindaian = waktuPemindaian;
    }

    public List<String> getPeringatanMedis() {
        return peringatanMedis;
    }

    public void setPeringatanMedis(List<String> peringatanMedis) {
        this.peringatanMedis = peringatanMedis;
    }

    public static class Builder {
        private UUID idAnalisis;
        private Produk produk;
        private int skorKesehatan;
        private String statusRisiko;
        private String rekomendasi;
        private LocalDateTime waktuPemindaian;
        private List<String> peringatanMedis;

        public Builder idAnalisis(UUID idAnalisis) {
            this.idAnalisis = idAnalisis;
            return this;
        }

        public Builder produk(Produk produk) {
            this.produk = produk;
            return this;
        }

        public Builder skorKesehatan(int skorKesehatan) {
            this.skorKesehatan = skorKesehatan;
            return this;
        }

        public Builder statusRisiko(String statusRisiko) {
            this.statusRisiko = statusRisiko;
            return this;
        }

        public Builder rekomendasi(String rekomendasi) {
            this.rekomendasi = rekomendasi;
            return this;
        }

        public Builder waktuPemindaian(LocalDateTime waktuPemindaian) {
            this.waktuPemindaian = waktuPemindaian;
            return this;
        }

        public Builder peringatanMedis(List<String> peringatanMedis) {
            this.peringatanMedis = peringatanMedis;
            return this;
        }

        public AnalisisResultDto build() {
            return new AnalisisResultDto(idAnalisis, produk, skorKesehatan, statusRisiko, rekomendasi, waktuPemindaian, peringatanMedis);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
