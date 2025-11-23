package org.example.ia.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Administrador de conexiones a múltiples bases de datos (MySQL y MongoDB).
 * Permite ejecutar consultas en cualquier base de datos según el contexto.
 */
@Component
public class DataSourceManager {

    private final Map<String, JdbcTemplate> jdbcTemplates = new HashMap<>();
    private final Map<String, DataSource> dataSources = new HashMap<>();
    private final MongoTemplate mongoTemplate;

    public DataSourceManager(
            @Qualifier("paradasJdbcTemplate") JdbcTemplate paradasJdbcTemplate,
            @Qualifier("usuariosJdbcTemplate") JdbcTemplate usuariosJdbcTemplate,
            @Qualifier("viajesJdbcTemplate") JdbcTemplate viajesJdbcTemplate,
            @Qualifier("facturasJdbcTemplate") JdbcTemplate facturasJdbcTemplate,
            @Qualifier("tarifasJdbcTemplate") JdbcTemplate tarifasJdbcTemplate,
            @Qualifier("paradasDataSource") DataSource paradasDataSource,
            @Qualifier("usuariosDataSource") DataSource usuariosDataSource,
            @Qualifier("viajesDataSource") DataSource viajesDataSource,
            @Qualifier("facturasDataSource") DataSource facturasDataSource,
            @Qualifier("tarifasDataSource") DataSource tarifasDataSource,
            @Qualifier("mongoTemplate") MongoTemplate mongoTemplate
    ) {
        // Registrar JdbcTemplates
        jdbcTemplates.put("paradas", paradasJdbcTemplate);
        jdbcTemplates.put("usuarios", usuariosJdbcTemplate);
        jdbcTemplates.put("viajes", viajesJdbcTemplate);
        jdbcTemplates.put("facturas", facturasJdbcTemplate);
        jdbcTemplates.put("tarifas", tarifasJdbcTemplate);

        // Registrar DataSources
        dataSources.put("paradas", paradasDataSource);
        dataSources.put("usuarios", usuariosDataSource);
        dataSources.put("viajes", viajesDataSource);
        dataSources.put("facturas", facturasDataSource);
        dataSources.put("tarifas", tarifasDataSource);

        // Registrar MongoDB
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Obtiene el JdbcTemplate para una base de datos específica
     */
    public JdbcTemplate getJdbcTemplate(String database) {
        JdbcTemplate template = jdbcTemplates.get(database.toLowerCase());
        if (template == null) {
            throw new IllegalArgumentException("Base de datos no configurada: " + database);
        }
        return template;
    }

    /**
     * Obtiene el DataSource para una base de datos específica
     */
    public DataSource getDataSource(String database) {
        DataSource ds = dataSources.get(database.toLowerCase());
        if (ds == null) {
            throw new IllegalArgumentException("Base de datos no configurada: " + database);
        }
        return ds;
    }

    /**
     * Obtiene el MongoTemplate para consultas MongoDB
     */
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    /**
     * Detecta automáticamente qué base de datos usar según las tablas mencionadas en la SQL
     */
    public String detectDatabase(String sql) {
        String sqlLower = sql.toLowerCase();

        // Tablas de MONOPATINES (MongoDB)
        if (sqlLower.contains("monopatin")) {
            return "monopatines";
        }
        // Tablas de USUARIOS
        if (sqlLower.contains("usuario") || sqlLower.contains("cuenta")) {
            return "usuarios";
        }
        // Tablas de VIAJES
        if (sqlLower.contains("viaje")) {
            return "viajes";
        }
        // Tablas de FACTURAS
        if (sqlLower.contains("factura")) {
            return "facturas";
        }
        // Tablas de TARIFAS
        if (sqlLower.contains("tarifa")) {
            return "tarifas";
        }
        // Tablas de PARADAS (por defecto)
        if (sqlLower.contains("parada")) {
            return "paradas";
        }

        // Por defecto, usar paradas
        return "paradas";
    }

    /**
     * Verifica si la base de datos es MongoDB
     */
    public boolean isMongoDB(String database) {
        return "monopatines".equalsIgnoreCase(database);
    }

    /**
     * Lista todas las bases de datos disponibles
     */
    public String[] getAvailableDatabases() {
        String[] sqlDatabases = jdbcTemplates.keySet().toArray(new String[0]);
        String[] allDatabases = new String[sqlDatabases.length + 1];
        System.arraycopy(sqlDatabases, 0, allDatabases, 0, sqlDatabases.length);
        allDatabases[sqlDatabases.length] = "monopatines";
        return allDatabases;
    }
}
