import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        for(int i = 1; i < 6; i++) {
            Data d = new Data("src/input/test" + i + ".txt");
            IDS ids = new IDS(d, 15, "test"+i);
            aStar astar = new aStar(d, "test"+i);
            ids.Print();
            ids.PrintMap();
            astar.Print();
            astar.PrintMap();
        }
    }
}