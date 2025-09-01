#requires -Version 5.1
Param(
  [string]$JavaHome = $env:JAVA_HOME,
  [string]$Profile = "h2",
  [switch]$SkipTests
)

$ErrorActionPreference = "Stop"

function Write-Info($msg){ Write-Host "[INFO] $msg" -ForegroundColor Cyan }
function Write-Warn($msg){ Write-Host "[WARN] $msg" -ForegroundColor Yellow }
function Write-Err($msg){ Write-Host "[ERROR] $msg" -ForegroundColor Red }

# Ir para a pasta do backend (diretório deste script)
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

# Garantir JAVA_HOME
if (-not $JavaHome -or -not (Test-Path $JavaHome)) {
  $adoptium = "C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.16.8-hotspot"
  if (Test-Path $adoptium) { $JavaHome = $adoptium }
}
if (-not $JavaHome -or -not (Test-Path $JavaHome)) {
  Write-Warn "JAVA_HOME não definido ou inválido. Use o parâmetro -JavaHome ou configure no sistema."
  exit 1
}
$env:JAVA_HOME = $JavaHome
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
$env:SPRING_PROFILES_ACTIVE = $Profile

Write-Info "JAVA_HOME: $env:JAVA_HOME"
Write-Info "Perfil: $env:SPRING_PROFILES_ACTIVE"

# Usar mvnw se existir, senão mvn
$useWrapper = Test-Path ".\mvnw.cmd"
$mvnCmd = if ($useWrapper) { ".\mvnw.cmd" } else { "mvn" }

$args = @()
if ($SkipTests.IsPresent) { $args += "-DskipTests" }
$args += "spring-boot:run"

Write-Info "Executando: $mvnCmd $($args -join ' ')"
& $mvnCmd @args

exit $LASTEXITCODE
