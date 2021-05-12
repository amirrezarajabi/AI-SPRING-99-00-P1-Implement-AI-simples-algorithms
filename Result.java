import java.util.ArrayList;

public class Result {

    public String solution = "";
    public ArrayList<String> showMaps = new ArrayList<>();
    public int cost = 0;
    public int depth = 0;
    public double time = 0.0;
    public String path = "";
    public int expanded = 0;
    public int generated = 0;

    public Result(String solution, ArrayList<String> showMaps, int cost, int depth, double time, String path, int expanded, int generated){
        this.solution = solution;
        this.cost = cost;
        this.depth = depth;
        this.time = time;
        this.showMaps = showMaps;
        this.path = path;
        this.expanded = expanded;
        this.generated = generated;
    }

    public Result() {}

    public String output(){
        String output = solution + "\n";
        if(cost != -1)
            output += "Cost: " + cost + "\n";
        if(depth != -1)
            output += "Depth: " + depth + "\n";
        output += "#Expanded: " + expanded + "\n";
        output += "#Generated: " + generated + "\n";
        output += "Time(s): " + time/1000000000.0 + "\n\n################\n";
        if (showMaps != null){
            for (int i = 0; i < showMaps.size() ; i++)
                output += "\n" + showMaps.get(i) + "\n################\n";
        }
        return output;
    }

    public String getPath() {
        return path;
    }
}
