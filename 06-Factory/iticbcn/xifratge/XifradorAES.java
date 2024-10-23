package iticbcn.xifratge;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import javax.crypto.spec.IvParameterSpec;
public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private final int MIDA_IV = 16;
    private byte[] iv = new byte[MIDA_IV];
    private final String CLAU = "yagito";

    public void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet","Hola Andrés cómo está tu cuñado","Àgora ïlla Ôtto"};
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";
            try {
            bXifrats = xifraAES(msg, CLAU);
            desxifrat = desxifraAES (bXifrats, CLAU);
        } catch (Exception e) {
            System.err.println("Error de xifrat: "+ e.getLocalizedMessage ());
        }   
        System.out.println("--------------------" );
        System.out.println("Msg: " + msg);
        System.out.println("Enc: " + new String(bXifrats));
        System.out.println("DEC: " + desxifrat);
        }
    }
    public byte[] xifraAES(String msg, String clau) throws Exception {
        // Obtener bytes del mensaje
        byte[] bytes = msg.getBytes("UTF-8");
        // Generar IV
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        //bloc de dades que juntament amb una clau per asegurar que el mateix text
        //xifrat moltes vegades dona textos diferents
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        // Generar clave a partir del hash
        //permet crear una clau secreta a partir d'un array de bytes
        //necessites una clau que sigui secreta i que s'utilitzi tant per xifrar com per desxifrar dades
        SecretKeySpec keySpec = crearClau(clau);

        // Cifrar el mensaje
        byte[] encrypted = cifrar(bytes, keySpec, ivParams);
    
        // Concatenar IV y mensaje cifrado
        return combinarIVyCifrado(iv, encrypted);
    }
    //metpde per crear la clau
    private SecretKeySpec crearClau(String clau) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = messageDigest.digest(clau.getBytes("UTF-8"));
        byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes16, ALGORISME_XIFRAT);
    }
    private byte[] cifrar(byte[] bytes, SecretKeySpec keySpec, IvParameterSpec ivParams) throws Exception {
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParams);
        return cipher.doFinal(bytes);
    }
    
    private byte[] combinarIVyCifrado(byte[] iv, byte[] encrypted) {
        byte[] encryptedMessage = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, encryptedMessage, 0, iv.length);
        System.arraycopy(encrypted, 0, encryptedMessage, iv.length, encrypted.length);
        return encryptedMessage;
    }
    public String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        // Extreure l'IV
        byte[] iv = Arrays.copyOfRange(bIvIMsgXifrat, 0, MIDA_IV);
        // Extreure la part xifrada
        byte[] encryptedMsg = Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
        // Generar hash de la clau
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = messageDigest.digest(clau.getBytes("UTF-8"));
        byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes16, ALGORISME_XIFRAT);
    
        // Inicialitzar el cifrador en mode desxifrat
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        // Desxifrar el missatge
        byte[] decrypted = cipher.doFinal(encryptedMsg);
        
        return new String(decrypted, "UTF-8");
    }
    
}