package iticbcn.xifratge;
//package activitat3;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador{
    public static final char[]ABECEDARI = {
        'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë', 
        'f', 'g', 'h', 'i', 'í', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ', 
        'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü', 
        'v', 'w', 'x', 'y', 'z'
    };
    public char [] abcPermutat={};
    public Random CONTRASENYA;
    public void initRandom(int contrasenya){
        CONTRASENYA = new Random ( contrasenya );
    }
    public void main ( String [] args ) {
        String msgs[] = { "Test 01 àrbritre, coixí, Perímetre" ,
        "Test 02 Taüll, DÍA, año" ,
        "Test 03 Peça, Òrrius, Bòvila" };
        String msgsXifrats [] = new String [ msgs. length ];
        System . out . println ( "Xifratge: \n --------" );
        for ( int i = 0; i < msgs. length ; i ++) {
            initRandom ( 1234 );
            msgsXifrats [ i ] = xifraPoliAlfa ( msgs[ i ]);
            System . out . printf ( "%-34s -> %s%n", msgs[ i ], msgsXifrats [ i ]);
        }
        System . out . println ( "Desxifratge: \n -----------" );
        for ( int i = 0; i < msgs. length ; i ++) {
            initRandom ( 1234 );
            String msg = desxifraPoliAlfa ( msgsXifrats [ i ]);
            System . out . printf ( "%-34s -> %s%n", msgsXifrats [ i ], msg);
        }
    }
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada{
        try {
            int clau2 = Integer.parseInt(clau);
            initRandom(clau2);
        } catch (NumberFormatException e) {
            System.out.println("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
        String resultat = xifraPoliAlfa(msg);
        byte [] resultByte = resultat.getBytes();
        return new TextXifrat(resultByte);
    }
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada{
        String text = xifrat.toString();
        int clau2 = Integer.parseInt(clau);
        String resultat = desxifraRot13(text, clau2);
        return resultat;
    }
    public void permutaAlfabet(){
        abcPermutat=new char[ABECEDARI.length];
        Character[] characterArray = new Character[ABECEDARI.length];
        for (int i = 0; i < ABECEDARI.length; i++) {
            characterArray[i] = ABECEDARI[i];
        }
        List<Character> charList = Arrays.asList(characterArray);
        Collections.shuffle(charList, CONTRASENYA);
        for (int i = 0; i < charList.size(); i++) {
            abcPermutat[i] = charList.get(i);
        }
    }

    public String xifraPoliAlfa( String msg ){
        String resultat = "";
        for(int i = 0; i<msg.length();i++){
            char carAct = msg.charAt(i);
            permutaAlfabet();
            if(Character.isUpperCase(carAct)){
                carAct=Character.toLowerCase(carAct);
                for(int j=0; j<ABECEDARI.length; j++){
                    if(carAct==ABECEDARI[j]){
                        resultat += Character.toUpperCase(abcPermutat[j]);
                    }
                }
            }else if(Character.isLowerCase(carAct)){
                for(int j=0; j<ABECEDARI.length; j++){
                    if(msg.charAt(i)==ABECEDARI[j]){
                        resultat +=(abcPermutat[j]);
                    }
                }
            }else{
                resultat += (msg.charAt(i));
            }
        }
        return resultat;
    }

    //descifra un mensaje polialfabetic
    public String desxifraPoliAlfa( String msgXifrat ){
        String resultat = "";
        for(int i = 0; i<msgXifrat.length();i++){
            char carAct = msgXifrat.charAt(i);
            permutaAlfabet();
            if(Character.isUpperCase(carAct)){
                carAct=Character.toLowerCase(carAct);
                for(int j=0; j<ABECEDARI.length; j++){
                    
                    if(carAct==abcPermutat[j]){
                        resultat += Character.toUpperCase(ABECEDARI[j]);
                    }
                }
            }else if(Character.isLowerCase(carAct)){
                for(int j=0; j<ABECEDARI.length; j++){
                    if(msgXifrat.charAt(i)==abcPermutat[j]){
                        resultat +=(ABECEDARI[j]);
                    }
                }
            }else{
                resultat += (msgXifrat.charAt(i));
            }
        }
        return resultat;
    }
}    
