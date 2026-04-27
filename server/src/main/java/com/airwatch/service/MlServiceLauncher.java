package com.airwatch.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

/**
 * Porneste automat microserviciul Python ML (FastAPI pe portul 8000)
 * cand Spring Boot isi termina initializarea, si il opreste cand serverul se inchide.
 */
@Component
public class MlServiceLauncher {

    private Process mlProcess;

    @PostConstruct
    public void startMlService() {
        try {
            // Calea absoluta catre folderul ml-service (relativa la working dir al serverului)
            String projectRoot = Paths.get("").toAbsolutePath()
                    .getParent() // urcam din /server in radacina proiectului
                    .toString();

            String mlServiceDir = projectRoot + File.separator + "ml-service";
            String pythonExe    = mlServiceDir + File.separator + "venv"
                    + File.separator + "Scripts" + File.separator + "python.exe";
            String mainPy       = mlServiceDir + File.separator + "main.py";

            File pyFile = new File(pythonExe);
            if (!pyFile.exists()) {
                // Fallback: python din PATH (Linux/Mac sau venv necreat)
                pythonExe = "python";
            }

            ProcessBuilder pb = new ProcessBuilder(pythonExe, mainPy);
            pb.directory(new File(mlServiceDir));
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD); // nu spam in consola

            mlProcess = pb.start();
            System.out.println("✅ ML Service (Python) pornit automat pe portul 8000");

        } catch (Exception e) {
            System.err.println("⚠️ Nu s-a putut porni ML Service: " + e.getMessage()
                    + " (K-Means va folosi fallback)");
        }
    }

    @PreDestroy
    public void stopMlService() {
        if (mlProcess != null && mlProcess.isAlive()) {
            mlProcess.destroyForcibly();
            System.out.println("ML Service oprit.");
        }
    }
}
