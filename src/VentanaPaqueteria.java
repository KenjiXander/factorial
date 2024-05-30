import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPaqueteria {
    private JPanel ventana;
    private JTabbedPane tabbedPane1;
    private JSpinner spinner1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField1;
    private JTextField textField2;
    private JButton agregarButton;
    private JButton totalButton;
    private JComboBox comboBox1;
    private JButton totalPesoPorCiudadButton;
    private JButton totalPesoButton;
    private JList list1;
    private JButton modificarButton;
    private JButton modificarEstadoButton;
    private JButton ordenarPorBurbujaButton;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextField textField3;
    private JButton ordenarPorInserciónPesoButton;
    private Lista paquetes = new Lista();
    private int selectedIndex = -1;


    public VentanaPaqueteria(){
        quemarDatos();
        llenarJList(paquetes);
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    paquetes.adicionarElemento(new Paqueteria(Integer.parseInt(spinner1.getValue().toString()),
                            Double.parseDouble(textField1.getText()),
                            comboBox3.getSelectedItem().toString(),
                            comboBox2.getSelectedItem().toString(),
                            textField2.getText()));
                    JOptionPane.showMessageDialog(null, "Paquete agregado");
                    limpiarDatos();
                    llenarJList(paquetes);
                    System.out.println(paquetes.listarPaquetes());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"El total de paquetes es: " +
                paquetes.sumarTotalPaquetes());
            }
        });
        totalPesoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"El peso total es: " +
                paquetes.sumarTotalPeso());
            }
        });
        totalPesoPorCiudadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"El peso total de los paquetes de la ciudad " +
                        comboBox1.getSelectedItem().toString() + " es: " +
                        paquetes.sumarTotalPesoCiudad(comboBox1.getSelectedItem().toString()));
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list1.getSelectedIndex()!=-1){
                    selectedIndex = list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getServiEntrega().get(selectedIndex);
                    spinner1.setValue(pa.getTracking());
                    textField1.setText("" + pa.getPeso());
                    comboBox2.setSelectedItem(pa.getCiudadRecepcion());
                    comboBox3.setSelectedItem(pa.getCiudadEntrega());
                    textField2.setText("" + pa.getCedulaReceptor());
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex != -1) {
                    Paqueteria pa = paquetes.getServiEntrega().get(selectedIndex);
                    pa.setTracking(Integer.parseInt(spinner1.getValue().toString()));
                    pa.setPeso(Double.parseDouble(textField1.getText()));
                    pa.setCiudadEntrega(comboBox3.getSelectedItem().toString());
                    pa.setCiudadRecepcion(comboBox2.getSelectedItem().toString());
                    pa.setCedulaReceptor(textField2.getText());
                    llenarJList(paquetes);
                    JOptionPane.showMessageDialog(null, "Paquete modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un paquete para modificar");
                }
            }
        });

        modificarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int trackingNumber = Integer.parseInt(textField3.getText());
                    Paqueteria pa = paquetes.buscarLinealPorTracking(trackingNumber);
                    if (pa == null) {
                        JOptionPane.showMessageDialog(null, "El número de tracking ingresado no existe, por favor ingrese un número válido.");
                    } else if ("Enviado".equals(pa.getEstado())) {
                        JOptionPane.showMessageDialog(null, "Este paquete ya ha sido enviado.");
                    } else {
                        pa.setEstado("Enviado");
                        JOptionPane.showMessageDialog(null, "Estado del paquete actualizado a Enviado.");
                        llenarJList(paquetes);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un número de tracking válido.");
                }
            }
        });

        ordenarPorBurbujaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarBurbuja();
                mostrarListas();
            }
        });

    }

    public void limpiarDatos(){
        spinner1.setValue(0);
        textField1.setText("");
        comboBox2.setSelectedIndex(0);
        comboBox3.setSelectedIndex(0);
        textField2.setText("");
        textField3.setText("");
    }

    public void quemarDatos(){
        try{
            paquetes.adicionarElemento(new Paqueteria(123,25,"Santo Domingo","Esmeraldas",
                    "1111111111"));
            paquetes.adicionarElemento(new Paqueteria(456,20,"Santo Domingo","Quito",
                    "2222222222"));
            paquetes.adicionarElemento(new Paqueteria(789,30,"Quito","Guayaquil",
                    "3333333333"));
        } catch (Exception e){

        }
    }

    public void llenarJList(Lista lista){
        DefaultListModel dlm = new DefaultListModel<>();
        for(Paqueteria pa:paquetes.getServiEntrega())
            dlm.addElement(pa);
        list1.setModel(dlm);
    }

    public void ordenarBurbuja(){

        List<Paqueteria> auxiliar = paquetes.getServiEntrega();

        int size = auxiliar.size();

        for(int i = 0; i < size - 1; i++){
            boolean swapped = false;
            for(int j = 0; j < size - i - 1; j++){
                if(auxiliar.get(j).getTracking() > auxiliar.get(j + 1).getTracking()){
                    Paqueteria temp = auxiliar.get(j);
                    auxiliar.set(j, auxiliar.get(j + 1));
                    auxiliar.set(j + 1, temp);

                    swapped = true;
                }
            }
            if(!swapped){
                break;
            }
        }
    }

    public void mostrarListas() {
        StringBuilder sb = new StringBuilder();
        for (Paqueteria pa : paquetes.getServiEntrega()) {
            sb.append(pa.toString());
            sb.append("\n");
        }
        textArea1.setText(sb.toString());
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPaqueteria");
        frame.setContentPane(new VentanaPaqueteria().ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1000,550);
        frame.setLocationRelativeTo(null);
    }
}
