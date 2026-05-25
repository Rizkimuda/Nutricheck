package com.nutricheck.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "peringatan_personalisasi")
public class PeringatanPersonalisasi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_zat", nullable = false)
    private ZatAditif zatAditif;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_kondisi", nullable = false)
    private KondisiKesehatan kondisiKesehatan;

    @Column(name = "efek_negatif", nullable = false)
    private String efekNegatif;

    public PeringatanPersonalisasi() {}

    public PeringatanPersonalisasi(UUID id, ZatAditif zatAditif, KondisiKesehatan kondisiKesehatan, String efekNegatif) {
        this.id = id;
        this.zatAditif = zatAditif;
        this.kondisiKesehatan = kondisiKesehatan;
        this.efekNegatif = efekNegatif;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZatAditif getZatAditif() {
        return zatAditif;
    }

    public void setZatAditif(ZatAditif zatAditif) {
        this.zatAditif = zatAditif;
    }

    public KondisiKesehatan getKondisiKesehatan() {
        return kondisiKesehatan;
    }

    public void setKondisiKesehatan(KondisiKesehatan kondisiKesehatan) {
        this.kondisiKesehatan = kondisiKesehatan;
    }

    public String getEfekNegatif() {
        return efekNegatif;
    }

    public void setEfekNegatif(String efekNegatif) {
        this.efekNegatif = efekNegatif;
    }

    public static class Builder {
        private UUID id;
        private ZatAditif zatAditif;
        private KondisiKesehatan kondisiKesehatan;
        private String efekNegatif;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder zatAditif(ZatAditif zatAditif) {
            this.zatAditif = zatAditif;
            return this;
        }

        public Builder kondisiKesehatan(KondisiKesehatan kondisiKesehatan) {
            this.kondisiKesehatan = kondisiKesehatan;
            return this;
        }

        public Builder efekNegatif(String efekNegatif) {
            this.efekNegatif = efekNegatif;
            return this;
        }

        public PeringatanPersonalisasi build() {
            return new PeringatanPersonalisasi(id, zatAditif, kondisiKesehatan, efekNegatif);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
