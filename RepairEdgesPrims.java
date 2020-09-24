import java.util.*;

/*
There's an undirected connected graph with n nodes labeled 1..n. But some of the edges has been broken disconnecting the graph. Find the minimum cost to repair the edges so that all the nodes are once again accessible from each other.

Input:
n, an int representing the total number of nodes.
edges, a list of integer pair representing the nodes connected by an edge.
edgesToRepair, a list where each element is a triplet representing the pair of nodes between which an edge is currently broken and the cost of repairing that edge, respectively (e.g. [1, 2, 12] means to repair an edge between nodes 1 and 2, the cost would be 12).

TC : O(n^2)
SC : O(n^2)     // max no. of edges in an undirected graph with n vertices = n (n - 1) / 2
 */

public class RepairEdgesPrims {
    public static void main(String[] args)
    {
/*        int n = 5;
        int[][] edges = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}};
        int[][] edgesToRepair = {{1, 2, 12}, {3, 4, 30}, {1, 5, 8}};
 */
        int n = 6;
        int[][] edges = {{1, 2}, {2, 3}, {4, 5}, {5, 6}, {1, 5}, {2, 4}, {3, 4}};
        int[][] edgesToRepair = {{1, 5, 110}, {2, 4, 84}, {3, 4, 79}};

        // Creating map of edges and its cost to repair
        Map<List<Integer>, Integer> costOfEdgesMap = createCostOfEdgesMap(edges, edgesToRepair);
        System.out.println("cost List = " + costOfEdgesMap);

        int minRepairCost = findMinCost(n, costOfEdgesMap);
        System.out.println("Minimum Repair cost = " + minRepairCost);
    }

    public static Map<List<Integer>, Integer> createCostOfEdgesMap(int[][] edges, int[][] edgesToRepair)
    {
        Map<List<Integer>, Integer> costOfEdgesMap = new HashMap<>();

        // Default cost of each node is taken as 0
        for(int[] edge : edges)
            costOfEdgesMap.put(Arrays.asList(edge[0] - 1,edge[1] - 1), 0);

        for(int[] edgeToRepair : edgesToRepair)
        {
            List<Integer> toFrom = Arrays.asList(edgeToRepair[0] - 1,edgeToRepair[1] - 1);
            costOfEdgesMap.put(toFrom, edgeToRepair[2]);
        }

        return  costOfEdgesMap;
    }

    public static int findMinCost(int n, Map<List<Integer>, Integer> costOfEdgesMap)
    {
        int minFinalCost = 0;
        int[] finalCost = new int[n];
        boolean[] nodesInMST = new boolean[n];
        int[] parent = new int[n];

        // Starting with Prim's Algorithm implementation to find MST
        Arrays.fill(finalCost, Integer.MAX_VALUE);
        finalCost[0] = 0;
        parent[0] = -1;

        for(int i = 0; i < n - 1; i++)
        {
            // Applying greedy method to find vertex with min reaching cost
            int minVertex = findMinVertex(n, finalCost, nodesInMST);
            nodesInMST[minVertex] = true;       // new vertex included in MST

            // Relaxing all the adjacent vertices of minVertex which are not in MST
            for(int v = 0; v < n; v++)
            {
                List<Integer> edge = new ArrayList<>();
                if(costOfEdgesMap.containsKey(Arrays.asList(v, minVertex)))
                    edge = Arrays.asList(v, minVertex);
                else
                    edge = Arrays.asList(minVertex, v);

                if(costOfEdgesMap.containsKey(edge) && !nodesInMST[v] && costOfEdgesMap.get(edge) < finalCost[v])
                {
                    finalCost[v] = costOfEdgesMap.get(edge);
                    parent[v] = minVertex;
                }
            }
        }
        System.out.print("final cost = ");
        for(int cost : finalCost)
            System.out.print(cost + " ");
        System.out.println();

       System.out.print("nodes in MST = ");
        for(boolean inMST : nodesInMST)
            System.out.print(inMST + " ");
        System.out.println();

        System.out.print("parent = ");
        for(int par : parent)
            System.out.print(par + " ");
        System.out.println();

        for(int cost : finalCost)
            minFinalCost += cost;

        return minFinalCost;
    }

    public static int findMinVertex(int n, int[] costArr, boolean[] nodesInMST)
    {
        int minCost = Integer.MAX_VALUE;
        int minVertex = 0;
        for(int i = 0; i < n; i++)
        {
            if(!nodesInMST[i] && costArr[i] < minCost)
            {
                minCost = costArr[i];
                minVertex = i;
            }
        }
        return minVertex;
    }
}
