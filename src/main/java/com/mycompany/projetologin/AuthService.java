package com.mycompany.projetologin;

public class AuthService {
    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean login(String username, String password) {

        try {
            UserDTO user = this.userRepository.getUser(username);
            if (user == null) {
                throw new RuntimeException("Usu");
            }

            if (!PasswordUtils.verifyPassword(password, user.getHashedPassword())) {
                throw new RuntimeException("Senha incorreta");
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
            return false;
        }

        return true;
    }
}
