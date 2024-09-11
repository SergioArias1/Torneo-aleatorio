public class Participante {

    private String nombre;

    private String avgshjdk;

    private final int identificador;

    public Participante (String nombre, int identificador){
        this.nombre = nombre;
        this.identificador = identificador;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    //sdsdfdssdf


    public int getIdentificador(){
        return identificador;
    }

    public String toString(){
        return "{ Nombre: " + this.nombre + " (" + this.identificador + ") }";
    }




}
