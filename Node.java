import java.util.ArrayList;

public class Node {

    private State state;
    private String order;
    private int g;
    private Node p;
    private int cutoff;

    public Node(Node p, int g, int c, String order, State s)
    {
        state = s;
        this.order = order;
        this.g = g;
        this.p = p;
        cutoff = c;
    }

    public State getState() {
        return state;
    }

    public int getCutoff() {
        return cutoff;
    }

    public int getG() {
        return g;
    }

    public Node getP() {
        return p;
    }

    public String getOrder() {
        return order;
    }

    public ArrayList<Node> successor(ArrayList<String[]> map) {
        ArrayList<Node> children = new ArrayList<>();
        State u = state.child(-1, 0, map);
        State d = state.child(1, 0, map);
        State l = state.child(0, -1, map);
        State r = state.child(0, 1, map);
        if (p != null){
            if(u != null)
                if (u.equals(p.getState()))
                    u = null;
            if(d != null)
                if (d.equals(p.getState()))
                    d = null;
            if (r != null)
                if (r.equals(p.getState()))
                    r = null;
            if(l != null)
                if (l.equals(p.getState()))
                    l = null;
        }
        if(u != null)
            children.add(new Node(this, g + u.getG(map), cutoff + 1, "U", u));
        if(d != null)
            children.add(new Node(this, g + d.getG(map), cutoff + 1, "D", d));
        if(r != null)
            children.add(new Node(this, g + r.getG(map), cutoff + 1, "R", r));
        if(l != null)
            children.add(new Node(this, g + l.getG(map), cutoff + 1, "L", l));
        return children;
    }

    public boolean isGoal(ArrayList<String[]> Map){
        for(int i = 0; i < state.getBUTTER().size(); i++){
            if(!Map.get(state.getBUTTER().get(i).getX())[state.getBUTTER().get(i).getY()].contains("p"))
                return false;
        }
        return true;
    }

    public int getF(ArrayList<Point> ps){
        return getG() + state.getH(ps);
    }

   public ArrayList<Node> predecessor(ArrayList<String[]> map){
        ArrayList<Node> children = new ArrayList<>();
        State u0 = state.father(-1,0, map).getFirst();
        State u1 = state.father(-1,0, map).getSecond();
        State d0 = state.father(1,0, map).getFirst();
        State d1 = state.father(1,0, map).getSecond();
        State r0 = state.father(0,+1, map).getFirst();
        State r1 = state.father(0,+1, map).getSecond();
        State l0 = state.father(0,-1, map).getFirst();
        State l1 = state.father(0,-1, map).getSecond();
       if (p != null){
           if(u0 != null)
               if (u0.equals(p.getState()))
                   u0 = null;
           if(d0 != null)
               if (d0.equals(p.getState()))
                   d0 = null;
           if (r0 != null)
               if (r0.equals(p.getState()))
                   r0 = null;
           if(l0 != null)
               if (l0.equals(p.getState()))
                   l0 = null;
           if(u1 != null)
               if (u1.equals(p.getState()))
                   u1 = null;
           if(d1 != null)
               if (d1.equals(p.getState()))
                   d1 = null;
           if (r1 != null)
               if (r1.equals(p.getState()))
                   r1 = null;
           if(l1 != null)
               if (l1.equals(p.getState()))
                   l1 = null;
       }
        if(u0 != null)
            children.add(new Node(this, g - u0.getG(map), cutoff - 1, "D", u0));
        if(d0 != null)
            children.add(new Node(this, g - u0.getG(map), cutoff - 1, "U", d0));
        if(r0 != null)
            children.add(new Node(this, g - r0.getG(map), cutoff - 1, "L", r0));
        if(l0 != null)
            children.add(new Node(this, g - l0.getG(map), cutoff - 1, "R", l0));
        if(u1 != null)
            children.add(new Node(this, g + u1.getG(map), cutoff - 1, "D", u1));
        if(d1 != null)
            children.add(new Node(this, g + d1.getG(map), cutoff - 1, "u", d1));
        if(r1 != null)
            children.add(new Node(this, g + r1.getG(map), cutoff - 1, "L", r1));
        if(l1 != null)
            children.add(new Node(this, g + l1.getG(map), cutoff - 1, "R", l1));
        return children;
    }
}
