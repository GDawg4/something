import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Functions {
    private String nombre;
    private List funcion;
    private List<String> parametros;


    public Functions(String nombre, List<String> funcion, List<String> parametros) {
        this.nombre = nombre;
        this.funcion = funcion;
        this.parametros = parametros;
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "nombre='" + nombre + '\'' +
                ", funcion='" + funcion.toString() + '\'' +
                ", parametros=" + parametros.toString() +
                '}';
    }

    public List process(ArrayList<Object> values){
        List<Object> canBeProcessed = funcion;
        int j = 0;
        for (Object i: parametros){
            Collections.replaceAll(canBeProcessed, i, values.get(j));
            j ++;
        }
        return canBeProcessed;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getFuncion() {
        return funcion;
    }

    public void setFuncion(List funcion) {
        this.funcion = funcion;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }
}

