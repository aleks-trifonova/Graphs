//Aleksandra Trifonova
//Comp Sci 282 Monday Wednesday 2pm
//Project 4: BFS and DFS
//Turned in December 10, 2018
//Professor Lorentz
//In this program a breadth first search method is contained done using a queue
// as well as a depth first search
//algorithm

import java.io.*; // for BufferedReader
import java.util.*; // for StringTokenizer


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
        Set<Vertex_Node> set = new HashSet<>();
        set = output_bfs(set, s);
        Vertex_Node vertexListTemp = head;
        while (vertexListTemp != null) { // this will check for unconnected vertices
            if(!set.contains(vertexListTemp)){
                set = output_bfs(set, vertexListTemp);
            }
            vertexListTemp = vertexListTemp.getNext();
        }
    }

    public Set<Vertex_Node> output_bfs(Set<Vertex_Node> set, Vertex_Node s) {//performs the bfs
        Queue<Vertex_Node> queue = new LinkedList<>();
        set.add(s); // adds the value to the set
        queue.add(s); // adds it to the queue
        s.setDistance(0);
        s.setParent(new Vertex_Node(null, null));
        //BFS insert it into the queue. Print it out. Clear the queue then add the next value to the queue and repeat.
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
        return set;
    }

    public void output_dfs(Vertex_Node s) {
        Set<Vertex_Node> set = new HashSet<>();
        s.setDistance(0);
        s.setParent(new Vertex_Node(null, null));
        set = output_dfs(set, s);
        Vertex_Node vertexListTemp = head;
        //Insert its value into the set and rather using a stack it will process the values directly
        //as they come in
        while (vertexListTemp != null) {
            if(!set.contains(vertexListTemp)){
                vertexListTemp.setDistance(0);
                vertexListTemp.setParent(new Vertex_Node(null, null));
                set = output_dfs(set, vertexListTemp);
            }
            vertexListTemp = vertexListTemp.getNext();
        }
    }

    public Set<Vertex_Node> output_dfs(Set<Vertex_Node> set, Vertex_Node s) {
        set.add(s);//add value into the set and print it out
        System.out.println(s.getName() + ", " + s.getDistance() + ", " + s.getParent().getName());
        Edge_Node listTemp = s.getNbrList();
        //DFS will insert the values into the set and rather than putting them on the stack will process them
        //directly as they come in and print out the values in order.
        while (listTemp != null) {
            if (!set.contains(listTemp.getTarget())) {
                listTemp.getTarget().setParent(s);
                listTemp.getTarget().setDistance(s.getDistance() + 1);
                set.add(listTemp.getTarget());
                set = output_dfs(set, listTemp.getTarget());
            }
            listTemp = listTemp.getNext();
        }
        return set;
    }

    // If you implemented DFS then leave this method the way it is
// If you did not implement DFS then change the “true” to “false”
    public static boolean implementedDFS() {
        return true;
    }

    public static String myName() {
        return "Aleksandra Trifonova";
    }
}
