import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

public class Hashes {

    // Variable global para contar los intentos de fuerza bruta
    public int npass = 0;

    // Método para obtener el hash SHA-512 con salt
    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update((pw + salt).getBytes());
        byte[] hash = md.digest();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }

    // Método para obtener el hash PBKDF2 con salt (sin SecretKeyFactory)
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        char[] passwordChars = pw.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 65536, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }

    

    // Método de fuerza bruta
    public String forcaBruta(String alg, String hash, String salt)
            throws NoSuchAlgorithmException, Exception {
        String charset = "abcdefABCDEF1234567890!"; // Conjunto de caracteres a probar
        npass = 0; // Resetear contador de intentos

        // Longitudes de 1 a 6
        for (int i = 0; i < charset.length(); i++) {
            String guess = "" + charset.charAt(i);
            npass++; // Incrementar el contador de intentos
            if (checkPassword(guess, alg, hash, salt))
                return guess;

            for (int y = 0; y < charset.length(); y++) {
                guess = "" + charset.charAt(i) + charset.charAt(y);
                npass++; // Incrementar el contador de intentos
                if (checkPassword(guess, alg, hash, salt))
                    return guess;

                for (int x = 0; x < charset.length(); x++) {
                    guess = "" + charset.charAt(i) + charset.charAt(y) + charset.charAt(x);
                    npass++; // Incrementar el contador de intentos
                    if (checkPassword(guess, alg, hash, salt))
                        return guess;

                    for (int z = 0; z < charset.length(); z++) {
                        guess = "" + charset.charAt(i) + charset.charAt(y) + charset.charAt(x) + charset.charAt(z);
                        npass++; // Incrementar el contador de intentos
                        if (checkPassword(guess, alg, hash, salt))
                            return guess;

                        for (int l = 0; l < charset.length(); l++) {
                            guess = "" + charset.charAt(i) + charset.charAt(y) + charset.charAt(x)
                                    + charset.charAt(z) + charset.charAt(l);
                            npass++; // Incrementar el contador de intentos
                            if (checkPassword(guess, alg, hash, salt))
                                return guess;

                            for (int m = 0; m < charset.length(); m++) {
                                guess = "" + charset.charAt(y) + charset.charAt(x) + charset.charAt(x)
                                        + charset.charAt(z) + charset.charAt(l) + charset.charAt(m);
                                npass++; // Incrementar el contador de intentos
                                if (checkPassword(guess, alg, hash, salt))
                                    return guess;
                            }
                        }
                    }
                }
            }
        }

        return null; // No match found
    }

    // Helper method to check the password
    private boolean checkPassword(String guess, String alg, String hash, String salt)
            throws NoSuchAlgorithmException, Exception {
        String generatedHash = alg.equals("SHA-512") ? getSHA512AmbSalt(guess, salt)
                : getPBKDF2AmbSalt(guess, salt);
        return generatedHash.equals(hash);
    }

    // Método para calcular el intervalo de tiempo entre dos tiempos en milisegundos
    public String getInterval(long t1, long t2) {
        long duration = t2 - t1;
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
        long millis = duration - TimeUnit.SECONDS.toMillis(seconds);

        return String.format("%d días / %d horas / %d minutos / %d segundos / %d millis", days, hours, minutes, seconds,
                millis);
    }

    // Método main para ejecutar el ejemplo
    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";  // Sal proporcionada
        String pw = "aaabF!";  // Contraseña original
        Hashes h = new Hashes();

        // Generación de los hashes de la contraseña con sal
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };

        // Intentar forzar la contraseña para cada algoritmo
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();  // Tiempo de inicio
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();  // Tiempo de fin

            // Mostrar los resultados
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);  // Número de intentos realizados
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }
}
