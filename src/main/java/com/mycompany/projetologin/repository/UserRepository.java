package com.mycompany.projetologin.repository;

import com.mycompany.projetologin.dto.UserDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class UserRepository {
    Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<UserDTO> getUsers() throws Exception {
        ArrayList<UserDTO> users = new ArrayList<>();

        // SQL para selecionar todos os registros
        String sql = "SELECT * FROM users";

        // Tentando executar a consulta SQL
        try (
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            // Loop para percorrer todos os registros retornados
            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("hashedPassword"));
                users.add(user);
            }
        } catch (Exception e) {
            throw new Exception( "Erro ao listar usuários: " + e.getMessage());
        }
        return users;
    }
    public UserDTO getUser(String name) throws Exception {
        String sql = "SELECT * FROM users WHERE name = ?";

        UserDTO user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // Verifica se há resultados antes de acessar os valores
                user = new UserDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("hashedPassword")
                );
            } else {
                throw new Exception("Usuário não foi encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Erro ao obter usuário " + name + ": " + e.getMessage());
        }

        return user;
    }

    public boolean createUser(String name, String hashedPassword) throws Exception {
        String sql = "INSERT INTO users (name, hashedPassword) VALUES (?, ?)";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, hashedPassword);

            pstmt.executeUpdate();

            System.out.println("Usuário Inserido com sucesso");
        } catch (Exception e) {
            throw new Exception("Erro ao inserir usuário" + e.getMessage());
        }
        return true;
    }
    public void putUser() {}
    public void deleteUser() {}
}
