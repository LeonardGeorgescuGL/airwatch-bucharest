package com.airwatch.model;

import jakarta.persistence.*;

/**
 * Beneficiul acordat membrilor din Top 3 al clasamentului.
 * Fiecare pozitie (1, 2, 3) are un beneficiu definit in DB.
 * Legatura cu userul se face prin pozitia curenta in leaderboard.
 */
@Entity
@Table(name = "BENEFICIU")
public class Beneficiu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiu")
    private Integer idBeneficiu;

    /** Pozitia in top pentru care se acorda acest beneficiu (1, 2 sau 3) */
    @Column(name = "pozitie_top", nullable = false, unique = true)
    private Integer pozitieTop;

    /** Denumirea beneficiului afisata in UI */
    @Column(name = "denumire", nullable = false)
    private String denumire;

    /** Descriere extinsa pentru UI */
    @Column(name = "descriere")
    private String descriere;

    /** Endpoint-ul de download pe care il va apela frontend-ul */
    @Column(name = "endpoint_descarcare")
    private String endpointDescarcare;

    /** Textul insignei speciale (ex: "🏆 Vocea Cartierului") */
    @Column(name = "insign_text")
    private String insignaText;

    /** Eticheta butonului de download din UI */
    @Column(name = "label_buton")
    private String labelButon;

    /** Daca pozitia beneficiaza de bifa "Profil Verificat" */
    @Column(name = "profil_verificat")
    private Boolean profilVerificat = false;

    public Beneficiu() {}

    public Integer getIdBeneficiu() { return idBeneficiu; }
    public void setIdBeneficiu(Integer idBeneficiu) { this.idBeneficiu = idBeneficiu; }

    public Integer getPozitieTop() { return pozitieTop; }
    public void setPozitieTop(Integer pozitieTop) { this.pozitieTop = pozitieTop; }

    public String getDenumire() { return denumire; }
    public void setDenumire(String denumire) { this.denumire = denumire; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public String getEndpointDescarcare() { return endpointDescarcare; }
    public void setEndpointDescarcare(String e) { this.endpointDescarcare = e; }

    public String getInsignaText() { return insignaText; }
    public void setInsignaText(String insignaText) { this.insignaText = insignaText; }

    public String getLabelButon() { return labelButon; }
    public void setLabelButon(String labelButon) { this.labelButon = labelButon; }

    public Boolean getProfilVerificat() { return profilVerificat; }
    public void setProfilVerificat(Boolean profilVerificat) { this.profilVerificat = profilVerificat; }
}
