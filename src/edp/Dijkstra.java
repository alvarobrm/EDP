/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

/**
 *
 * @author alvar
 */
public class Dijkstra {

    // Dijkstra's algorithm to find shortest path from s to all other nodes

    public static int[] dijkstra(Graph G, int s) {
        final int[] dist = new int[G.getNodes()];  // shortest known distance from "s"
        final int[] pred = new int[G.getNodes()];  // preceeding node in path
        final boolean[] visited = new boolean[G.getNodes()]; // all false initially

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s] = 0;

        for (int i = 0; i < dist.length; i++) {
            final int next = minVertex(dist, visited);
            if (next ==-1)
                continue;
            visited[next] = true;

           // The shortest path to next is dist[next] and via pred[next].
            final int[] n = G.neighbors(next);
            for (int j = 0; j < n.length; j++) {
                final int v = n[j];
                final int d = dist[next] + G.getWeight(next, v);
                if (dist[v] > d) {
                    dist[v] = d;
                    pred[v] = next;
                }
            }
        }
        return pred;  // (ignore pred[s]==0!)
    }

    private static int minVertex(int[] dist, boolean[] v) {
        int x = Integer.MAX_VALUE;
        int y = -1;   // graph not connected, or no unvisited vertices
        for (int i = 0; i < dist.length; i++) {
            if (!v[i] && dist[i] < x) {
                y = i;
                x = dist[i];
            }
        }
        return y;
    }

    public static void printPath(Graph G, int[] pred, int s, int e) {
        final java.util.ArrayList path = new java.util.ArrayList();
        int x = e;
        while (x != s) {
            path.add(0, "camino : ");
            x = pred[x];
        }
        path.add(0,"no camino : ");
        System.out.println(path);
    }

}
