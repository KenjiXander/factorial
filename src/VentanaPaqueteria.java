import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Lista paquetes = new Lista();


    public VentanaPaqueteria(){
        quemarDatos();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    paquetes.adicionarElemento(new Paqueteria(Integer.parseInt(spinner1.getValue().toString()),
                            Double.parseDouble(textField1.getText()),
                            comboBox3.getSelectedItem().toString(),
                            comboBox2.getSelectedItem().toString(),
                            textField2.getText()));
                    llenarJList();
                    JOptionPane.showMessageDialog(null, "Paquete agregado");
                    limpiarDatos();
                    System.out.println(paquetes.listarPaquetes());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"El total de paquetese es: " +
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
                    int indice = list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getServiEntrega().get(indice);
                    spinner1.setValue(pa.getTracking());
                    textField1.setText("" + pa.getPeso());
                    comboBox2.setSelectedItem(pa.getCedulaReceptor());
                    comboBox3.setSelectedItem(pa.getCiudadEntrega());
                    textField2.setText("" + pa.getCedulaReceptor());
                }
            }
        });
    }

    public void limpiarDatos(){
        spinner1.setValue(0);
        textField1.setText("");
        comboBox2.setSelectedIndex(0);
        comboBox3.setSelectedIndex(0);
        textField2.setText("");
    }

    public void quemarDatos(){
        try{
            paquetes.adicionarElemento(new Paqueteria(123,25,"Santo Domingo","Guayaquil",
                    "1111111111"));
            paquetes.adicionarElemento(new Paqueteria(456,20,"Santo Domingo","Quito",
                    "1111111111"));
            paquetes.adicionarElemento(new Paqueteria(789,30,"Quito","Guayaquil",
                    "1111111111"));
        } catch (Exception e){

        }
    }

    public void llenarJList(Lista lista){
        DefaultListModel dlm = new DefaultListModel<>();
        for(Paqueteria pa:paquetes.getServiEntrega())
            dlm.addElement(pa);
        list1.setModel(dlm);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPaqueteria");
        frame.setContentPane(new VentanaPaqueteria().ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,550);
        frame.setLocationRelativeTo(null);
    }
}
