# CABA-Pro

Sistema web profesional para la gestión de árbitros, partidos, liquidaciones y torneos de baloncesto, desarrollado con Spring Boot, Thymeleaf y H2 Database.

## Tabla de Contenidos
- [Descripción](#descripción)
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Uso](#uso)
- [Módulos Principales](#módulos-principales)
- [Autores](#autores)
- [Licencia](#licencia)

---

## Descripción
CABA-Pro es una aplicación web diseñada para la administración integral de árbitros, partidos, liquidaciones y torneos de baloncesto. Permite el registro y gestión de usuarios con roles diferenciados (admin, árbitro), la asignación de partidos, la generación de liquidaciones y la visualización de información relevante en paneles personalizados.

---

## Características
- **Login y registro de usuarios** (árbitros y administradores)
- **Gestión de árbitros**: registro, edición y visualización de perfiles
- **Asignación y gestión de partidos**
- **Gestión de torneos**
- **Generación y consulta de liquidaciones**
- **Paneles de control diferenciados por rol**
- **Persistencia de datos con H2 Database**
- **Interfaz moderna con Thymeleaf**

---

## Tecnologías
- Java 17
- Spring Boot 3.5.5
- Spring Data JPA
- Spring MVC
- Thymeleaf
- H2 Database
- Maven

---

## Instalación
1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/Mvanegas1505/CABA-Pro-.git
   cd CABA-Pro-
   ```

2. **Compila y ejecuta el proyecto:**
   ```bash
   ./mvnw spring-boot:run
   ```
   O en Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

3. **Accede a la aplicación:**
   - [http://localhost:8080](http://localhost:8080)

4. **Accede a la consola H2 (base de datos):**
   - [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - URL JDBC: `jdbc:h2:file:./data/testdb`
   - Usuario: `sa`
   - Contraseña: *(vacía)*

---

## Configuración
- La configuración principal se encuentra en `src/main/resources/application.properties` y `application-dev.properties`.
- La base de datos H2 se almacena en modo archivo para persistencia entre reinicios.
- El perfil activo por defecto es `dev`.

---

## Estructura del Proyecto
```
CABA-Pro-
├── src
│   ├── main
│   │   ├── java/com/CABA/CabaPro
│   │   │   ├── controller/   # Controladores web
│   │   │   ├── dto/          # Clases DTO para transferencia de datos
│   │   │   ├── model/        # Entidades JPA
│   │   │   ├── repository/   # Interfaces de acceso a datos
│   │   │   ├── service/      # Lógica de negocio
│   │   │   └── CabaProApplication.java
│   │   ├── resources
│   │   │   ├── templates/    # Vistas Thymeleaf (admin, arbitro, user, home)
│   │   │   ├── static/       # Archivos estáticos (CSS, JS)
│   │   │   ├── application.properties
│   │   │   └── application-dev.properties
├── pom.xml
├── mvnw / mvnw.cmd           # Wrapper de Maven
└── data/                     # Archivo de base de datos H2
```

---

## Uso
- **Login:** Acceso para árbitros y administradores.
- **Panel de árbitro:** Visualización y edición de perfil, partidos asignados, liquidaciones.
- **Panel de administrador:** Gestión de árbitros, partidos y torneos.
- **Liquidaciones:** Generación y consulta de liquidaciones por partido.
- **Torneos:** Creación y gestión de torneos.

---

## Módulos Principales
- **controller/**: Lógica de rutas y endpoints web.
- **service/**: Lógica de negocio y operaciones sobre entidades.
- **model/**: Entidades JPA (Usuario, Partido, Liquidacion, etc.).
- **repository/**: Interfaces para acceso a datos con Spring Data JPA.
- **dto/**: Objetos de transferencia de datos para formularios y APIs.
- **templates/**: Vistas Thymeleaf para cada rol y funcionalidad.

---

## Autores
- Proyecto desarrollado por estudiantes de la Universidad [Nombre], para la materia "Tópicos Especiales en Ingeniería de Software".
- Repositorio mantenido por [Mvanegas1505](https://github.com/Mvanegas1505) y colaboradores.

---

## Licencia
Este proyecto se distribuye bajo la licencia MIT.  
Consulta el archivo LICENSE para más detalles.
