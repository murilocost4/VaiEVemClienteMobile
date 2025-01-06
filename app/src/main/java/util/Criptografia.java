package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    public static String criptografarSenha(String senha) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] hash = md.digest(senha.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {

                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
    }
}
