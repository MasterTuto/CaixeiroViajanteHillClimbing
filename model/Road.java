package model;

public class Road {
    private City from;
    private City to;
    private double length;

    public Road(City from, City to, double length) {
        this.from = from;
        this.to = to;
        this.length = length;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public double getLength() {
        return length;
    }

    public String toString() {
        return from.toString() + "-" + to.toString() + "(" + length + ")";
    }
}
