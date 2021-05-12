package src;
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
    private Result result;

    public IDS(Data data, int maxDepth, String p)  {
        this.data = data;
        state = new State(data.getROBOT(), data.getBUTTER());
        Node n = new  Node(null, 0, 0, "", state);
        target = n;
        goal = n.isGoal(data.getMAP());
        t = System.nanoTime();
        maxdepth = maxDepth;
        runDeepSearch();
        t = System.nanoTime() - t;
        path = p;
        if(goal == false){
            result = new Result("cant pass the butter", null, -1, -1, t, path);
        }
        else {
            ArrayList<String> answer = new ArrayList<>();
            ArrayList<String> answerrev = new ArrayList<>();
            Node nn = target;
            String solution = "";
            while (nn != null) {
                answer.add(nn.getState().printMap(data.getMAP()));
                solution += " " + nn.getOrder();
                nn = nn.getP();
            }
            char[] ans = solution.toCharArray();
            solution = "";
            for (int i = ans.length - 1; i >= 0; i--)
                solution = solution + ans[i];
            for (int i = answer.size() - 1; i >= 0; i--)
                answerrev.add(answer.get(i));
            result = new Result(solution, answerrev, target.getCutoff(), target.getCutoff(), t, path);
        }
        data.output(result);

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

    public Result getResult() {
        return result;
    }
}
