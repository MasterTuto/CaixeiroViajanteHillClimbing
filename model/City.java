package model;

import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class City {
    private String name;
    private ImageView image;
    private double x, y;

    public City(String name, double x, double y, ImageView image) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.image = image;

        String papel1 = "Medico";
        String papel2 = "Enfermeiro";
        String papel3 = "Funcionario";
        String papel4 = "Funcionario";
        String papel5 = "Funcionario";
        String papel6 = "Funcionario";

        ArrayList<String> papeis = new ArrayList<String>();
        papeis.add(papel1);
        papeis.add(papel2);
        papeis.add(papel3);
        papeis.add(papel4);
        papeis.add(papel5);
        papeis.add(papel6);


    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ImageView getImageView() {
        return image;
    }

    public boolean equals(City city) {
        return this.name.equals(city.getName());
    }

    public String toString() {
        return name;
    }
}
