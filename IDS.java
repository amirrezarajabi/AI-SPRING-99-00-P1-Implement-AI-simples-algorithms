import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class IDS {
    private boolean goal = false;
    private Node target;
    private State state;
    private Data data;
    private int num = 0;
    private int NUM = 0;
    private double t = 0;
    private int maxdepth;
    private String path = "";

    public IDS(Data data, int maxDepth, String p){
        this.data = data;
        state = new State(data.getROBOT(), data.getBUTTER());
        Node n = new  Node(null, 0, 0, "", state);
        target = n;
        goal = n.isGoal(data.getMAP());
        t = System.nanoTime();
        maxdepth = maxDepth;
        runDeepSearch();
        t = System.nanoTime() - t;
        path ="src/output/"+ p+".IDS";
    }

    public void runDeepSearch(){
        int depth = 0;
        while (!goal && depth < maxdepth){
            Node root = new Node(null, 0, 0, "", state);
            DFS(root, depth);
            depth++;
        }
    }

    public void DFS(Node root, int depth){
        num = 1;
        NUM = 1;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            Node node = stack.pop();
            if(node.isGoal(data.getMAP())){
                goal = true;
                target = node;
                return;
            }
            int c = node.getCutoff();
            if(c < depth){
                num = num + 1;
                for (Node n: node.successor(data.getMAP())){
                    NUM = NUM + 1;
                    if (n.isGoal(data.getMAP())){
                        goal = true;
                        target = n;
                        return;
                    }
                    stack.push(n);
                }
            }
        }
    }
    public void Print() throws IOException {
        File f1 = new File(path);
        if(!f1.exists()) {
            f1.createNewFile();
        }
        if(goal == false){
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
        System.out.println("Number of expanded nodes: "+ num);
        bw.write("Number of expanded nodes: "+ num + "\n");
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
        if(goal == false){
            return;
        }
        ArrayList<State> maps = new ArrayList<>();
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
