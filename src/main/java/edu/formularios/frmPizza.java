package edu.formularios;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class frmPizza {
    private JPanel jPanelPrincipal;
    private JComboBox comboBoxToppings;
    private JTextField txtPizza;
    private JButton btnAddIngrediente;
    private JLabel lblTotal;
    private JList lista1;
    private JButton btbPrepararPizza;
    private JComboBox comboBoxTipoPizza;
    private JList ListPrepare;
    private JRadioButton smallRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton bigRadioButton;



    private double total = 0;

    private List<Topping> ingredientes = new ArrayList<>();

    private List<String> especialidades = new ArrayList<>();

    private DefaultListModel modeloLista = new DefaultListModel();

    private DefaultListModel modeloPrepare = new DefaultListModel();
    public JPanel getjPanelPrincipal() {
        return jPanelPrincipal;
    }



    public frmPizza() {
        iniciarSistema();

        /**
         * SE AGREGARO QUE AL CAMBIAR DE TAMAÑO SE VUELVA A CALCULAR EL PRECIO
         */
        smallRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
        mediumRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
        bigRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
        /**
         * SE AGREGO UNA ACCION AL SELECCIONAR UNA ESPECIALIDAD
         */
        comboBoxTipoPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEspecialidades(String.valueOf(comboBoxTipoPizza.getSelectedItem()));
                txtPizza.setText(String.valueOf(comboBoxTipoPizza.getSelectedItem()));
            }
        });
        /**
         * SE AGREGO LA ACCION AL AGREGAR INGREDIENTES
         */
        btnAddIngrediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();

                modeloLista.addElement(ingrediente);
                lista1.setModel(modeloLista);
                calcularTotal();
            }
        });
        /**
         * SE AGREGO LA ACCCION AL BOTON PREPARAR
         */
        btbPrepararPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //refresh every time
                modeloPrepare.clear();

                if (txtPizza.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Ingrese el nombre de la pizza");
                    return;
                }
                if (modeloLista.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Ingrese al menos un ingrediente a la pizza");
                    return;
                }
                Pizza pizza = new Pizza(txtPizza.getText());
                Topping topi;
                for (int i= 0; i< lista1.getModel().getSize(); i++){
                    topi = (Topping) lista1.getModel().getElementAt(i);
                    pizza.addTopping(topi);
                }
                modeloPrepare.addElement("Preparing... "+pizza.getName());
                ListPrepare.setModel(modeloPrepare);
                modeloPrepare.addElement("Add Toppings... ");
                ListPrepare.setModel(modeloPrepare);

                for (int i= 0; i< lista1.getModel().getSize(); i++){
                    Topping t = (Topping) lista1.getModel().getElementAt(i);
                    modeloPrepare.addElement(t.getNombre());
                    ListPrepare.setModel(modeloPrepare);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException f) {
                        f.printStackTrace();
                    }

                }
                modeloPrepare.addElement("Pizza is ready.");
                ListPrepare.setModel(modeloPrepare);

            }
        });
        /**
         * SE ELIMINA UN INGREDIENTE AL HACER DOBLE CLICK
         */
        lista1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    modeloLista.remove(index);
                    calcularTotal();
                }
            }
        });
    }


    /**
     * SE INICIA EL SISTEMA Y SE CARGAN LOS INGREDIENTES Y LAS ESPECIALIDADES
     */

    private void iniciarSistema(){
        ingredientes.add(new Topping("Champiñones",3));
        ingredientes.add(new Topping("Mozzarella",4));
        ingredientes.add(new Topping("Cebolla",2));
        ingredientes.add(new Topping("Tomate",3));
        ingredientes.add(new Topping("Pepperoni",3));
        ingredientes.add(new Topping("Jamon",3));

        DefaultComboBoxModel model = new DefaultComboBoxModel(ingredientes.toArray());
        comboBoxToppings.setModel(model);

        especialidades.add("Al Gusto");
        especialidades.add("Hawaiana");
        especialidades.add("Italiana");
        especialidades.add("Extra Queso");
        especialidades.add("Napolitana");
        especialidades.add("Champiñones");

        DefaultComboBoxModel modelTipoPizza = new DefaultComboBoxModel(especialidades.toArray());
        comboBoxTipoPizza.setModel(modelTipoPizza);

    }

    /**
     * FUNCION PARA RECORRER LOS INGREDIENTES Y CALCULAR EL TOTAL EN BASE A LOS IGNREDIENTES
     * Y AL TAMAÑO DE LA PIZZA
     */
    private void calcularTotal(){
        double total =0;
        for (int i= 0; i< lista1.getModel().getSize(); i++){
            Topping t = (Topping) lista1.getModel().getElementAt(i);
            total+=t.getPrecio();
        }

        if (bigRadioButton.isSelected()){
            total=total+(total*0.30);
        } else if (mediumRadioButton.isSelected()) {
            total=total+(total*0.15);
        }
        lblTotal.setText(String.valueOf(total));
    }

    /***
     *
     * AL SELECCIONAR UNA ESPECIALIDAD SE CARGAN LOS INGREDIENTES DEPENDIENDO DE LA ESPECIALIDAD
     */
    private void cargarEspecialidades(String tipo){
        modeloLista.clear();
        Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();
        modeloLista.addElement(new Topping("Tomate",3));
        modeloLista.addElement(new Topping("Mozzarella",4));
        switch (tipo) {
            case "Hawaiana":
                modeloLista.addElement(new Topping("Piña",6));
                break;
            case "Italiana":
                modeloLista.addElement(new Topping("Salsa Especial",1));
                break;
            case "Extra Queso" :
                modeloLista.addElement(new Topping("Mozzarella",4));
                break;
            case "Napolitana" :
                modeloLista.addElement(new Topping("Albahaca",3));
                break;
            case "Champiñones" :
                modeloLista.addElement(new Topping("Champiñones",4));
                break;

            default:
                modeloLista.clear();
        }
        lista1.setModel(modeloLista);
        this.calcularTotal();

    }


}
