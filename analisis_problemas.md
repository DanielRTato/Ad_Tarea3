# Análisis de Problemas del Proyecto

He analizado el código fuente de los proyectos `MongoChamador` y `PelisPostgres` y he encontrado varios problemas críticos que impedirán el correcto funcionamiento del sistema.

## 1. `MongoChamador` se detiene inmediatamente (Crítico)
En la clase `org.example.Main` de `MongoChamador`, existe un método anotado con `@PostConstruct` que ejecuta una secuencia y luego termina la JVM:

```java
@PostConstruct
public void executar() {
    secuencia.executar();
    System.exit(200); // <--- ESTO MATARÁ EL SERVIDOR WEB
}
```

**El problema:** `MongoChamador` es una aplicación Web (tiene `@RestController` y `spring-boot-starter-web`). Al llamar a `System.exit(200)` durante el arranque (`PostConstruct`), la aplicación se cerrará inmediatamente después de iniciar, impidiendo que el servidor Tomcat reciba peticiones HTTP.

**Solución:** Eliminar `System.exit(200)` si la intención es que la API siga funcionando. Si es una tarea batch, no debería usar `spring-boot-starter-web` o debería estructurarse como un `CommandLineRunner` sin matar el proceso si se esperan otros hilos.

## 2. Comunicación entre microservicios incorrecta (Crítico)
El servicio `MongoChamador` intenta consumir `PelisPostgres`, pero las URLs y puertos no coinciden.

*   **Configuración en `Conexion.java` (MongoChamador):**
    ```java
    private static final String POSTGRES_URL_PELICULAS = "http://localhost:8081/peliculas";
    ```
    *   Apunta al puerto **8081**.
    *   Apunta a la ruta **/peliculas**.

*   **Realidad en `PelisPostgres`:**
    *   `application.properties`: `server.port=8080` (Escucha en el puerto **8080**, no 8081).
    *   `RestPeliculas.java`: `@RequestMapping(RestPeliculas.MAPPING)` donde `MAPPING = "/postgres/peliculas"`. (La ruta base es **/postgres/peliculas**, no /peliculas).

**Solución:**
Actualizar `Conexion.java` en `MongoChamador`:
```java
// Puerto 8080 y ruta /postgres/peliculas
private static final String POSTGRES_URL_PELICULAS = "http://localhost:8080/postgres/peliculas";
// Verificar también la ruta de actores
private static final String POSTGRES_URL_ACTORES = "http://localhost:8080/postgres/actores"; 
```

## 3. Cadena de Conexión a MongoDB Malformada (Potencial Error)
En `MongoChamador/src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://admin:admin@localhost:27017/?authSource=admin/probas
```
La parte `/probas` al final parece indicar la base de datos, pero está colocada después de los parámetros de consulta o mezclada con `authSource`.

**Formato estándar:** `mongodb://usuario:password@host:port/base_datos?authSource=admin`

**Solución sugerida:**
```properties
spring.data.mongodb.uri=mongodb://admin:admin@localhost:27017/probas?authSource=admin
```

## 4. Dependencias Duplicadas (Menor)
En `MongoChamador/pom.xml`, la dependencia `com.google.code.gson` está declarada dos veces con la misma versión. Esto no rompe la compilación pero es innecesario.

## 5. Errores Tipográficos (Menor)
En `MongoChamador/src/main/resources/application.properties`:
```properties
spring.application.nome=MongoChamador
```
Debería ser `spring.application.name`. Spring Boot ignorará `nome`.
