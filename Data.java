package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {

    private ArrayList<String[]> MAP = new ArrayList<>();
    private Point ROBOT;
    private ArrayList<Point> BUTTER = new ArrayList<>();
    private ArrayList<Point> PURCHASER = new ArrayList<>();

    public Data(String path) throws FileNotFoundException {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        String[] strings = data.split("\t");
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            strings = data.split("\t");
            MAP.add(strings);
        }
        myReader.close();
        for(int i =0; i < this.MAP.size(); i++){
            for(int j = 0; j < this.MAP.get(i).length; j++){
                String c = this.MAP.get(i)[j];
                if (c.contains("r")){
                    ROBOT = new Point(i, j);
                    MAP.get(i)[j] = MAP.get(i)[j].replace("r", "");
                }
                if (c.contains("b")){
                    BUTTER.add(new Point(i, j));
                    MAP.get(i)[j] = MAP.get(i)[j].replace("b", "");
                }
                if (c.contains("p")){
                    PURCHASER.add(new Point(i, j));
                }
            }
        }

    }

    public ArrayList<Point> getBUTTER() {
        return BUTTER;
    }

    public Point getROBOT() {
        return ROBOT;
    }

    public ArrayList<String[]> getMAP() {
        return MAP;
    }

    public ArrayList<Point> getPURCHASER() {
        return PURCHASER;
    }

    public void output(Result result) {
    	System.out.println("Saved data to file: (" + result.getPath() + ")");
    	System.out.println(result.output());
		try {
	        FileWriter myWriter = new FileWriter(result.getPath());
	        myWriter.write(result.output());
	        myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
