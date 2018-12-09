import java.io.*; // for BufferedReader
import java.util.*;


class Edge_Node {
    private Vertex_Node target;
    private Edge_Node next;

    public Edge_Node(Vertex_Node t, Edge_Node e) {
        target = t;
        next = e;
    }

    public Vertex_Node getTarget() {
        return target;
    }

    public Edge_Node getNext() {
        return next;
// no setters needed
    }
}

class Vertex_Node {
    private String name;
    private Edge_Node edge_head;
    private int distance;
    private Vertex_Node parent;
    private Vertex_Node next;

    public Vertex_Node(String s, Vertex_Node v) {
        name = s;
        next = v;
        distance = -1;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int d) {
        this.distance = d;
    }

    public Edge_Node getNbrList() {
        return edge_head;
    }

    public void setNbrList(Edge_Node e) {
        this.edge_head = e;
    }

    public Vertex_Node getNext() {
        return next;
    }

    public Vertex_Node getParent() {
        return parent;
    }

    public void setParent(Vertex_Node n) {
        this.parent = n;
    }
}

class Graph {
    private Vertex_Node head;
    private int size;

    public Graph() {
        head = null;
        size = 0;
    }

    // reset all distance values to -1
    public void clearDist() {
    }

    public Vertex_Node findVertex(String s) {
        Vertex_Node pt = head;
        while (pt != null && s.compareTo(pt.getName()) != 0)
            pt = pt.getNext();
        return pt;
    }

    public Vertex_Node input(String fileName) throws IOException {
        String inputLine, sourceName, targetName;
        Vertex_Node source = null, target;
        Edge_Node e;
        StringTokenizer input;
        BufferedReader inFile = new BufferedReader(new FileReader(fileName));
        inputLine = inFile.readLine();
        while (inputLine != null) {
            input = new StringTokenizer(inputLine);
            sourceName = input.nextToken();
            source = findVertex(sourceName);
            if (source == null) {
                head = new Vertex_Node(sourceName, head);
                source = head;
                size++;
            }
            if (input.hasMoreTokens()) {
                targetName = input.nextToken();
                target = findVertex(targetName);
                if (target == null) {
                    head = new Vertex_Node(targetName, head);
                    target = head;
                    size++;
                }
// put edge in one direction -- while checking for repeat
                e = source.getNbrList();
                while (e != null) {
                    if (e.getTarget() == target) {
                        System.out.print("Multiple edges from "
                                + source.getName() + " to ");
                        System.out.println(target.getName() + ".");
                        break;
                    }
                    e = e.getNext();
                }
                source.setNbrList(new Edge_Node(target, source.getNbrList()));
// put edge in the other direction
                e = target.getNbrList();
                while (e != null) {
                    if (e.getTarget() == source) {
                        System.out.print("Multiple edges from "
                                + target.getName() + " to ");
                        System.out.println(source.getName() + ".");
                        break;
                    }
                    e = e.getNext();
                }
                target.setNbrList(new Edge_Node(source, target.getNbrList()));
            }
            inputLine = inFile.readLine();
        }
        inFile.close();
        return source;
    }

    // You might find this helpful when debugging so that you
// can see what the graph actually looks like
    public void output() {
        Vertex_Node v = head;
        Edge_Node e;
        while (v != null) {
            System.out.print(v.getName() + ": ");
            e = v.getNbrList();
            while (e != null) {
                System.out.print(e.getTarget().getName() + " ");
                e = e.getNext();
            }
            System.out.println();
            v = v.getNext();
        }
    }

    public void output_bfs(Vertex_Node s) {
        Queue<Vertex_Node> queue = new LinkedList<>();
        Set<Vertex_Node> set = new HashSet<>();
        set.add(s);
        queue.add(s);
        s.setDistance(0);
        s.setParent(new Vertex_Node(null, null));
        while( head != null ) {
            while (!queue.isEmpty()) {
                Vertex_Node temp = queue.remove();
                System.out.println(temp.getName() + ", " + temp.getDistance() + ", " + temp.getParent().getName());
                Edge_Node listTemp = temp.getNbrList();
                while (listTemp != null) {
                    if (!set.contains(listTemp.getTarget())) {
                        listTemp.getTarget().setParent(temp);
                        listTemp.getTarget().setDistance(temp.getDistance() + 1);
                        set.add(listTemp.getTarget());
                        queue.add(listTemp.getTarget());
                    }
                    listTemp = listTemp.getNext();
                }
            }
        }
    }

    public void output_dfs(Vertex_Node s) {
        Set<Vertex_Node> set = new HashSet<>();
        set.add(s);
        s.setDistance(0);
        s.setParent(new Vertex_Node(null, null));
        set = output_dfs(set, s);
    }

    public Set<Vertex_Node> output_dfs(Set<Vertex_Node> set, Vertex_Node s) {
        System.out.println(s.getName() + ", " + s.getDistance() + ", " + s.getParent().getName());
        Edge_Node listTemp = s.getNbrList();
        while(s != null) {
            while (listTemp != null) {
                if (!set.contains(listTemp.getTarget())) {
                    listTemp.getTarget().setParent(s);
                    listTemp.getTarget().setDistance(s.getDistance() + 1);
                    set.add(listTemp.getTarget());
                    set = output_dfs(set, listTemp.getTarget());
                }
                listTemp = listTemp.getNext();
            }
        }
            return set;
    }

    // If you implemented DFS then leave this method the way it is
// If you did not implement DFS then change the “true” to “false”
    public static boolean implementedDFS() {
        return true;
    }

    public static String myName() {
        return "Santa Claus";
    }
}
