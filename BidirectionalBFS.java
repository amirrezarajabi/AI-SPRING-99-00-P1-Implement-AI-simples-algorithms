package src;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class BidirectionalBFS {
    private ArrayDeque<Node> frontier1 = new ArrayDeque<>(), frontier2 = new ArrayDeque<>();
    private ArrayList<State> explored1 = new ArrayList<>(), explored2 = new ArrayList<>();
    private State state;
    private Data data;
    private int NUM = 1;
    private double t = 0;
    private String path;
    private Pair<Node, Node> mres = null;
    public Result result = new Result();

    public BidirectionalBFS(Data d, String path){    	
        data = d;
        state = new State(data.getROBOT(), data.getBUTTER());
        this.path = path;
        init();
    }
    
    private void init() {
        t = System.nanoTime();
        run();
        t = System.nanoTime() - t;
        if(mres != null)
        	setres(mres.getFirst(), mres.getSecond());
        finish();
    }

    public void run(){
        
        ArrayList<String[]> map = data.getMAP();
        data.getPURCHASER();
        
        
        Node root = new Node(null, 0, 0, "", state);
        frontier1.add(root);
        
        for(Point p:data.getPURCHASER()) {
        	for(int i=-1;i<=1;i+=2) {
        		Point q = p.makePoint(i, 0);
                if(checkEmpty(q, map))
                	frontier2.add(new Node(null, 0, 0, "", new State(q, data.getPURCHASER())));
                
        		q = p.makePoint(0, i);
                if(checkEmpty(q, map))
                	frontier2.add(new Node(null, 0, 0, "", new State(q, data.getPURCHASER())));
        	}
        }
        

        while (!frontier1.isEmpty() && !frontier2.isEmpty()) {
        	mres = expandOneLayer(frontier1, frontier2, explored1, explored2, false);        	
        	if(mres != null) return;
        	
        	mres = expandOneLayer(frontier2, frontier1, explored2, explored1, true);        	
        	if(mres != null) return;
        }
    }
    
    private boolean checkEmpty(Point q, ArrayList<String []> map) {
    	return q.check(0, 0, map.size(), map.get(0).length) && !map.get(q.getX())[q.getY()].contains("x");
    }
    
    
    
    private Pair<Node, Node> expandOneLayer(ArrayDeque<Node> f1, ArrayDeque<Node> f2, ArrayList<State> e1, ArrayList<State> e2, boolean backward) {
    	
        int dep = f1.getFirst().getCutoff();
        
        while(!f1.isEmpty() && f1.getFirst().getCutoff() == dep) {
        	
            Node node = f1.removeFirst();
            
            e1.add(node.getState());
            
            for(Node child : backward ? node.predecessor(data.getMAP()):node.successor(data.getMAP())) {
                NUM = NUM + 1;
                if(checkExplored(child, e1) || checkFrontier(child, f1)!=null)
                	continue;
                
                Node res = checkFrontier(child, f2);
                
                if(res != null)
                	if(backward)
                		return new Pair<>(res, child);
                	else
                		return new Pair<>(child, res);
                
                f1.add(child);
            }
        }
    	return null;
    }

    private boolean checkExplored(Node n, ArrayList<State> ex){
        for(State e:ex)
            if(e.equals(n.getState()))
                return true;
        return false;
    }

    private Node checkFrontier(Node n, ArrayDeque<Node> fr){
    	for(Node nd:fr)
    		if(nd.getState().equals(n.getState()))
    			return nd;
        return null;
    }
    
    private void setres(Node a, Node b) {
    	ArrayList<Node> resNodes = new ArrayList<>();    	
    	resNodeFix(a, resNodes, true);
    	resNodeFix(b, resNodes, false);
    	
    	result.solution = "";
    	for(Node n:resNodes)
    		result.solution += n.getOrder() + " ";
    	result.solution = result.solution.toUpperCase();
    	
    	for(Node n:resNodes) if(n != b)
    		result.showMaps.add(n.getState().printMap(data.getMAP()));
    	result.depth = result.cost = a.getCutoff() - b.getCutoff();

    }
    
    private void finish() {
    	result.time = t;
    	result.path = path;
    	if(mres == null)
            result = new Result("cant pass the butter", null, -1, -1, t, path);
    	data.output(result);
    }
    
    private void resNodeFix(Node n, ArrayList<Node> rn, boolean back) {
    	if(!back) rn.add(n);
    	
    	if(n.getP() != null)
    		resNodeFix(n.getP(), rn, back);
    	
    	if(back) rn.add(n);
    }

}