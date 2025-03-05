package com.mycompany.projetologin.service;

import com.mycompany.projetologin.util.PasswordUtils;
import com.mycompany.projetologin.dto.UserDTO;
import com.mycompany.projetologin.repository.UserRepository;

public class AuthService {
    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String username, String password) throws Exception {
            UserDTO user = this.userRepository.getUser(username);
            if (user == null) {
                throw new RuntimeException("Esse usuário não existe");
            }
            if (!PasswordUtils.verifyPassword(password, user.getHashedPassword())) {
                throw new RuntimeException("Senha incorreta");
            }
    }
}
