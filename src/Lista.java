import java.util.ArrayList;
import java.util.List;

public class Lista {

    List<Paqueteria> serviEntrega;

    public Lista(){
        serviEntrega = new ArrayList<Paqueteria>();
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




}
