import java.util.Scanner;

public class Rot13 {
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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Introdueix un text per xifrar:");
        String inputText = scanner.nextLine();

        System.out.println("Introdueix un valor de xifratge:");
        int xifratge = Integer.parseInt(scanner.nextLine());    
        
        String textCifrat = xifraRot13(inputText, xifratge);
        System.out.println("Text xifrat: " + textCifrat);
        
        String textDesxifrat = desxifraRot13(textCifrat, xifratge);
        System.out.println("Text desxifrat: " + textDesxifrat);    

        System.out.println(forçaBrutaRotX(textCifrat));
        scanner.close();
    }
    //funcio per xifra el missatge
    public static String xifraRot13(String text, int xifratge) {
        StringBuilder textFinal = new StringBuilder();
        //recorrem el missatge
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            //si es majus el faig passar per l'array de majus
            if (Character.isUpperCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, ABECEDARIMAYUS, xifratge));
            } 
            //si no per les minuscules
            else if (Character.isLowerCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, ABECEDARI, xifratge));
            } 
            //si no es una lletra l'afegeix al final
            else {
                textFinal.append(currentChar);
            }
        }
        
        return textFinal.toString();
    }
    
    public static String desxifraRot13(String text, int xifratge) {
        StringBuilder textFinal = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            // majus
            if (Character.isUpperCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, ABECEDARIMAYUS, - xifratge));
            } 
            // minus
            else if (Character.isLowerCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, ABECEDARI, - xifratge));
            } 
            //si no es lletra
            else {
                textFinal.append(currentChar);
            }
        }
        
        return textFinal.toString();
    }


    public static String forçaBrutaRotX(String text){
        String textFinal = "";
        int repes = 1;
        while(repes <= ABECEDARI.length) {
            textFinal += "Número:"+ repes+ "    " +desxifraRot13(text, repes) + "\n";
            repes++;
        }
        return textFinal;

    }
    //funcio que ens calcula la rotacio del caracter
    public static char rotateChar(char ch, char[] abecedari, int shift) {
        int length = abecedari.length;
        for (int i = 0; i < abecedari.length; i++) {
            //comprovem que la lletra del text sigui igual que alguna de larray de lletres
            if (abecedari[i] == ch) {
                //en cas que ho sigui farem el calcul, amb el +lenght s'assegura que sempre sigui positiu i amb %length s'assegura que el resultat estigui dintre del rang de chaar[]abc
                int newIndex = (i + shift + length) % length;
                return abecedari[newIndex];
            }
        }
        return ch;
    }
}
