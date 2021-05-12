package src;

import java.io.IOException;


public class Main {
    @SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
    	for(int i=1;i<6;i++) {
    		int number = i;
            Data d = new Data("src/input/test" + number + ".txt");
            aStar algorithm1 = new aStar(d, "src/output/test" + number + "_ASTAR.txt");
            IDS algorithm2 = new IDS(d, 20,"src/output/test" + number + "_IDS.txt");
            BidirectionalBFS algorithm3 = new BidirectionalBFS(d,"src/output/test" + number + "_BIBFS.txt");
    	}
    }
}