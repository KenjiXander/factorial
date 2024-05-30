import java.util.ArrayList;
import java.util.List;

public class Lista {

    List<Paqueteria> serviEntrega;

    public Lista(){
        serviEntrega = new ArrayList<Paqueteria>();
    }

    public List<Paqueteria> getServiEntrega() {
        return serviEntrega;
    }

    public void adicionarElemento(Paqueteria p) throws Exception{
        if(serviEntrega.isEmpty())
            serviEntrega.add(p);
        else{
            for(Paqueteria pa:serviEntrega)
                if(pa.getTracking() == p.getTracking())
                    throw new Exception("Paquete ya existe");
            serviEntrega.add(p);
        }
    }

    public String listarPaquetes(){
        String mensaje = "";
        for(Paqueteria p:serviEntrega)
            mensaje += p + "\n";
        return mensaje;
    }

    public int sumarTotalPaquetes(){
        return totalPaquetes(0);
    }

    private int totalPaquetes(int indice){
        if(serviEntrega.size() == indice)
            return 0;
        else{
            return 1 + totalPaquetes(indice + 1);
        }
    }

    public double sumarTotalPeso(){
        return totalPeso(0);
    }

    private double totalPeso(int indice){
        if(serviEntrega.size() == indice)
            return 0;
        else{
            return serviEntrega.get(indice).getPeso() + totalPeso(indice + 1);
        }

    }

    public double sumarTotalPesoCiudad(String ciudad){
        return totalPesoCiudad(0,ciudad);
    }

    private double totalPesoCiudad(int indice, String ciudad){
        if(serviEntrega.size() == indice)
            return 0;
        else if(serviEntrega.get(indice).getCiudadRecepcion().equals(ciudad))
            return serviEntrega.get(indice).getPeso() + totalPesoCiudad(indice + 1,ciudad);
        else
            return totalPesoCiudad(indice + 1,ciudad);
    }

    public Paqueteria buscarLinealPorTracking(int tracking) {
        for (Paqueteria pa : serviEntrega) {
            if (pa.getTracking() == tracking) {
                return pa;
            }
        }
        return null;
    }

    public int buscarBinarioPorTracking(int tracking){
        int inicio = 0;
        int fin = serviEntrega.size() - 1;
        int medio;
        int resultado = -1;
        while(inicio <= fin){
            medio = (inicio + fin)/2;
            if(serviEntrega.get(medio).getTracking() == tracking){
                resultado =medio;
                fin = inicio - 1;

            } else if (serviEntrega.get(medio).getTracking() < tracking) {
                inicio = medio + 1;
            } else{
                fin = medio - 1;
            }
        }
        return resultado;
    }

}
