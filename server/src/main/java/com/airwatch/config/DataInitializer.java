package com.airwatch.config;

import com.airwatch.model.Beneficiu;
import com.airwatch.model.Masuratori;
import com.airwatch.model.Sensor;
import com.airwatch.repository.BeneficiuRepository;
import com.airwatch.repository.MasuratoriRepository;
import com.airwatch.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * La fiecare pornire a serverului:
 * 1. Asigura existenta beneficiilor Top 3 in DB (idempotent)
 * 2. Populeaza date istorice de masuratori pentru senzorii din DB
 *    (ultimele 30 zile × cate un senzor per zona, la intervale orare)
 *    => date concrete pentru graficul Prophet si pentru leaderboard
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private BeneficiuRepository beneficiuRepo;

    @Autowired
    private SensorRepository sensorRepo;

    @Autowired
    private MasuratoriRepository masuratoriRepo;

    // Valorile de baza AQI per zona - calibrate pe realitatea Bucurestiului (date ANPM 2024)
    // Format: { aqi_baza, pm25_baza, pm10_baza, no2_baza, o3_baza, co_baza, so2_baza }
    private static final Map<Integer, double[]> ZONE_AQI_BASE = new LinkedHashMap<>();
    static {
        // id_zona -> { aqi, pm25, pm10, no2, o3, co, so2 }
        ZONE_AQI_BASE.put(2, new double[]{ 80,  18.5, 32.0, 42.0, 55.0, 1.2, 8.0 });  // Centru - trafic ridicat
        ZONE_AQI_BASE.put(3, new double[]{ 55,  10.2, 18.0, 28.0, 70.0, 0.8, 5.0 });  // Nord - relativ curat (parc Herastrau)
        ZONE_AQI_BASE.put(4, new double[]{ 95,  22.0, 40.0, 52.0, 45.0, 1.5, 12.0}); // Sud - industrial/trafic intens
        ZONE_AQI_BASE.put(5, new double[]{ 75,  16.0, 28.0, 38.0, 58.0, 1.1, 9.0 });  // Est - trafic mediu
        ZONE_AQI_BASE.put(6, new double[]{ 85,  20.0, 36.0, 48.0, 50.0, 1.3, 10.0}); // Vest - DN1 trafic
        ZONE_AQI_BASE.put(7, new double[]{ 90,  21.5, 38.0, 50.0, 48.0, 1.4, 11.0}); // SE - industrial
    }

    @Override
    public void run(ApplicationArguments args) {
        seedBeneficii();
        seedMasuratoriIstorice();
    }

    // ----- BENEFICII -----
    private void seedBeneficii() {
        if (beneficiuRepo.findByPozitieTop(1).isEmpty()) {
            Beneficiu b1 = new Beneficiu();
            b1.setPozitieTop(1);
            b1.setDenumire("Acces Date Complet");
            b1.setDescriere("Descarca intregul istoric de masuratori din toate cartierele Bucurestiului");
            b1.setEndpointDescarcare("http://localhost:8080/api/export/masuratori/csv");
            b1.setInsignaText("\uD83C\uDFC6 Vocea Cartierului");
            b1.setLabelButon("Descarcă CSV Complet (toate datele)");
            b1.setProfilVerificat(true);
            beneficiuRepo.save(b1);
        }
        if (beneficiuRepo.findByPozitieTop(2).isEmpty()) {
            Beneficiu b2 = new Beneficiu();
            b2.setPozitieTop(2);
            b2.setDenumire("Acces Date 7 Zile");
            b2.setDescriere("Descarca masuratorile din ultimele 7 zile pentru cartierul tau");
            b2.setEndpointDescarcare("http://localhost:8080/api/export/masuratori/7zile/csv");
            b2.setInsignaText("\uD83E\uDD48 Investigator");
            b2.setLabelButon("Descarcă CSV Ultimele 7 Zile");
            b2.setProfilVerificat(true);
            beneficiuRepo.save(b2);
        }
        if (beneficiuRepo.findByPozitieTop(3).isEmpty()) {
            Beneficiu b3 = new Beneficiu();
            b3.setPozitieTop(3);
            b3.setDenumire("Acces Date 24 Ore");
            b3.setDescriere("Descarca masuratorile din ultimele 24 de ore pentru cartierul tau");
            b3.setEndpointDescarcare("http://localhost:8080/api/export/masuratori/24ore/csv");
            b3.setInsignaText("\uD83E\uDD49 Vigilent");
            b3.setLabelButon("Descarcă CSV Ultimele 24 Ore");
            b3.setProfilVerificat(true);
            beneficiuRepo.save(b3);
        }
        System.out.println("✅ Beneficii Top 3 initializate in DB");
    }

    // ----- DATE ISTORICE MASURATORI -----
    private void seedMasuratoriIstorice() {
        // Verifica daca avem deja suficiente masuratori (> 100 => nu mai seed-uim)
        long existing = masuratoriRepo.count();
        if (existing > 100) {
            System.out.println("✅ Masuratori existente: " + existing + " — skip seed");
            return;
        }

        List<Sensor> totiSenzorii = sensorRepo.findAll();
        if (totiSenzorii.isEmpty()) {
            System.out.println("⚠️ Nu exista senzori in DB — skip seed masuratori");
            return;
        }

        // Grupam senzorii pe zone — folosim un senzor reprezentativ per zona
        Map<Integer, Sensor> senzorPerZona = new LinkedHashMap<>();
        for (Sensor s : totiSenzorii) {
            if (s.getUrbanArea() != null) {
                senzorPerZona.putIfAbsent(s.getUrbanArea().getId(), s);
            }
        }

        Random rnd = new Random(42); // seed fix pentru reproductibilitate
        int totalSalvate = 0;
        LocalDateTime acum = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        // Iteram zonele si generam 30 zile × 24 ore de masuratori per senzor reprezentativ
        for (Map.Entry<Integer, Sensor> entry : senzorPerZona.entrySet()) {
            Integer idZona = entry.getKey();
            Sensor senzor = entry.getValue();
            double[] base = ZONE_AQI_BASE.getOrDefault(idZona,
                    new double[]{ 70, 15.0, 25.0, 35.0, 55.0, 1.0, 7.0 });

            for (int zi = 29; zi >= 0; zi--) {   // 30 zile in urma
                for (int ora = 0; ora < 24; ora++) { // fiecare ora
                    LocalDateTime ts = acum.minusDays(zi).withHour(ora);

                    // Variatie realista: trafic mai mare dimineata (7-9) si seara (17-20)
                    double factorOrar = 1.0;
                    if (ora >= 7 && ora <= 9)   factorOrar = 1.35; // rush morning
                    if (ora >= 17 && ora <= 20) factorOrar = 1.25; // rush evening
                    if (ora >= 0  && ora <= 5)  factorOrar = 0.65; // noapte - mai curat

                    // Variatie sezoniera: primavara -> valori relativ bune
                    // Variatie aleatoare Gaussian (±15%)
                    double noise = 1.0 + rnd.nextGaussian() * 0.12;

                    double pm25 = Math.max(2.0,  base[1] * factorOrar * noise);
                    double pm10 = Math.max(5.0,  base[2] * factorOrar * noise * 1.1);
                    double no2  = Math.max(5.0,  base[3] * factorOrar * noise);
                    double o3   = Math.max(10.0, base[4] * (2.0 - factorOrar) * noise); // O3 invers fata de trafic
                    double co   = Math.max(0.2,  base[5] * factorOrar * noise);
                    double so2  = Math.max(1.0,  base[6] * factorOrar * noise);

                    // AQI calculat din PM2.5 (cel mai relevant indicator)
                    int aqi = (int) Math.round(base[0] * factorOrar * noise);
                    aqi = Math.max(20, Math.min(250, aqi));

                    Masuratori m = new Masuratori();
                    m.setSensor(senzor);
                    m.setTimestamp(ts);
                    m.setPm25(round2(pm25));
                    m.setPm10(round2(pm10));
                    m.setNo2(round2(no2));
                    m.setO3(round2(o3));
                    m.setCo(round2(co));
                    m.setSo2(round2(so2));
                    m.setAqi(aqi);

                    masuratoriRepo.save(m);
                    totalSalvate++;
                }
            }
        }

        System.out.println("✅ Masuratori istorice seed: " + totalSalvate + " inregistrari pentru " + senzorPerZona.size() + " zone");
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
