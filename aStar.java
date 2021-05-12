import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class aStar {
    private ArrayList<Node> frontier = new ArrayList<>();
    private ArrayList<State> explored = new ArrayList<>();
    private State state;
    private Data data;
    private Node target;
    private int NUM = 1;
    private double t = 0;
    private String path;

    public aStar(Data d, String path){
        data = d;
        state = new State(data.getROBOT(), data.getBUTTER());
        t = System.nanoTime();
        ASTAR();
        t = System.nanoTime() - t;
        this.path = "src/output/"+path+".Astar";
    }

    public void ASTAR(){
        Node root = new Node(null, 0, 0, "", state);
        frontier.add(root);
        while (! frontier.isEmpty()){
            Node node = minFrontier();
            frontier.remove(node);
            target = node;
            if(node.isGoal(data.getMAP())){
                return;
            }
            explored.add(node.getState());
            for(Node child : node.successor(data.getMAP())){
                NUM = NUM + 1;
                if(!checkExplored(child)){
                    int i = checkFrontier(child);
                    if(i == -1){
                        frontier.add(child);
                    }
                    else{
                        if(frontier.get(i).getF(data.getPURCHASER()) > child.getF(data.getPURCHASER())){
                            frontier.remove(i);
                            frontier.add(child);
                        }
                    }
                }
            }
        }
    }

    public boolean checkExplored(Node n){
        for(int i = 0; i < explored.size(); i++){
            if(n.getState().equals(explored.get(i)))
                return true;
        }
        return false;
    }

    public int checkFrontier(Node n){
        for(int i = 0; i < frontier.size(); i++){
            if(n.getState().equals(frontier.get(i).getState()))
                return i;
        }
        return -1;
    }

    public Node minFrontier(){
        int j = 0;
        int min = frontier.get(0).getF(data.getPURCHASER());
        for (int i = 0; i < frontier.size(); i++){
            if (min > frontier.get(i).getF(data.getPURCHASER())){
                j = i;
                min = frontier.get(i).getF(data.getPURCHASER());
            }
        }
        return frontier.get(j);
    }

    public void Print() throws IOException {
        File f1 = new File(path);
        if(!f1.exists()) {
            f1.createNewFile();
        }
        if(frontier.isEmpty() && !target.isGoal(data.getMAP())){
            System.out.println("can't pass the butter");
            FileWriter fileWritter = new FileWriter(f1.getName(),true);
            BufferedWriter bw = new BufferedWriter(fileWritter);
            bw.write("can't pass the butter\n");
            bw.close();
            return;
        }
        FileWriter fileWritter = new FileWriter(f1.getName(),true);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        String answer = "";
        Node n = target;
        int s = target.getG();
        while (n != null){
            answer = answer + n.getOrder();
            n = n.getP();
        }
        char[] revAnswer = answer.toCharArray();
        for (int i = revAnswer.length-1; i >= 0; i--){
            System.out.print(revAnswer[i]);
            bw.write(revAnswer[i]);
        }
        System.out.println();
        bw.write("\n");
        System.out.println("COST: "+ target.getG());
        bw.write("COST: "+ target.getG() + "\n");
        System.out.println("DEPTH: " + target.getCutoff());
        bw.write("DEPTH: " + target.getCutoff() + "\n");
        System.out.println("Number of expanded nodes: "+ explored.size());
        bw.write("Number of expanded nodes: "+ explored.size() + "\n");
        System.out.println("Number of generated nodes: "+ NUM);
        bw.write("Number of generated nodes: "+ NUM + "\n");
        System.out.println("TIME(s): " + t/1000000000);
        bw.write("TIME(s): " + t/1000000000 + "\n");
        bw.close();
    }

    public void PrintMap() throws IOException {
        File f1 = new File(path);
        if(!f1.exists()) {
            f1.createNewFile();
        }
        ArrayList<State> maps = new ArrayList<>();
        if(frontier.isEmpty() && !target.isGoal(data.getMAP())){
            return;
        }
        FileWriter fileWritter = new FileWriter(f1.getName(),true);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        Node n = target;
        while (n != null){
            maps.add(n.getState());
            n = n.getP();
        }
        for (int j = maps.size() - 1; j >= 0; j--) {
            String s = maps.get(j).printMap(data.getMAP());
            bw.write(s);
            System.out.println("################");
            bw.write("################\n");
        }
        bw.close();
    }
}