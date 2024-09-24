import java.util.Scanner;

public class Rot13 {
    public static final char[] abecedari = {'a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final char[] abecedariMajus = {'A', 'B', 'C', 'Ç', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Introdueix un text per xifrar:");
        String inputText = scanner.nextLine();
        
        String textCifrat = xifraRot13(inputText);
        System.out.println("Text xifrat: " + textCifrat);
        
        String textDesxifrat = desxifraRot13(textCifrat);
        System.out.println("Text desxifrat: " + textDesxifrat);
        
        scanner.close();
    }
    //funcio per xifra el missatge
    public static String xifraRot13(String text) {
        StringBuilder textFinal = new StringBuilder();
        //recorrem el missatge
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            //si es majus el faig passar per l'array de majus
            if (Character.isUpperCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, abecedariMajus, 13));
            } 
            //si no per les minuscules
            else if (Character.isLowerCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, abecedari, 13));
            } 
            //si no es una lletra l'afegeix al final
            else {
                textFinal.append(currentChar);
            }
        }
        
        return textFinal.toString();
    }
    
    public static String desxifraRot13(String text) {
        StringBuilder textFinal = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            // majus
            if (Character.isUpperCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, abecedariMajus, -13));
            } 
            // minus
            else if (Character.isLowerCase(currentChar)) {
                textFinal.append(rotateChar(currentChar, abecedari, -13));
            } 
            //si no es lletra
            else {
                textFinal.append(currentChar);
            }
        }
        
        return textFinal.toString();
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
