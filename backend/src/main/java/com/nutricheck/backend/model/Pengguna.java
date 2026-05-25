package com.nutricheck.backend.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pengguna")
public class Pengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pengguna")
    private UUID idPengguna;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false, unique = true)
    private String email;

    private int usia;

    @Column(name = "jenis_kelamin")
    private String jenisKelamin;

    @Column(name = "berat_badan")
    private double beratBadan;

    @Column(name = "tinggi_badan")
    private double tinggiBadan;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "profil_kesehatan_pengguna",
        joinColumns = @JoinColumn(name = "id_pengguna"),
        inverseJoinColumns = @JoinColumn(name = "id_kondisi")
    )
    private List<KondisiKesehatan> kondisiKesehatan;

    public Pengguna() {}

    public Pengguna(UUID idPengguna, String nama, String email, int usia, String jenisKelamin, double beratBadan, double tinggiBadan, List<KondisiKesehatan> kondisiKesehatan) {
        this.idPengguna = idPengguna;
        this.nama = nama;
        this.email = email;
        this.usia = usia;
        this.jenisKelamin = jenisKelamin;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.kondisiKesehatan = kondisiKesehatan;
    }

    public UUID getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(UUID idPengguna) {
        this.idPengguna = idPengguna;
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

    public List<KondisiKesehatan> getKondisiKesehatan() {
        return kondisiKesehatan;
    }

    public void setKondisiKesehatan(List<KondisiKesehatan> kondisiKesehatan) {
        this.kondisiKesehatan = kondisiKesehatan;
    }

    public static class Builder {
        private UUID idPengguna;
        private String nama;
        private String email;
        private int usia;
        private String jenisKelamin;
        private double beratBadan;
        private double tinggiBadan;
        private List<KondisiKesehatan> kondisiKesehatan;

        public Builder idPengguna(UUID idPengguna) {
            this.idPengguna = idPengguna;
            return this;
        }

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

        public Builder kondisiKesehatan(List<KondisiKesehatan> kondisiKesehatan) {
            this.kondisiKesehatan = kondisiKesehatan;
            return this;
        }

        public Pengguna build() {
            return new Pengguna(idPengguna, nama, email, usia, jenisKelamin, beratBadan, tinggiBadan, kondisiKesehatan);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
