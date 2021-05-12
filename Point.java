import java.util.ArrayList;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point makePoint(int i, int j){
        return new Point(x + i, y + j);
    }

    public int distance(Point p){
        return Math.abs(p.getX() - x) + Math.abs(p.getY() - y);
    }

    public int minDistance(ArrayList<Point> ps, int d){
        int D = d;
        for(Point p: ps){
            if (D > distance(p))
                D = distance(p);
        }
        return D;
    }

    @Override
    public boolean equals(Object o) {
        return x == ((Point) o).getX() && y == ((Point) o).getY();
    }

    public boolean check(int i, int j, int X, int Y){
        if(x + i >= X || x + i < 0 || y  + j >= Y || y + j < 0)
            return false;
        return true;
    }

    public int arrayEquals(ArrayList<Point> ps){
        for (int i = 0; i < ps.size(); i++){
            if (ps.get(i).equals(this))
                return i;
        }
        return -1;
    }
    public void Print(){
        System.out.print("["+ x + ", "+ y+"]");
    }
}
