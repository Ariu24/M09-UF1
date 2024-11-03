package iticbcn.xifratge;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador {
    private static final char[] ABECEDARI = {
        'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë', 
        'f', 'g', 'h', 'i', 'í', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ', 
        'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü', 
        'v', 'w', 'x', 'y', 'z'
    };
    
    private final char[] abecedariXifat;

    public XifradorMonoalfabetic() {
        this.abecedariXifat = permutaAlfabet(ABECEDARI);
    }

    public String xifraMonoAlfa(String text) {
        return intercanvia(text, ABECEDARI, abecedariXifat);
    }

    public String desxifraMonoAlfa(String text) {
        return intercanvia(text, abecedariXifat, ABECEDARI);
    }

    private String intercanvia(String text, char[] abcXifrat, char[] abcDesXifrat) {
        StringBuilder resultat = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char carAct = text.charAt(i);
            if (Character.isUpperCase(carAct)) {
                carAct = Character.toLowerCase(carAct);
                appendReplacedChar(carAct, abcXifrat, abcDesXifrat, resultat, true);
            } else if (Character.isLowerCase(carAct)) {
                appendReplacedChar(carAct, abcXifrat, abcDesXifrat, resultat, false);
            } else {
                resultat.append(carAct);
            }
        }
        return resultat.toString();
    }

    private void appendReplacedChar(char carAct, char[] abcXifrat, char[] abcDesXifrat, StringBuilder resultat, boolean isUpperCase) {
        for (int j = 0; j < abcXifrat.length; j++) {
            if (carAct == abcXifrat[j]) {
                resultat.append(isUpperCase ? Character.toUpperCase(abcDesXifrat[j]) : abcDesXifrat[j]);
                return;
            }
        }
        resultat.append(carAct);
    }

    private char[] permutaAlfabet(char[] abecedari) {
        char[] abcPermutat = Arrays.copyOf(abecedari, abecedari.length);
        List<Character> charList = Arrays.asList(new Character[abcPermutat.length]);
        for (int i = 0; i < abcPermutat.length; i++) {
            charList.set(i, abcPermutat[i]);
        }
        Collections.shuffle(charList);
        for (int i = 0; i < charList.size(); i++) {
            abcPermutat[i] = charList.get(i);
        }
        return abcPermutat;
    }

    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        String resultat = xifraMonoAlfa(msg);
        return new TextXifrat(resultat.getBytes());
    }

    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        String text = new String(xifrat.getBytes());
        return desxifraMonoAlfa(text);
    }
}
