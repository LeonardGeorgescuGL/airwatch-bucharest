

$ROOT = Split-Path -Parent $MyInvocation.MyCommand.Definition
$JAVA_HOME = "C:\Software\Java\jdk-21.0.2"
$MAVEN = "$ROOT\server\maven\apache-maven-3.9.6\bin\mvn.cmd"

Write-Host "AirWatch Bucuresti - Start All Services" -ForegroundColor Cyan
Write-Host ""

Write-Host "[1/3] Pornesc Backend Spring Boot pe portul 8080..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", @"
    `$env:JAVA_HOME = '$JAVA_HOME'
    `$env:PATH = '$JAVA_HOME\bin;' + `$env:PATH
    Set-Location '$ROOT\server'
    Write-Host 'Serviciu Backend Spring Boot' -ForegroundColor Green
    & '$MAVEN' spring-boot:run
"@

Start-Sleep -Seconds 2

Write-Host "[2/3] Pornesc ML Service Python pe portul 8000..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", @"
    Set-Location '$ROOT\ml-service'
    Write-Host 'Serviciu ML Python' -ForegroundColor Magenta
    if (Test-Path '.\venv\Scripts\Activate.ps1') {
        .\venv\Scripts\Activate.ps1
        uvicorn main:app --host 0.0.0.0 --port 8000 --reload
    } else {
        Write-Host 'EROARE: venv nu exista! Creeaza-l cu: python -m venv venv' -ForegroundColor Red
        Write-Host 'Apoi: .\venv\Scripts\Activate.ps1 && pip install -r requirements.txt' -ForegroundColor Red
        Pause
    }
"@

Start-Sleep -Seconds 2

Write-Host "[3/3] Pornesc Frontend React pe portul 5173..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", @"
    Set-Location '$ROOT\client'
    Write-Host 'Interfata Frontend React' -ForegroundColor Blue
    npm run dev
"@

Write-Host ""
Write-Host "Toate serviciile au fost lansate in ferestre separate." -ForegroundColor Green
Write-Host ""
Write-Host "  Backend  -> http://localhost:8080" -ForegroundColor White
Write-Host "  ML API   -> http://localhost:8000" -ForegroundColor White  
Write-Host "  Frontend -> http://localhost:5173" -ForegroundColor White
Write-Host ""
Write-Host "Asteapta initializarea conexiunilor inainte de utilizare." -ForegroundColor DarkYellow
Start-Sleep -Seconds 5
