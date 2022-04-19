package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.City;
import model.Path;
import model.Road;

public class MainController {
    @FXML
    public Pane mainAnchor;

    @FXML
    public CheckBox cbCidade1;

    @FXML
    public CheckBox cbCidade2;

    @FXML
    public CheckBox cbCidade3;

    @FXML
    public CheckBox cbCidade4;

    @FXML
    public CheckBox cbCidade5;

    @FXML
    public CheckBox cbCidade6;

    @FXML
    public CheckBox cbCidade7;

    @FXML
    public CheckBox cbCidade8;

    @FXML
    public CheckBox cbCidade9;

    @FXML
    public CheckBox cbCidade10;

    @FXML
    public ArrayList<CheckBox> cbCidades;

    @FXML
    public ArrayList<ImageView> ivCidades;

    @FXML
    public Label lblPath;

    @FXML
    public Label lblCost;

    @FXML
    public Button startBtn;

    private ArrayList<City> allCities;

    private City initialCity;

    private HashMap<String, HashMap<String, Double>> allDistances;

    private ArrayList<Line> lines;

    private final int MAX_TRIES = 200;

    // create initialize method
    public void initialize() {
        // initialize allCities, anotherCities and map
        allCities = new ArrayList<City>();
        lines = new ArrayList<Line>();
        allDistances = new HashMap<String, HashMap<String, Double>>();


        String[] names = new String[] {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10"};

        double[][] rawDistances = new double[][] {{0, 30, 84, 56, -1, 70, -1, 75, -1, 80},
                {30, 0, 65, -1, -1, -1, 70, -1, 56, 40}, {84, 65, 0, 74, 52, 55, -1, 135, 143, 48},
                {56, -1, 74, 0, 135, -1, -1, 20, -1, -1}, {-1, -1, 52, 135, 0, 70, -1, 122, 98, 80},
                {70, -1, 55, -1, 70, 0, 63, -1, 82, 35}, {-1, 70, -1, -1, -1, 63, 0, -1, 120, 57},
                {75, -1, 135, 20, 122, -1, -1, 0, -1, -1},
                {-1, 56, 143, -1, 98, 82, 120, -1, 0, 20}, {80, 40, 48, -1, 80, 35, 57, -1, 20, 0}};


        // for loop from 0 to 10
        for (int i = 0; i < 10; i++) {
            // create new city
            double[] coords = getCoords(ivCidades.get(i));

            City city = new City(names[i], coords[0], coords[1], ivCidades.get(i));
            // add city to allCities
            allCities.add(city);

            allDistances.put(names[i], new HashMap<String, Double>());

            // for loop from 0 to 10
            for (int j = 0; j < 10; j++) {
                // add distance to allDistances
                allDistances.get(names[i]).put(names[j], rawDistances[i][j]);
            }

        }


        // for each cidade in ivCidade, set double click up
        for (ImageView ivCidade : ivCidades) {
            ivCidade.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // write alert to stdout
                    System.out.println("Double click on " + ivCidade.getId());
                    if (initialCity != null) {
                        initialCity.getImageView().getParent().getStyleClass().clear();
                    }

                    initialCity = allCities.get(ivCidades.indexOf(ivCidade));
                    initialCity.getImageView().getParent().getStyleClass().add("city-selected");
                }
            });
        }
    }

    private double[] getCoords(ImageView iv) {
        // return coords of parent of iv
        return new double[] {iv.getParent().getLayoutX() + iv.getFitWidth() / 5,
                iv.getParent().getLayoutY() + iv.getFitHeight() / 2};
    }

    public Color getRandomColor() {
        Random r = new Random();
        return Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    public void drawLine(City city1, City city2) {
        // create a new line
        Platform.runLater(() -> {
            Line line = new Line();
            // set start point
            line.setStartX(city1.getX());
            line.setStartY(city1.getY());
            // set end point
            line.setEndX(city2.getX());
            line.setEndY(city2.getY());
            // set line color
            line.setStroke(Color.BLACK);
            // line.setStroke(getRandomColor());
            // set line width
            line.setStrokeWidth(5);
            // add line to lines
            lines.add(line);
            // add line to mainAnchor
            mainAnchor.getChildren().add(line);
        });
    }

    public void drawLines(Path path) {
        // delete all lines in lines

        lines.forEach(line -> {
            Platform.runLater(() -> mainAnchor.getChildren().remove(line));
        });
        lines.clear();

        // append initialCity to the beginning of path
        // drawLine(initialCity, path.getFirstCity());
        // log initial and first
        System.out.println(
                "Initial: " + initialCity.getName() + " First: " + path.getFirstCity().getName());

        // for each road in path, draw a line
        for (Object r : path) {
            Road road = (Road) r;
            drawLine(road.getFrom(), road.getTo());
            // sleep for a quarter of a second
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        drawLine(path.getLastCity(), initialCity);
    }

    public double calcDistance(City city1, City city2) {
        // get the distance between city1 and city2
        double distance = allDistances.get(city1.getName()).get(city2.getName());
        // return distance
        return distance;
    }

    public Path randomSolution() {

        // create a new path
        Path path = new Path();
        // add initialCity to path

        ArrayList<City> cities = new ArrayList<City>(allCities);
        cities.remove(initialCity);

        Collections.shuffle(cities);
        cities.add(0, initialCity);


        City previousCity = null;
        int iterations = cities.size();
        while (!cities.isEmpty()) {
            System.out.println("Cities left: " + cities.size());
            if (previousCity == null) {
                previousCity = cities.get(0);
                cities.remove(0);
            }

            City nextCity = cities.get(0);
            cities.remove(0);

            double distance = calcDistance(previousCity, nextCity);
            if (distance == -1) {
                cities.add(nextCity);
                if (--iterations == 0) {
                    return randomSolution();
                }
                continue;
            }

            iterations = cities.size();
            // log cities names
            System.out.println(
                    "Adding road from " + previousCity.getName() + " to " + nextCity.getName());
            path.add(new Road(previousCity, nextCity, distance));
            previousCity = nextCity;

        }

        return path;
    }

    public ArrayList<Path> getNeighbours(Path solution) {
        // create an array list of paths
        ArrayList<Path> neighbours = new ArrayList<Path>();


        for (int i = 0; i < solution.size() + 1; i++) {
            for (int j = i + 1; j < solution.size() + 1; j++) {
                if (i != j && i != 0 && j != 0) {

                    City[] cities = solution.getCities();
                    City temp = cities[i];
                    cities[i] = cities[j];
                    cities[j] = temp;

                    boolean isContinous = true;
                    for (int k = 0; k < cities.length - 1; k++) {
                        if (allDistances.get(cities[k].getName())
                                .get(cities[k + 1].getName()) == -1) {
                            isContinous = false;
                            break;
                        }
                    }

                    if (isContinous) {
                        Path path = new Path(cities, allDistances);

                        neighbours.add(path);
                    }
                }
            }
        }
        // log neighbours siz
        System.out.println("Neighbours size: " + neighbours.size());


        return neighbours;
    }

    public Path bestNeighbour(ArrayList<Path> allNeighbours) {
        allNeighbours.sort((a, b) -> a.getLength() - b.getLength());

        return allNeighbours.get(0);
    }

    public void simulate() {
        // ignore if initialCity is null
        if (initialCity == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao iniciar");
                alert.setHeaderText("Não há cidade inicial selecionada");
                alert.setContentText("Selecione uma cidade inicial");
                alert.showAndWait();
            });
            startBtn.setDisable(false);
            return;
        }


        System.out.println("Generation random solution");
        Path solution = randomSolution();
        System.out.println("Generation random solution done");

        double solutionLength = solution.getLength();

        System.out.println("Generation neighbours");
        ArrayList<Path> neighbours = getNeighbours(solution);
        System.out.println("Generation neighbours done");

        System.out.println("Getting best neighbour");
        Path bestNeighbour = bestNeighbour(neighbours);
        System.out.println("Getting best neighbour done");



        for (int i = 0; i < MAX_TRIES && solutionLength > bestNeighbour.getLength(); i++) {
            solution = bestNeighbour;
            solutionLength = solution.getLength();


            System.out.println("Generation neighbours");
            neighbours = getNeighbours(solution);
            System.out.println("Generation neighbours done");

            System.out.println("Getting best neighbour");
            bestNeighbour = bestNeighbour(neighbours);

            System.out.println("Getting best neighbour done");

            Path p = bestNeighbour;

            Platform.runLater(() -> {
                lblCost.setText("" + p.getLength());
                lblPath.setText(p.toString());
            });
            drawLines(p);
            // sleep for half second
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Path sol = solution;
        Platform.runLater(() -> {
            lblCost.setText("" + sol.getLength());
            lblPath.setText(sol.toString());
            startBtn.setDisable(false);
        });
        drawLines(sol);

        System.out.println("------------------------------------------------------------\n");

        System.out.println("Solution length: " + solution.getLength());
        System.out.println("Solution: " + solution);
    }

    public void beginSimulation(ActionEvent evt) {
        startBtn.setDisable(true);

        Thread t = new Thread(this::simulate);
        t.start();
    }

    public void addCidadeNoTrajeto(ActionEvent evt) {

    }

}
