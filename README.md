# 🎰 Proyecto Bingo Full-Stack

Este es un sistema de Bingo profesional desarrollado con **Java (Spring Boot)**, **React** y **MySQL**. El proyecto está completamente **dockerizado**, lo que permite levantarlo en cualquier máquina sin necesidad de instalar nada manualmente.

---

### 🚀 Levantar el sistema completo
Desde la raíz del proyecto, ejecutá:

```bash
docker compose up -d --build
🔗 Acceder a las aplicaciones
Frontend (React): http://localhost:5173

Backend (API): http://localhost:8080

Base de Datos (MySQL): Puerto 3306

🛠️ Tecnologías y Arquitectura
Backend: Java 21 & Spring Boot (contenedor bingo_backend).

Frontend: React + Vite (contenedor bingo_frontend).

Database: MySQL 8.0 (contenedor bingo_db).

🛠️ Desarrollo Manual (Opcional)
Si preferís trabajar sin Docker para debuggear el código directamente:

Backend
Requiere JDK 21 y Maven.

Configurar el application.properties para apuntar a localhost:3306.

Frontend
Requiere Node.js 22+.

Ejecutar npm install y luego npm run dev.

📩 Contacto
Desarrollado por Matias Ezequiel Mamaní

LinkedIn Profile
