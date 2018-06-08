package gabrieltakazaki.utfpr.edu.br.churrasco.utils;

public class UtilsVazio {
    public static boolean stringVazia(String texto){

        if (texto == null || texto.trim().length() == 0){
            return true;
        }else{
            return false;
        }
    }
}
