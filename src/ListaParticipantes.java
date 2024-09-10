import java.io.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class ListaParticipantes {

    private Participante[] participantes;

    private int numParticipantes;

    private int max_Participantes;

    private Scanner teclado = new Scanner(System.in);

    public ListaParticipantes (int max_Participantes){
        this.participantes = new Participante[max_Participantes];
        this.numParticipantes = max_Participantes;
        this.max_Participantes = max_Participantes;
        reunirParticipantes();
    }

    public ListaParticipantes(File fichero){
        System.out.println();
        String[] ficherosTorneos = mostrarFicheroTorneosJugados("TorneosRep");
        System.out.println();
        int ficheroElegido = Utilidades.leerNumero(teclado,"Elija un torneo" +
                " tecleando el número correspondiente: ",1,ficherosTorneos.length);
        System.out.println();
        System.out.println("Aqui tienes los participantes con los que inicializarás el torneo:");
        System.out.println();
        String cadena = ficherosTorneos[ficheroElegido - 1];
        // Verificar y mostrar el resultado
        String nombreFichero = cadena;
        String[] participantes = leerFicheroParticipantes(nombreFichero);
        System.out.println();
        System.out.print("Pulsa cualquier tecla y ENTER para continuar: ");
        teclado.next();
        teclado.nextLine();
        int longitudFichero = contarLineas(nombreFichero);
        this.max_Participantes = longitudFichero;
        this.numParticipantes = longitudFichero;
        this.participantes = new Participante[max_Participantes];
        inicializarParticipantes(participantes);
    }


    public int getNumParticipantes(){
        return numParticipantes;
    }

    public void reunirParticipantes(){
        System.out.println();
        System.out.println("Introduce el nombre de los participantes: ");
        String participante;
        for (int i = 0; i < numParticipantes; i++){
            System.out.print("Participante " + (i+1) + ": ");
            participante = teclado.nextLine();
            participantes[i] = new Participante(participante,(i+1));
            for (int j = 0; j < i; j++){
                if (participantes[i].getNombre().equalsIgnoreCase(participantes[j].getNombre())){
                    System.out.println("Ya existe un participante con ese nombre.");
                    i--;
                }
            }
        }
    }

    public void inicializarParticipantes(String[] listaParticipantes){
        for (int i = 0; i < listaParticipantes.length; i++){
            this.participantes[i] = new Participante(listaParticipantes[i],(i+1));
        }
    }

    public void eliminarParticipante(String nombreParticipante){
        for (int i = 0; i < numParticipantes; i++){
            if (participantes[i].getNombre().equals(nombreParticipante)){
                for (int j = (i + 1); j < numParticipantes; j++){
                    participantes[j-1] = participantes[j];
                }
                numParticipantes--;
            }
        }
    }

    public void mostrarParticipantes(){
        System.out.println("----PARTICIPANTES ACTUALES----");
        int contador = 0;
        for (int i = 0; i < numParticipantes; i++){
            contador++;
            System.out.print(participantes[i].toString() + "   ");
            if (contador == 5){
                System.out.println("\n");
                contador = 0;
            }
        }
        System.out.println();
    }



    public Participante getParticipante (int identificador){
        Participante res = null;
        for (int i = 0; i < numParticipantes; i++){
            if (participantes[i].getIdentificador() == identificador){
                res = participantes[i];
            }
        }
        return res;
    }

    public void sustituirParticipante(String nombreParticipante, String nombreNuevo){
        for (int i = 0; i < numParticipantes; i++){
            if (participantes[i].getNombre().equalsIgnoreCase(nombreParticipante)){
                participantes[i].setNombre(nombreNuevo);
            }
        }
    }

    public Participante getParticipante2 (int posicion){
        return participantes[posicion];
    }

    public void mostrarParticipantesABorrar(){
        for (int i = 0; i < numParticipantes; i++){
            System.out.println(participantes[i]);
        }
    }

    public void guardarParticipantes(String fichero) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero + ".txt");
            for (int i = 0; i < max_Participantes; i++) {
                salida.println(getParticipante2(i).getNombre());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
    }

    public void acumularFicheroParticipantes(String fichero, String nombreFichero) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(new FileWriter(fichero + ".txt", true));
            salida.println(nombreFichero);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
    }

    public String[] mostrarFicheroTorneosJugados(String fichero){
        BufferedReader entrada = null;
        String[] nombresFicheros = null;
        try {
            entrada = new BufferedReader(new FileReader(fichero + ".txt"));
            String cadena;
            nombresFicheros = new String[contarLineas(fichero)];
            int i = 0;
            while ((cadena = entrada.readLine()) != null) {
                File file = new File(cadena + ".txt");
                if (file.exists()) {
                    System.out.println((i + 1) + ". " + cadena);
                    nombresFicheros[i] = cadena;
                    i++;
                }
            }
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }finally {
            try {
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return nombresFicheros;
    }

    public String[] leerFicheroParticipantes(String fichero){
        BufferedReader entrada = null;
        String[] participantes = null;
        try {
            entrada = new BufferedReader(new FileReader(fichero + ".txt"));
            String cadena;
            participantes = new String[contarLineas(fichero)];
            int i = 0;
            while ((cadena = entrada.readLine()) != null) {
                System.out.println(cadena);
                participantes[i] = cadena;
                i++;
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return participantes;
    }

    public int contarLineas(String fichero){
        BufferedReader entrada = null;
        int lineas = 0;
        String cadena;
        try {
            entrada = new BufferedReader(new FileReader(fichero + ".txt"));
            while ((cadena = entrada.readLine()) != null) {
                lineas++;
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return lineas;
    }
}
