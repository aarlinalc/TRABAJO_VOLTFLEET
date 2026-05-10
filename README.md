# ⚡ VoltFleet - Sistema de Gestión de Infraestructura de Recarga

VoltFleet es una solución Full-Stack diseñada para la gestión, monitorización y mantenimiento de redes de cargadores para vehículos eléctricos. Permite a los gestores visualizar el estado de la red en tiempo real y a los coordinadores técnicos administrar incidencias.

## 🏗️ Arquitectura del Proyecto

Este repositorio contiene un ecosistema dividido en dos piezas clave:
* **Frontend (Mobile):** Aplicación Android nativa (Java) con integración de Google Maps y consumo de API REST vía Volley.
* **Backend (API):** Servidor desarrollado en Spring Boot (Java 17) con arquitectura MVC.
* **Base de Datos:** PostgreSQL relacional.

## 🚀 Estado Actual (Roadmap)

- [x] **Fase 1:** Diseño de BD y despliegue del servidor API REST (Entidades: Usuarios, Estaciones, Cargadores).
- [x] **Fase 2:** UI/UX Android - Login validado contra servidor y visualización en tiempo real de estaciones en Google Maps. Navegación jerárquica a detalles de componentes técnicos.
- [ ] **Fase 3 (En curso):** Sistema de Ticketing bidireccional (Creación y gestión de incidencias/avisos).
- [ ] **Fase 4:** Panel de Inteligencia de Negocio (Cálculo de rentabilidad, kWh dispensados, telemetría).

## 🛠️ Requisitos Previos

Para ejecutar este proyecto en local, necesitas:
* **Java SDK 17** o superior.
* **PostgreSQL** instalado y corriendo en el puerto `5432`.
* **Android Studio** (Koala o superior recomendado).
* **Maven** (incluido vía wrapper en el proyecto backend).

## ⚙️ Instrucciones de Despliegue

### 1. Configuración de la Base de Datos
1. Crea una base de datos en PostgreSQL llamada `voltfleet_db`.
2. Ejecuta el script SQL incluido en `/backend/src/main/resources/schema.sql` (o deja que Hibernate genere las tablas automáticamente si está configurado en `update`).
3. Inserta los datos de prueba iniciales (mock data) en las tablas `estacion`, `cargador` y `usuario`.

### 2. Levantar el Backend (Spring Boot)
1. Navega a la carpeta del servidor.
2. Revisa el archivo `application.properties` y ajusta tus credenciales de PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/voltfleet_db
   spring.datasource.username=TU_USUARIO
   spring.datasource.password=TU_CONTRASEÑA
