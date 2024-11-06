package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception{
        //amb aquesta comanda es crea el parell de claus amb l'algorisme RSA, aquest es un tipus d'algorisme criptografic
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        //generar el parell de claus
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }
    public byte[] xifraRSA(String msg, PublicKey clauPublica)throws Exception{
        //com el xifratge es realitza a partir de bytes, hem de passar a bytes el missatge
        byte[] datos = msg.getBytes("UTF-8");
        //instanciar l'objecte cipher (cipher, es una classe que encripta i desencripta) en l'algorisme RSA com en el genera
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        //inicialitzar el cipher en mode xifratge amb la clau publica
        cipher.init(Cipher.ENCRYPT_MODE, clauPublica);
        //tornar el missatge xifrat en un array de bytes
        byte[] mensajeCifrado = cipher.doFinal(datos);
        //retornar el missatge xifrat en un array de bytes
        return mensajeCifrado;
    }
    String desxifraRSA(byte[] msgXifrat, PrivateKey ClauPrivada)throws Exception {
        // Crear una instancia de Cipher amb algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Inicializar el Cipher en modo de desxifrat amb la clau privada
        cipher.init(Cipher.DECRYPT_MODE, ClauPrivada);
        //convertim el resultat a un array de bytes, ja que el resultat esta en bytes
        byte[] datosDesxifrats = cipher.doFinal(msgXifrat);
        // Passar a un string l'array de bytes del resultat i posar-lo a UTF-8, suposant que el missatge original estaba en UTF-8
        return new String(datosDesxifrats, StandardCharsets.UTF_8);
    }
}
