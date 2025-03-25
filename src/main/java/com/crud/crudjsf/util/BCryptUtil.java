package com.crud.crudjsf.util;
import org.mindrot.jbcrypt.BCrypt;


public class BCryptUtil {

    // Encriptar la contraseña
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verificar la contraseña
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}