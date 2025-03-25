package com.crud.crudjsf.config;


import org.mindrot.jbcrypt.BCrypt;

public class SecurityConfig {

    // Método para encriptar una contraseña

    public String encodePassword(String rawPassword) {
        // Generar un salt aleatorio y encriptar la contraseña
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    // Método para verificar la contraseña texto plano contra la contraseña encriptada

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        // Verificar si la contraseña cruda coincide con la contraseña encriptada
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}