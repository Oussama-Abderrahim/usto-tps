import gridsim.GridSim;
import gridsim.ParameterException;
import gridsim.net.Link;
import gridsim.net.SimpleLink;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by Oussama on 20/06/2019.
 */

public class GridManager
{
    public static final double BAUD_RATE = 100; // bits/sec
    public static final double PROPAGATION_DELAY = 10;   // propagation delay in millisecond
    public static final int MTU = 50;          // max. transmission unit in byte

    public static GridManager instance = null;

    private GridNode master;

    public static void main(String[] args)
    {
        GridManager gridManager = GridManager.getInstance();

        gridManager.start();
        gridManager.end();
        System.exit(0);
    }

    private void end()
    {
        master.end();
    }

    public static GridManager getInstance()
    {
        if (instance == null)
            instance = new GridManager();
        return instance;
    }

    private GridManager()
    {
        try {
            initGridSim();

            int n = 3;

//            Scanner sc = new Scanner(System.in);
//
//            System.out.println("Entrer le nombre d'inconnus");
//            n = sc.nextInt();

            int[][] A = new int[][]{
                    {1, 3, 4},
                    {3, 5, -4},
                    {4, 7, -2}
            };

            int[] B = new int[]{
                    50, 2, 31
            };

//            System.out.println("A : ");

//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < n; j++) {
//                    A[i][j] = sc.nextInt();
//                }
//            }

//            System.out.println("B : ");
//            for (int i = 0; i < n; i++) {
//                B[i] = sc.nextInt();
//            }

            master = new GridNodeMaster("Master", A, B, n);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unwanted errors happen");
        }
    }

    /**
     * Create a new GridNode and attach it with a newly create Link to the given master node.
     * new node is added to master's slavelist.
     *
     * @param master master node of the newly created node
     * @return
     */
    public GridNode createSlave(GridNode master)
    {
        Link link = null;
        GridNode slave = null;
        try {
            link = new SimpleLink("link_" + master.getName() + "_" + master.getSlaveCount(), BAUD_RATE,
                                  PROPAGATION_DELAY, MTU
            );

            slave = new GridNode(master.getName() + "_slv" + "_" + master.getSlaveCount(), master);
            master.addSlave(slave);
            link.attach(master, slave);
        } catch (ParameterException e) {
            System.err.println("Invalid Parameters creating link " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error creating slave " + e.getMessage());
        }
        return slave;
    }

    private void start()
    {
        System.out.println("Starting simulation");
        GridSim.startGridSimulation();
    }

    private static void initGridSim()
    {
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = true;  // mean trace GridSim events

        // Initialize the GridSim package without any statistical
        // functionalities. Hence, no GridSim_stat.txt file is created.
        System.out.println("Initializing GridSim package");
        GridSim.init(1000, calendar, trace_flag);
    }

}
