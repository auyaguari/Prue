package velo.uned.velocimetro.util;

/**
 * Created by Alvaro on 27/03/2018.
 */

public class Validacion {
    //valida que la cadena no tenga caracteres especiales
    public boolean validarCaracteresEspeciales(String text){
        boolean des=true;
        Character c;
        Integer val;
        for (Integer i=0;i<=text.length()-1;i++){
            c=text.charAt(i);
            val= Integer.valueOf(c);
            if(val<48 | val>57&val<65 |val>90&val<97 | val>122){
                return false;
            }
        }
        return des;
    }
    //valida tamaño 50 caracteres
    public boolean validarLongitud(String text){
        boolean des=true;
        Integer lon=text.length();
        if (lon>50)
            return false;
        return des;
    }
    //valida password 6 caracteres y 4 numeros
    public boolean validarPassword(String text) {
        boolean des = true;
        Character c;
        Integer val;
        Integer contnum = 0;
        Integer contlet = 0;
        for (Integer i = 0; i <= text.length() - 1; i++) {
            c = text.charAt(i);
            val = Integer.valueOf(c);
            if (val < 48 | val > 57 & val < 65 | val > 90 & val < 97 | val > 122) {
                return false;
            }else{
                if(val>=65&val<=90 | val>=97&val<=122){
                    contlet ++;
                }
                if(val>=48&val<=57){
                    contnum ++;
                }

            }
        }
        if (contlet<6 | contnum<4)
            return false;
        return des;
    }

}
