import javax.imageio.IIOException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Juego {

    private String fase;

    private ListaParticipantes lista;

    private int num_participantesInicial;

    private Scanner teclado = new Scanner(System.in);

    public Juego() {
        boolean respuesta = false;
        File fichero = new File("TorneosRep.txt");
        if (fichero.exists() && fichero.length() > 0){
            respuesta = Utilidades.leerBoolean("¿Desea repetir algún torneo de los jugados?: ");
            if (respuesta){
                this.lista = new ListaParticipantes(fichero);
                this.num_participantesInicial = lista.getNumParticipantes();
                System.out.println();
            }else {
                int numParticipantes = Utilidades.leerNumeroEspecial("Introduce el número de participantes [2,4,8,16,32,64,128,256,512 o 1024]: ");
                System.out.println();
                this.num_participantesInicial = numParticipantes;
                this.lista = new ListaParticipantes(numParticipantes);
                System.out.println();
            }
        }else {
            int numParticipantes = Utilidades.leerNumeroEspecial("Introduce el número de participantes [2,4,8,16,32,64,128,256,512 o 1024]: ");
            System.out.println();
            this.num_participantesInicial = numParticipantes;
            this.lista = new ListaParticipantes(numParticipantes);
            System.out.println();
        }
        lista.mostrarParticipantesABorrar();
        System.out.println();
        boolean resul = Utilidades.leerBoolean("¿Deseas cambiar el nombre de algún participante? No podrás hacerlo mas tarde: ");
        System.out.println();
        if (resul) {
            boolean encontrado = false;
            boolean seguir;
            boolean mostrarLista = false;
            do {
                if (mostrarLista) {
                    System.out.println();
                    lista.mostrarParticipantesABorrar();
                }
                System.out.println();
                System.out.print("Escribe el nombre del participante a modificar, o escriba SALIR para salir: ");
                String participanteElegido = teclado.nextLine();
                for (int i = 0; i < this.num_participantesInicial && !encontrado; i++) {
                    String nombreParticipante = lista.getParticipante2(i).getNombre();
                    int identificadorParticipante = lista.getParticipante2(i).getIdentificador();
                    if (participanteElegido.equalsIgnoreCase(nombreParticipante)) {
                        encontrado = true;
                    }
                }
                if (participanteElegido.equalsIgnoreCase("SALIR")) {
                    seguir = false;
                } else if (!encontrado) {
                    System.out.println("No existe el participante con nombre " + participanteElegido + ".");
                    seguir = true;
                    mostrarLista = false;
                } else {
                    System.out.println();
                    boolean yaExiste;
                    String nuevoNombreParticipante;
                    do {
                        yaExiste = false;
                        System.out.print("Escribe el nuevo nombre del participante: ");
                        nuevoNombreParticipante = teclado.nextLine();
                        for (int i = 0; i < this.num_participantesInicial && !yaExiste; i++) {
                            if (nuevoNombreParticipante.equalsIgnoreCase(lista.getParticipante2(i).getNombre())) {
                                System.out.println(nuevoNombreParticipante + " ya está participando. ");
                                yaExiste = true;
                            }
                        }
                    } while (yaExiste);
                    lista.sustituirParticipante(participanteElegido, nuevoNombreParticipante);
                    System.out.println();
                    seguir = Utilidades.leerBoolean("¿Desea modificar algún otro participante?: ");
                    encontrado = false;
                    if (seguir) {
                        mostrarLista = true;
                    }
                }
            } while (seguir);
        }
        if (!respuesta || (resul)){
            boolean res = Utilidades.leerBoolean("¿Vas a querer guardar el torneo para futura repetición?: ");
            if (res){
                String nombreFichero = Utilidades.leerString("Escriba el nombre del archivo: ");
                lista.guardarParticipantes(nombreFichero);
                lista.acumularFicheroParticipantes("TorneosRep",nombreFichero);
            }
        }
    }

    public boolean isInteger(String cadena) {
        boolean res;
        try {
            int entero = Integer.parseInt(cadena);
            res = true;
        } catch (NumberFormatException ex) {
            res = false;
        }
        return res;
    }


    public static void main(String[] args) {
        System.out.println("---------------------------------------");
        System.out.println("SERGIO'S WORLD ALEATORY CUP SIMULATION");
        System.out.println("---------------------------------------");
        System.out.println("\n");
        Juego juego = new Juego();
        juego.jugar();
    }

    public void jugar() {
        String ganadorFinal;
        String fase;
        while (lista.getNumParticipantes() > 1) {
            lista.mostrarParticipantes();
            fase = getFase();
            System.out.println();
            System.out.println(fase);
            for (int i = 0; i < fase.length(); i++) {
                System.out.print("-");
            }
            System.out.println();
            simularEnfrentamientos();
        }
        ganadorFinal = lista.getParticipante2(0).getNombre();
        System.out.println("*************************");
        System.out.println("GANADOR FINAL: " + ganadorFinal);
        System.out.println("*************************");
        boolean respuesta = Utilidades.leerBoolean("¿Desea guardar el resultado? ");
        if (respuesta) {
            String nombreTorneo = Utilidades.leerString("Escriba el nombre del torneo: ");
            guardarTorneo("TorneosGuardados", ganadorFinal,nombreTorneo);
            mostrarTorneos("TorneosGuardados");
        }
        // creamos un nombre de fichero aleatorio
    }


    public String getFase() {
        String fase = "";
        switch (lista.getNumParticipantes()) {
            case 1024:
                fase = "RONDA DE 1024";
                break;
            case 512:
                fase = "RONDA DE 512";
                break;
            case 256:
                fase = "RONDA DE 256";
                break;
            case 128:
                fase = "RONDA DE 128";
                break;
            case 64:
                fase = "RONDA DE 64";
                break;
            case 32:
                fase = "DIECISEISAVOS DE FINAL";
                break;
            case 16:
                fase = "OCTAVOS DE FINAL";
                break;
            case 8:
                fase = "CUARTOS DE FINAL";
                break;
            case 4:
                fase = "SEMI-FINALES";
                break;
            case 2:
                fase = "FINAL";
                break;
        }
        return fase;
    }


    public void simularEnfrentamientos() {
        Scanner sc = new Scanner(System.in);
        boolean noEnfrentado = false;
        Participante[] sinEnfrentar = new Participante[lista.getNumParticipantes()];
        for (int i = 0; i < lista.getNumParticipantes(); i++) {
            sinEnfrentar[i] = lista.getParticipante2(i);
        }
        System.out.print("|   Presione cualquier tecla y ENTER para comenzar: ");
        sc.next();
        sc.nextLine();
        System.out.println();
        int numParticipantes = lista.getNumParticipantes();
        for (int i = 0; i < numParticipantes / 2; i++) {
            int identAleatorio = -1;
            int pos = -1;
            while (!noEnfrentado) {
                identAleatorio = (int) (Math.random() * num_participantesInicial) + 1;
                for (int j = 0; j < sinEnfrentar.length && !noEnfrentado; j++) {
                    if (sinEnfrentar[j] != null && sinEnfrentar[j].getIdentificador() == identAleatorio) {
                        noEnfrentado = true;
                        pos = j;
                    }
                }
            }
            sinEnfrentar[pos] = null;
            noEnfrentado = false;
            int identAleatorio2 = -1;
            while (!noEnfrentado) {
                identAleatorio2 = (int) (Math.random() * num_participantesInicial) + 1;
                for (int j = 0; j < sinEnfrentar.length && !noEnfrentado; j++) {
                    if (sinEnfrentar[j] != null && sinEnfrentar[j].getIdentificador() == identAleatorio2) {
                        noEnfrentado = true;
                        pos = j;
                    }
                }
            }
            sinEnfrentar[pos] = null;
            noEnfrentado = false;
            Participante aleatorio1 = lista.getParticipante(identAleatorio);
            Participante aleatorio2 = lista.getParticipante(identAleatorio2);
            System.out.println("|  ---ENFRENTAMIENTO---");
            System.out.println("|  ---> " + aleatorio1.getNombre() + "  vs  " +
                    aleatorio2.getNombre() + " <---");
            System.out.print("|   Presione cualquier tecla y ENTER para comprobar el ganador: ");
            sc.next();
            sc.nextLine();
            System.out.println();
            Participante ganador = getGanador(aleatorio1, aleatorio2);
            System.out.println("GANADOR: * " + ganador.getNombre().toUpperCase() + " *");
            System.out.println();
            if (ganador == aleatorio1) {
                lista.eliminarParticipante(aleatorio2.getNombre());
            } else {
                lista.eliminarParticipante(aleatorio1.getNombre());
            }
            System.out.print("|   Presione cualquier tecla y ENTER para continuar: ");
            sc.next();
            sc.nextLine();
            System.out.println();
        }
    }

    public Participante getGanador(Participante uno, Participante dos) {
        int numAleatorio = (int) (Math.random() * 2);
        if (numAleatorio == 1) {
            return uno;
        } else {
            return dos;
        }
    }

    public void guardarTorneo(String fichero, String ganadorFinal, String nombreTorneo) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(new FileWriter(fichero + ".txt", true));
            salida.println(LocalDate.now() + "   " + nombreTorneo +  "   {Participantes: " + num_participantesInicial
                   + ", Ganador final: " + ganadorFinal + "}");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
    }

    public void mostrarTorneos(String fichero) {
        BufferedReader entrada = null;
        try {
            entrada = new BufferedReader(new FileReader(fichero + ".txt"));
            String cadena;
            while ((cadena = entrada.readLine()) != null) {
                System.out.println(cadena);
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
    }

}