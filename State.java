package src;

import java.util.ArrayList;
import java.util.Objects;

public class State {
    private Point ROBOT;
    private ArrayList<Point> BUTTER = new ArrayList<>();

    public State(Point r, ArrayList<Point> b){
        ROBOT = r;
        BUTTER = b;
    }

    public int getH(ArrayList<Point> PURCHASER){
        int h = 0;
        for (int i = 0; i < BUTTER.size(); i++)
            h = h + BUTTER.get(i).minDistance(PURCHASER, 1000000);
        return h;
    }

    public State child(int i, int j, ArrayList<String[]> map){
        Point r = ROBOT.makePoint(i, j);
        if(!r.check(0, 0, map.size(), map.get(0).length))
            return null;
        if (map.get(r.getX())[r.getY()].contains("x"))
            return null;
        int bc = r.arrayEquals(BUTTER);
        if (bc == -1)
            return new State(r, initial(0, 0, 0));
        if (bc != -1){
            if(map.get(r.getX())[r.getY()].contains("p"))
                return null;
            if (!r.check(i, j, map.size(), map.get(0).length))
                return null;
            if (map.get(r.getX() + i)[r.getY() + j].contains("x"))
                return null;
            if (BUTTER.get(bc).makePoint(i, j).arrayEquals(BUTTER) != -1)
                return null;
            return new State(r, initial(i, j, bc));
        }
        return null;
    }


    public ArrayList<Point> initial(int i, int j, int k)
    {
        ArrayList<Point> ps = new ArrayList<>();
        for (int l = 0; l < BUTTER.size(); l++){
            if(l == k)
                ps.add(BUTTER.get(l).makePoint(i, j));
            else
                ps.add(BUTTER.get(l).makePoint(0, 0));
        }
        return ps;
    }

    public int getG(ArrayList<String[]> map){
        if(map.get(ROBOT.getX())[ROBOT.getY()].contains("p"))
            return Integer.parseInt(map.get(ROBOT.getX())[ROBOT.getY()].replace("p", ""));
        return Integer.parseInt(map.get(ROBOT.getX())[ROBOT.getY()]);
    }

    public void printState(){
        ROBOT.Print();
        System.out.println();
        for (Point p: BUTTER)
            p.Print();
        System.out.println();
    }

    public boolean check(int i, int j){
        for (int k = 0; k < BUTTER.size(); k++){
            if (BUTTER.get(k).getX() == i && BUTTER.get(k).getY() == j)
                return true;
        }
        return false;
    }

    public String printMap(ArrayList<String[]> map) {
        String ss = "";
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                if (check(i, j)) {
                    ss = ss + map.get(i)[j] + "b ";
                }
                else if (ROBOT.getX() == i && ROBOT.getY() == j) {
                    ss = ss + map.get(i)[j] + "r ";
                }
                else {
                    ss = ss + map.get(i)[j] + " ";
                }
            }
            ss = ss + "\n";
        }
        return ss;
    }

    public Point getROBOT() {
        return ROBOT;
    }

    public ArrayList<Point> getBUTTER() {
        return BUTTER;
    }

    @Override
    public boolean equals(Object o) {
        if(!ROBOT.equals(((State) o).getROBOT()))
            return false;
        for (int i = 0; i < BUTTER.size(); i++){
            if(!BUTTER.get(i).equals(((State) o).getBUTTER().get(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ROBOT, BUTTER);
    }

    public Pair<State, State> father(int i, int j, ArrayList<String[]> map){
        Point r = ROBOT.makePoint(i, j);
        if (!r.check(0, 0, map.size(), map.get(0).length))
            return new Pair<>(null, null);
        if (map.get(r.getX())[r.getY()].contains("x"))
            return new Pair<>(null, null);
        if (r.arrayEquals(BUTTER) != -1)
            return new Pair<>(null, null);
        else{
            if(!ROBOT.check(0-i, 0-j, map.size(), map.get(0).length))
                return new Pair<>(new State(r, initial(0,0,0)), null);
            if (map.get(ROBOT.getX()-i)[ROBOT.getY()-j].contains("x"))
                return new Pair<>(new State(r, initial(0,0,0)), null);
            if (ROBOT.makePoint(0-i, 0-j).arrayEquals(BUTTER) == -1)
                return new Pair<>(new State(r, initial(0,0,0)), null);
            else
                return new Pair<>(new State(r, initial(0,0,0)), new State(r, initial(i, j, ROBOT.makePoint(0-i, 0-j).arrayEquals(BUTTER))));
        }
    }
}
