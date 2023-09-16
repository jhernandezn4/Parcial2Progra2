package edu.pizza.especialidades;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

import java.util.ArrayList;
import java.util.List;

public class Hawaiana extends Pizza {
    private List<Topping> defaultToppings = new ArrayList<>();
    public  Hawaiana(String name, String salsa, Topping... toppings){


        super(name,
                toppings);


    }

    public List<Topping> getDefaultToppings() {
        return defaultToppings;
    }
}
