package src;
import java.util.ArrayList;


public class aStar {
    private ArrayList<Node> frontier = new ArrayList<>();
    private ArrayList<State> explored = new ArrayList<>();
    private State state;
    private Data data;
    private Node target;
    private int NUM = 1;
    private double t = 0;
    private Result result;

    public aStar(Data d, String path) {
        data = d;
        state = new State(data.getROBOT(), data.getBUTTER());
        t = System.nanoTime();
        ASTAR();
        t = System.nanoTime() - t;
        if(frontier.isEmpty() && !target.isGoal(data.getMAP())){
            result = new Result("cant pass the butter", null, -1, -1, t, path);
        }
        else {
            ArrayList<String> answer = new ArrayList<>();
            ArrayList<String> answerrev = new ArrayList<>();
            Node n = target;
            String solution = "";
            while (n != null){
                answer.add(n.getState().printMap(d.getMAP()));
                solution += " " + n.getOrder();
                n = n.getP();
            }
            char[] ans = solution.toCharArray();
            solution = "";
            for (int i = ans.length - 1; i >= 0; i--)
                solution = solution + ans[i];
            for (int i = answer.size()- 1; i >= 0; i--)
                answerrev.add(answer.get(i));
            result = new Result(solution, answerrev, target.getG(), target.getCutoff(), t, path);
        }
        data.output(result);
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

    public Result getResult() {
        return result;
    }
}