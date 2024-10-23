package iticbcn.xifratge;
//package activitat3;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador{
    public final char[]ABECEDARI = {
        'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë', 
        'f', 'g', 'h', 'i', 'í', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ', 
        'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü', 
        'v', 'w', 'x', 'y', 'z'
    };
    public final char[]abecedariXifat= permutaAlfabet(ABECEDARI);
    public void main(String[] args) {
        System.out.println("Aquest es l'ABECEDARI xifrat" + Arrays.toString(abecedariXifat));
        System.out.println("Introdueix un text per xifrar:");
        System.out.println("Text xifrat: " + xifraMonoAlfa("Hòls"));
        System.out.println("Text desxifrat: "+desxifraMonoAlfa(xifraMonoAlfa("Hòls")));
    }
    public String xifraMonoAlfa(String text){
        return intercanvia(text,ABECEDARI,abecedariXifat);
    }
    public String desxifraMonoAlfa(String text){
        return intercanvia(text,abecedariXifat,ABECEDARI);
    }
    //primero el abecedario cifrado y luego el descifrado
    public String intercanvia(String text, char[]abcXifrat, char[]abcDesXifrat){
        StringBuilder resultat = new StringBuilder();
        for(int i = 0; i<text.length();i++){
            char carAct = text.charAt(i);
            if(Character.isUpperCase(carAct)){
                carAct=Character.toLowerCase(carAct);
                for(int j=0; j<abcXifrat.length; j++){
                    if(carAct==abcXifrat[j]){
                        resultat.append(Character.toUpperCase(abcDesXifrat[j]));
                    }
                }
            }else if(Character.isLowerCase(carAct)){
                for(int j=0; j<abcXifrat.length; j++){
                    if(text.charAt(i)==abcXifrat[j]){
                        resultat.append(abcDesXifrat[j]);
                    }
                }
            }else{
                resultat.append(text.charAt(i));
            }
        }
        return resultat.toString();
    }
    public char[] permutaAlfabet(char [] abecedari){
        char[]abcPermutat=new char[abecedari.length];
        Character[] characterArray = new Character[abecedari.length];
        for (int i = 0; i < abecedari.length; i++) {
            characterArray[i] = abecedari[i];
        }
        List<Character> charList = Arrays.asList(characterArray);
        Collections.shuffle(charList);
        for (int i = 0; i < charList.size(); i++) {
            abcPermutat[i] = charList.get(i);
        }
        return abcPermutat;
    }
}
