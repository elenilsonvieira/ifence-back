<#
  Inicia Postgres via Docker Compose (se necessário), aguarda a porta 5432 responder
  e então sobe o backend com o profile 'pg'.
#>
param(
  [switch]$NoDocker,
  [int]$WaitSeconds = 60
)

$ErrorActionPreference = 'Stop'

Push-Location $PSScriptRoot
try {
  if (-not $NoDocker) {
    if (Get-Command docker -ErrorAction SilentlyContinue) {
      Write-Host 'Subindo Postgres (docker compose up -d)...'
      docker compose up -d | Out-Null
    } else {
      Write-Warning 'Docker não encontrado. Iniciando apenas o backend (espere que um Postgres local esteja ativo em localhost:5432).'
    }
  }

  # Aguarda a porta 5432 ficar disponível
  $elapsed = 0
  while ($elapsed -lt $WaitSeconds) {
    $ok = $false
    try {
      $tcp = New-Object System.Net.Sockets.TcpClient
      $iar = $tcp.BeginConnect('127.0.0.1', 5432, $null, $null)
      $ok = $iar.AsyncWaitHandle.WaitOne(1000)
      $tcp.Close()
    } catch {}
    if ($ok) { break }
    Start-Sleep -Seconds 1
    $elapsed++
  }
  if ($elapsed -ge $WaitSeconds) {
    Write-Warning "Postgres não respondeu na porta 5432 após $WaitSeconds s. Tentando iniciar o backend assim mesmo..."
  } else {
    Write-Host 'Postgres disponível. Iniciando backend...'
  }

  $env:SPRING_PROFILES_ACTIVE = 'pg'
  if (-not $env:JAVA_HOME) {
    # Ajuste conforme sua instalação
    $env:JAVA_HOME = 'C:/Program Files/Eclipse Adoptium/jdk-17.0.16.8-hotspot'
    $env:Path = "$env:JAVA_HOME/bin;" + $env:Path
  }

  ./mvnw.cmd -DskipTests spring-boot:run
}
finally {
  Pop-Location
}
