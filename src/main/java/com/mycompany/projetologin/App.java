package com.mycompany.projetologin;

import com.mycompany.projetologin.config.DatabaseConnection;
import com.mycompany.projetologin.config.DatabaseTableInitializer;
import com.mycompany.projetologin.dto.UserDTO;
import com.mycompany.projetologin.repository.UserRepository;
import com.mycompany.projetologin.service.AuthService;

import java.sql.Connection;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        DatabaseConnection dbc = new DatabaseConnection();
        Connection conn = dbc.connect();

        DatabaseTableInitializer.createUsers(conn);

        UserRepository userRepository = new UserRepository(conn);

        //userRepository.createUser("Adrian", PasswordUtils.hashPassword("123"));
        //userRepository.createUser("Bruno", PasswordUtils.hashPassword("1234"));

        try {
            UserDTO user = userRepository.getUser("Adrian");
            ArrayList<UserDTO> users = userRepository.getUsers();
            System.out.println(user.toString());
            System.out.println(users);

            // Simulando login

            AuthService authService = new AuthService(userRepository);
            boolean result = authService.login("Adian", "123");
            System.out.println(result);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        dbc.disconnect(conn);
    }
}
