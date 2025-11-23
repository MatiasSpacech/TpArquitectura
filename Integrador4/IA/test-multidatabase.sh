#!/bin/bash

# Script de prueba para el módulo IA con múltiples bases de datos
# Asegúrate de que el servicio IA esté corriendo en puerto 8089

BASE_URL="http://localhost:8089/api/ia/prompt"

echo "=========================================="
echo "PRUEBAS DEL MÓDULO IA - MÚLTIPLES BASES DE DATOS"
echo "=========================================="
echo ""

# Función para hacer requests
test_prompt() {
    local descripcion=$1
    local prompt=$2

    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "📋 $descripcion"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Prompt: $prompt"
    echo ""

    response=$(curl -s -X POST "$BASE_URL" \
        -H "Content-Type: application/json" \
        -d "\"$prompt\"")

    echo "Respuesta:"
    echo "$response" | jq '.' 2>/dev/null || echo "$response"
    echo ""
    echo ""

    sleep 2
}

# PRUEBAS EN BASE DE DATOS PARADAS
echo "🗂️  PRUEBAS EN BD: PARADAS"
echo "=========================================="
test_prompt "Test 1: Todas las paradas" "Dame todas las paradas"
test_prompt "Test 2: Paradas específicas" "Dame las paradas de la ciudad Tandil"
test_prompt "Test 3: Conteo de paradas" "Cuántas paradas hay en total"

# PRUEBAS EN BASE DE DATOS USUARIOS
echo "🗂️  PRUEBAS EN BD: USUARIOS"
echo "=========================================="
test_prompt "Test 4: Todos los usuarios" "Dame todos los usuarios"
test_prompt "Test 5: Usuario por email" "Dame el usuario con email agusvan@gmail.com"
test_prompt "Test 6: Usuarios con rol ADMIN" "Dame los usuarios que son administradores"
test_prompt "Test 7: Cuentas activas" "Dame todas las cuentas activas"
test_prompt "Test 8: Cuentas premium" "Dame las cuentas tipo PREMIUM"

# PRUEBAS EN BASE DE DATOS VIAJES
echo "🗂️  PRUEBAS EN BD: VIAJES"
echo "=========================================="
test_prompt "Test 9: Todos los viajes" "Dame todos los viajes"
test_prompt "Test 10: Viajes activos" "Dame los viajes que están activos"
test_prompt "Test 11: Viajes largos" "Dame los viajes que duraron más de 40 minutos"
test_prompt "Test 12: Viajes con pausa" "Dame los viajes que tuvieron tiempo de pausa mayor a 0"

# PRUEBAS EN BASE DE DATOS FACTURAS
echo "🗂️  PRUEBAS EN BD: FACTURAS"
echo "=========================================="
test_prompt "Test 13: Todas las facturas" "Dame todas las facturas"
test_prompt "Test 14: Total facturado" "Cuál es el total facturado"
test_prompt "Test 15: Facturas mayores a 200" "Dame las facturas con importe mayor a 200"

# PRUEBAS EN BASE DE DATOS TARIFAS
echo "🗂️  PRUEBAS EN BD: TARIFAS"
echo "=========================================="
test_prompt "Test 16: Todas las tarifas" "Dame todas las tarifas"
test_prompt "Test 17: Tarifa más reciente" "Dame la tarifa más reciente"
test_prompt "Test 18: Tarifas con recargo" "Dame las tarifas con porcentaje de recargo mayor a 25"

# PRUEBAS CROSS-DATABASE (si el modelo las soporta)
echo "🗂️  PRUEBAS CROSS-DATABASE"
echo "=========================================="
test_prompt "Test 19: Usuarios y cuentas" "Dame los nombres de usuarios con sus tipos de cuenta"

echo "=========================================="
echo "✅ PRUEBAS COMPLETADAS"
echo "=========================================="

