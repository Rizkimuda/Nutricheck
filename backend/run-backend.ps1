$javaCheck = try { java -version 2>&1 } catch { $null }
if (-not $javaCheck) {
    Write-Host "Error: Java (JDK) tidak terdeteksi pada sistem Anda. Pastikan JDK 17+ telah terinstal dan terdaftar di PATH." -ForegroundColor Red
    Exit
}

$mavenVersion = "3.9.6"
$mavenDir = "apache-maven-$mavenVersion"
$zipFile = "maven.zip"
$zipUrl = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"

if (-not (Test-Path $mavenDir)) {
    Write-Host "Maven tidak terdeteksi lokal. Mengunduh Maven $mavenVersion..." -ForegroundColor Yellow
    Invoke-WebRequest -Uri $zipUrl -OutFile $zipFile
    Write-Host "Mengekstrak Maven..." -ForegroundColor Yellow
    Expand-Archive -Path $zipFile -DestinationPath "."
    Remove-Item $zipFile
}

Write-Host "Menjalankan Spring Boot backend..." -ForegroundColor Green
& ".\$mavenDir\bin\mvn.cmd" spring-boot:run
