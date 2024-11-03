package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    public static final char[] ABECEDARI = {
        'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë',
        'f', 'g', 'h', 'i', 'í', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ',
        'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü',
        'v', 'w', 'x', 'y', 'z'
    };

    public static final char[] ABECEDARIMAYUS = {
        'A', 'À', 'Á', 'Ä', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É', 'Ë',
        'F', 'G', 'H', 'I', 'Í', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ',
        'O', 'Ò', 'Ó', 'Ö', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ü',
        'V', 'W', 'X', 'Y', 'Z'
    };

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try{
            int clau2 = Integer.parseInt(clau);
        if(clau2>40 || clau2<0){ throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");}
        String resultat = xifraRotX(msg, clau2);
        return new TextXifrat(resultat.getBytes());
        }catch(NumberFormatException e){
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try{
            int clau2 = Integer.parseInt(clau);
            if(clau2>40 || clau2<0){ throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");}
            String text = new String(xifrat.getBytes());
            return desxifraRotX(text, clau2);
        }catch(NumberFormatException e){
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        
    }
    public String xifraRotX(String text, int shift) {
        StringBuilder textFinal = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, ABECEDARIMAYUS, shift));
            } else if (Character.isLowerCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, ABECEDARI, shift));
            } else {
                textFinal.append(currentChar);
            }
        }
        return textFinal.toString();
    }
    public String desxifraRotX(String text, int shift) {
        return xifraRotX(text, -shift);
    }
    public String forçaBrutaRotX(String text) {
        StringBuilder textFinal = new StringBuilder();
        for (int repes = 1; repes <= ABECEDARI.length; repes++) {
            textFinal.append("Número: ").append(repes)
                     .append("    ").append(desxifraRotX(text, repes)).append("\n");
        }
        return textFinal.toString();
    }
    public static char rotateChar(char ch, char[] abecedari, int shift) {
        int length = abecedari.length;
        for (int i = 0; i < length; i++) {
            if (abecedari[i] == ch) {
                int newIndex = (i + shift + length) % length;
                return abecedari[newIndex];
            }
        }
        return ch;
    }
}
