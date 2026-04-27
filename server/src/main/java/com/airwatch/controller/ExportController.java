package com.airwatch.controller;

import com.airwatch.model.Masuratori;
import com.airwatch.repository.MasuratoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "*")
public class ExportController {

    @Autowired
    private MasuratoriRepository masuratoriRepository;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * GET /api/export/masuratori/csv
     * LOC #1 - Toate datele istorice fara limita (beneficiul maxim)
     */
    @GetMapping("/masuratori/csv")
    public ResponseEntity<byte[]> exportToate() {
        List<Masuratori> lista = masuratoriRepository.findAll();
        return buildCsvResponse(lista, "airwatch_COMPLET_toate_datele.csv");
    }

    /**
     * GET /api/export/masuratori/7zile/csv
     * LOC #2 - Ultimele 7 zile
     */
    @GetMapping("/masuratori/7zile/csv")
    public ResponseEntity<byte[]> export7Zile() {
        LocalDateTime deLa = LocalDateTime.now().minusDays(7);
        List<Masuratori> lista = masuratoriRepository.findAll().stream()
                .filter(m -> m.getTimestamp() != null && m.getTimestamp().isAfter(deLa))
                .collect(Collectors.toList());
        return buildCsvResponse(lista, "airwatch_ultimele_7_zile.csv");
    }

    /**
     * GET /api/export/masuratori/24ore/csv
     * LOC #3 - Ultimele 24 ore
     */
    @GetMapping("/masuratori/24ore/csv")
    public ResponseEntity<byte[]> export24Ore() {
        LocalDateTime deLa = LocalDateTime.now().minusHours(24);
        List<Masuratori> lista = masuratoriRepository.findAll().stream()
                .filter(m -> m.getTimestamp() != null && m.getTimestamp().isAfter(deLa))
                .collect(Collectors.toList());
        return buildCsvResponse(lista, "airwatch_ultimele_24_ore.csv");
    }

    private ResponseEntity<byte[]> buildCsvResponse(List<Masuratori> lista, String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("id_masurare,id_senzor,timestamp,pm25,pm10,no2,o3,so2,co,aqi\n");

        for (Masuratori m : lista) {
            sb.append(m.getIdMasurare()).append(",");
            sb.append(m.getSensor() != null ? m.getSensor().getId() : "N/A").append(",");
            sb.append(m.getTimestamp() != null ? m.getTimestamp().format(FMT) : "").append(",");
            sb.append(nullSafe(m.getPm25())).append(",");
            sb.append(nullSafe(m.getPm10())).append(",");
            sb.append(nullSafe(m.getNo2())).append(",");
            sb.append(nullSafe(m.getO3())).append(",");
            sb.append(nullSafe(m.getSo2())).append(",");
            sb.append(nullSafe(m.getCo())).append(",");
            sb.append(nullSafe(m.getAqi())).append("\n");
        }

        byte[] bytes = sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }

    private String nullSafe(Object val) {
        return val != null ? val.toString() : "";
    }
}
