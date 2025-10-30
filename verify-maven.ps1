Write-Host "`nMaven Installation Verification Script" -ForegroundColor Cyan
Write-Host "================================`n" -ForegroundColor Cyan

# Check if MAVEN_HOME is set
$mavenHome = [System.Environment]::GetEnvironmentVariable("MAVEN_HOME", "Machine")
if ($mavenHome) {
    Write-Host "✅ MAVEN_HOME is set to: $mavenHome" -ForegroundColor Green
} else {
    Write-Host "❌ MAVEN_HOME is not set!" -ForegroundColor Red
    Write-Host "   Please set MAVEN_HOME to your Maven installation directory" -ForegroundColor Yellow
}

# Check if Maven is in PATH
$mvnCommand = Get-Command mvn -ErrorAction SilentlyContinue
if ($mvnCommand) {
    Write-Host "✅ Maven is found in PATH at: $($mvnCommand.Source)" -ForegroundColor Green
    
    # Get Maven version
    Write-Host "`nMaven Version Information:" -ForegroundColor Cyan
    Write-Host "------------------------" -ForegroundColor Cyan
    mvn -version
} else {
    Write-Host "❌ Maven (mvn) is not found in PATH!" -ForegroundColor Red
    Write-Host "   Please add %MAVEN_HOME%\bin to your PATH environment variable" -ForegroundColor Yellow
}

Write-Host "`nJava Version Information:" -ForegroundColor Cyan
Write-Host "-----------------------" -ForegroundColor Cyan
java -version

Write-Host "`nVerification Complete!" -ForegroundColor Cyan
Write-Host "`nPress any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")