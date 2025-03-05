package com.mycompany.projetologin.config;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseTableInitializer {
    public static void createUsers(Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE NOT NULL,"+
                "hashedPassword TEXT NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso");
        } catch(Exception e) {
            System.out.println("Erro ao criar tabela" + e.getMessage());
        }
    }
}
