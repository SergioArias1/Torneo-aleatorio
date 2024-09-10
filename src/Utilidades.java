import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {

    private static Scanner teclado = new Scanner(System.in);

    public static double leerDouble (String texto){
        double n = 0;
        boolean repetir;
        do{
            repetir = false;
            try{
                System.out.print(texto);
                n = teclado.nextDouble();
            }catch (InputMismatchException ex){
                System.out.println("Valor no válido: ");
                repetir = true;
            }finally {
                teclado.nextLine();
            }
        }while (repetir);
        return n;
    }

    public static String leerString (String mensaje){
        String res;
        System.out.print(mensaje);
        res = teclado.nextLine();
        return res;
    }

    public static boolean leerBoolean(String mensaje) {
        boolean resultado = false;
        boolean correcto = false;
        do {
            System.out.print(mensaje);
            String cadena = teclado.nextLine();
            if (Character.toUpperCase(cadena.charAt(0)) == 'S') {
                resultado = true;
                correcto = true;
            } else if (Character.toUpperCase(cadena.charAt(0)) == 'N') {
                resultado = false;
                correcto = true;
            }
        } while (!correcto);
        return resultado;
    }

    public static int leerNumeroEspecial(String mensaje) {
        int res = 0;
        boolean correcto = false;
        while (!correcto) {
            System.out.print(mensaje);
            try {
                res = teclado.nextInt();
                if (res == 2 || res == 4 || res == 8 || res == 16 || res == 32 || res == 64
                || res == 128 || res == 256 || res == 512 || res == 1024) {
                    correcto = true;
                }else {
                    System.out.println("Cantidad no válida de participantes.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Introduzca un valor numérico.");
            }finally {
                teclado.nextLine();
            }
        }
        return res;
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int res = 0;
        boolean correcto = false;
        while (!correcto) {
            System.out.print(mensaje);
            try {
                res = teclado.nextInt();
                if (res >= minimo && res <= maximo) {
                    correcto = true;
                } else {
                    System.out.println("Introduzca un valor dentro de los límites (" + minimo + "-" + maximo + ").");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Introduzca un valor numérico.");
            }finally {
                teclado.nextLine();
            }
        }
        return res;
    }




}
