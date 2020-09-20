import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* Given a 2D grid, each cell is either a zombie 1 or a human 0. Zombies can turn adjacent (up/down/left/right) human beings into zombies every hour. Find out how many hours does it take to infect all humans?

Time Complexity : O(m * n)
Space Complexity : O(m * n)
*/
public class ZombiesInfectHumans {
    public int time;
    public int[] coordinate;

    public ZombiesInfectHumans(int time, int[] coordinate)
    {
        this.time = time;
        this.coordinate = coordinate;
    }

    public static void main(String[] args)
    {
        List<List<Integer>> grid = Arrays.asList(Arrays.asList(0,1,1,0,1), Arrays.asList(0,1,0,1,0), Arrays.asList(0,0,0,0,1), Arrays.asList(0,1,0,0,0));

        System.out.println(computeMinHours(grid));
    }

    public static int computeMinHours(List<List<Integer>> grid)
    {
        int minHours = 0;
        Queue<ZombiesInfectHumans> zombiesQueue = new LinkedList<>();
        // Scanning the grid to find all the zombies and simultaneously storing in the queue
        for(int rows = 0; rows < grid.size(); rows++)
        {
            for(int cols = 0; cols < grid.get(0).size(); cols++)
            {
                if(grid.get(rows).get(cols) == 1)
                {
                    zombiesQueue.add(new ZombiesInfectHumans(0, new int[] {rows, cols}));
                }
            }
        }
        // Used to fetch adjacent coordinates
        int[][] adjDirections = {{0,1}, {0, -1}, {-1, 0}, {1, 0}};

        while(!zombiesQueue.isEmpty())
        {
            ZombiesInfectHumans zombie = zombiesQueue.remove();
            minHours = zombie.time;
            // Computing the adjacent coordinates and checking for validity
            for(int[] dir : adjDirections)
            {
                int adjXCoord = zombie.coordinate[0] + dir[0];
                int adjYCoord = zombie.coordinate[1] + dir[1];
                if(adjXCoord >= 0 && adjXCoord < grid.size() && adjYCoord >= 0 && adjYCoord < grid.get(0).size() && grid.get(adjXCoord).get(adjYCoord) == 0)
                {
                    // Infect the adjacent human & add the newly converted zombie to Queue
                    grid.get(adjXCoord).set(adjYCoord, 1);
                    zombiesQueue.add(new ZombiesInfectHumans(zombie.time + 1, new int[] {adjXCoord, adjYCoord}));
                }
            }
        }
        return minHours;
    }
}
