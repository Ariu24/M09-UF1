package iticbcn.xifratge;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    public static final char[] ABECEDARI = {
        'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë',
        'f', 'g', 'h', 'i', 'í', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ',
        'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü',
        'v', 'w', 'x', 'y', 'z'
    };

    private char[] abcPermutat;
    private Random contrasenya;

    private void initRandom(Long contrasenya) {
        this.contrasenya = new Random(contrasenya);
    }

    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        Long clau2 = parseClau(clau);
        initRandom(clau2);
        String resultat = xifraPoliAlfa(msg);
        return new TextXifrat(resultat.getBytes());
    }
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        Long clau2 = parseClau(clau);
        initRandom(clau2);
        String text = new String(xifrat.getBytes());
        return desxifraPoliAlfa(text);
    }
    private Long parseClau(String clau) throws ClauNoSuportada {
        try {
            return Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }
    private void permutaAlfabet() {
        abcPermutat = Arrays.copyOf(ABECEDARI, ABECEDARI.length);
        for (int i = abcPermutat.length - 1; i > 0; i--) {
            int j = contrasenya.nextInt(i + 1);
            char temp = abcPermutat[i];
            abcPermutat[i] = abcPermutat[j];
            abcPermutat[j] = temp;
        }
    }

    public String xifraPoliAlfa(String msg) {
        StringBuilder resultat = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char carAct = msg.charAt(i);
            permutaAlfabet();
            if (Character.isUpperCase(carAct)) {
                carAct = Character.toLowerCase(carAct);
                resultat.append(replaceChar(carAct, true));
            } else if (Character.isLowerCase(carAct)) {
                resultat.append(replaceChar(carAct, false));
            } else {
                resultat.append(carAct);
            }
        }
        return resultat.toString();
    }

    private char replaceChar(char carAct, boolean isUpperCase) {
        for (int j = 0; j < ABECEDARI.length; j++) {
            if (carAct == ABECEDARI[j]) {
                return isUpperCase ? Character.toUpperCase(abcPermutat[j]) : abcPermutat[j];
            }
        }
        return carAct;
    }

    public String desxifraPoliAlfa(String msgXifrat) {
        StringBuilder resultat = new StringBuilder();
        for (int i = 0; i < msgXifrat.length(); i++) {
            char carAct = msgXifrat.charAt(i);
            permutaAlfabet();
            if (Character.isUpperCase(carAct)) {
                carAct = Character.toLowerCase(carAct);
                resultat.append(replaceDesxifraChar(carAct, true));
            } else if (Character.isLowerCase(carAct)) {
                resultat.append(replaceDesxifraChar(carAct, false));
            } else {
                resultat.append(carAct);
            }
        }
        return resultat.toString();
    }

    private char replaceDesxifraChar(char carAct, boolean isUpperCase) {
        for (int j = 0; j < ABECEDARI.length; j++) {
            if (carAct == abcPermutat[j]) {
                return isUpperCase ? Character.toUpperCase(ABECEDARI[j]) : ABECEDARI[j];
            }
        }
        return carAct;
    }
}
