package edu.formularios;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class frmPizza {
    private JPanel jPanelPrincipal;
    private JComboBox comboBoxToppings;
    private JTextField txtPizza;
    private JButton btnAddIngrediente;
    private JLabel lblTotal;
    private JList lista1;
    private JButton btbPrepararPizza;

    private double total = 0;

    private List<Topping> ingredientes = new ArrayList<>();


    private DefaultListModel modeloLista = new DefaultListModel();
    public JPanel getjPanelPrincipal() {
        return jPanelPrincipal;
    }


    public frmPizza() {
        cargarToppings();

        btnAddIngrediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();

                modeloLista.addElement(ingrediente);
                lista1.setModel(modeloLista);
                total += ingrediente.getPrecio();
                lblTotal.setText(String.valueOf(total));
            }
        });
        btbPrepararPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtPizza.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Ingrese un ingrediente de la pizza");
                    return;
                }

                Pizza pizza = new Pizza(txtPizza.getText());
                Topping topi;
                for (int i= 0; i< lista1.getModel().getSize(); i++){
                    topi = (Topping) lista1.getModel().getElementAt(i);
                    pizza.addTopping(topi);
                }
                pizza.prepare();
            }
        });
    }

    //cargar topppings

    private void cargarToppings(){
        ingredientes.add(new Topping("Champiñones",3));
        ingredientes.add(new Topping("Mozzarella",4));
        ingredientes.add(new Topping("Cebolla",2));
        ingredientes.add(new Topping("Tomate",3));
        ingredientes.add(new Topping("Pepperoni",3));
        ingredientes.add(new Topping("Jamon",3));

        DefaultComboBoxModel model = new DefaultComboBoxModel(ingredientes.toArray());
        comboBoxToppings.setModel(model);

    }


}
