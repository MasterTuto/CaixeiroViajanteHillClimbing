package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;


public class Path implements Iterable {
    private Stack<Road> pathSequence;

    public Path() {
        pathSequence = new Stack<Road>();
    }

    public Path(Path path) {
        this.pathSequence = new Stack<Road>();
        pathSequence.addAll(path.pathSequence);
    }

    public Path(City[] cities, HashMap<String, HashMap<String, Double>> distances) {
        pathSequence = new Stack<Road>();

        for (int i = 1; i < cities.length; i++) {
            City c1 = cities[i - 1];
            City c2 = cities[i];

            Road road = new Road(c1, c2, distances.get(c1.getName()).get(c2.getName()));
            pathSequence.add(road);
        }
    }

    public void add(Road path) {
        pathSequence.push(path);
    }

    public Road getLast() {
        return pathSequence.peek();
    }

    public void removeLast() {
        pathSequence.pop();
    }

    public boolean isEmpty() {
        return pathSequence.empty();
    }

    public int size() {
        return pathSequence.size();
    }

    public Road pop() {
        return pathSequence.pop();
    }

    public Stack<Road> getPathSequence() {
        return pathSequence;
    }

    public void setPathSequence(Stack<Road> pathSequence) {
        this.pathSequence = pathSequence;
    }

    public int getLength() {
        int length = 0;
        for (Road path : pathSequence) {
            length += path.getLength();
        }
        return length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Road path : pathSequence) {
            sb.append(path.toString() + ", ");
        }
        return sb.toString();
    }


    public City getLastCity() {
        return pathSequence.peek().getTo();
    }

    public City getFirstCity() {
        return pathSequence.peek().getFrom();
    }

    public void purge() {
        pathSequence.clear();
    }

    public City[] getCities() {
        City[] cities = new City[pathSequence.size() + 1];
        int i = 0;

        for (Road path : pathSequence) {
            cities[i] = path.getFrom();
            i++;
        }

        cities[pathSequence.size()] = pathSequence.lastElement().getTo();

        return cities;
    }

    public Road[] getRoads() {
        Road[] roads = new Road[pathSequence.size()];
        int i = 0;
        for (Road path : pathSequence) {
            roads[i] = path;
            i++;
        }
        return roads;
    }

    public Road getLastRoad() {
        return pathSequence.peek();
    }

    // implement iterable methods
    @Override
    public Iterator<Road> iterator() {
        return new PathIterator();
    }

    private class PathIterator implements Iterator<Road> {
        private int index;

        public PathIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < pathSequence.size();
        }

        @Override
        public Road next() {
            return pathSequence.get(index++);
        }
    }
}
