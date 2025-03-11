package com.mycompany.projetologin.repository;

import com.mycompany.projetologin.dto.UserDTO;
import com.mycompany.projetologin.util.PasswordUtils;

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
    public void putUser(String uptUsername, String uptPassword, int id) throws Exception {
	String sql = "UPDATE users  SET name = ?, hashedPassword = ? WHERE id = ?";
            try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
                
                // Substitui o primeiro parâmetro (?) com o novo nome fornecido.
                pstmt.setString(1, uptUsername);
                
                // Substitui o segundo parâmetro (?) com o novo email fornecido.
                pstmt.setString(2, PasswordUtils.hashPassword(uptPassword));
                
                // Substitui o terceiro parâmetro (?) com o ID do usuário para identificar qual usuário atualizar.
                pstmt.setInt(3, id);
                
                // Executa o comando SQL e retorna o número de linhas afetadas pela operação.
                int rowsUpdated = pstmt.executeUpdate();

                // Verifica se pelo menos uma linha foi atualizada.
                if (rowsUpdated > 0) {
                    // Se a atualização foi bem-sucedida, imprime a mensagem de sucesso.
                    System.out.println("Usuário atualizado com sucesso!");
                } else {
                    // Se nenhuma linha foi atualizada (significa que o ID fornecido não foi encontrado), imprime uma mensagem.
                    System.out.println("Nenhum usuário encontrado com o ID fornecido.");
                }
            } catch (Exception e) {
                // Caso ocorra algum erro durante a execução do PreparedStatement, 
                // ele é capturado aqui.
                // O erro é impresso com uma mensagem explicativa.
                throw new Exception("Erro ao atualizar usuário: " + e.getMessage());
            }
        
    }
    public void deleteUser(int id) throws Exception {
	String sql = "DELETE FROM users WHERE id = ?";
			
	try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	
		pstmt.setInt(1, id);
		
		int rowsDeleted = pstmt.executeUpdate();
		
	} catch(Exception e) {
		throw new Exception("Erro ao deletar usuário: " + e.getMessage());
	}
    }
}
