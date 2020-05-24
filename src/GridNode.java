import eduni.simjava.Sim_event;
import eduni.simjava.Sim_system;
import gridsim.GridSim;
import gridsim.GridSimTags;
import gridsim.IO_data;
import gridsim.net.Link;
import gridsim.net.SimpleLink;

import java.util.ArrayList;

/**
 * Created by Oussama on 20/06/2019.
 */
public class GridNode extends GridSim
{
    public static final int PACKET_SIZE = 500;

    /* Tags */
    public static final int SEND_MSG = 1;
    public static final int CALC_DET = 2;
    public static final int PRINT_MAT = 3;

    /**
     * Reference to master node
     */
    private GridNode masterNode = null;
    private ArrayList<GridNode> slaveList;

    public GridNode(String s) throws Exception
    {
        super(s);
        this.slaveList = new ArrayList<>();
    }

    public GridNode(String s, GridNode masterNode) throws Exception
    {
        this(s);
        this.masterNode = masterNode;
    }


    /**
     * Creates a new Node Object
     *
     * @param name this entity name
     * @param link the physical link that connects this entity to its master
     * @throws Exception This happens when name is null or haven't
     *                   initialized GridSim.
     */
    public GridNode(String name, Link link) throws Exception
    {
        super(name, link);
        this.slaveList = new ArrayList<>();
    }

    /**
     * Get an event from the Node's event queue.
     *
     * @return {Sim_event} the received event, null if none.
     */
    public Sim_event receive()
    {
        Sim_event ev = new Sim_event();
        super.sim_get_next(ev);

        return ev;
    }

    /**
     * Send the msg Object to the selected slave Node, with the given tag
     * Outputs to the console a message.
     *
     * @param msg        {Object} Object to send
     * @param slaveIndex {int} the index of the slave to send to
     * @param tag
     */
    public void sendData(Object msg, int slaveIndex, int tag)
    {
        GridNode dist = this.slaveList.get(slaveIndex);

        IndexedMessage indexedMessage = new IndexedMessage(msg, slaveIndex);

        IO_data data = new IO_data(indexedMessage, PACKET_SIZE, GridSim.getEntityId(dist.getName()));

        System.out.println(getName() + ".body(): Sending " + msg +
                                   ", at time = " + GridSim.clock() + " to " +
                                   dist.getName());

        // sends through Output buffer of this entity
        super.send(dist.getName(), GridSimTags.SCHEDULE_NOW, tag, data);
    }

    /**
     * Send the msg data to master node with the given tag
     *
     * @param msg Object to send
     * @param tag Tag to attach
     */
    private void sendToMaster(Object msg, int index, int tag)
    {
        IO_data data = new IO_data(
                new IndexedMessage(msg, index),
                PACKET_SIZE,
                GridSim.getEntityId(masterNode.getName())
        );

        System.out.println(getName() + ".body(): Sending (" + msg + ", " + index + ")" +
                                   ", at time = " + GridSim.clock() + " to " +
                                   masterNode.getName());
        // sends through Output buffer of this entity
        super.send(masterNode.getName(), GridSimTags.SCHEDULE_NOW, tag, data);
    }

    public static IndexedMessage extractMessageFromEvent(Sim_event ev)
    {
        return (IndexedMessage) ((IO_data) (ev.get_data())).getData();
    }

    @Override
    /**
     * Instructions to be run by the node when the simulation starts.
     */
    public void body()
    {
        while (Sim_system.running()) {
            Sim_event ev = this.receive();

            switch (ev.get_tag()) {
                case GridNode.SEND_MSG:
                    System.out.println("Received " + extractMessageFromEvent(ev).obj);
                    break;
                case GridNode.CALC_DET:
                    computeDeterminant(ev);
                    break;
                case GridNode.PRINT_MAT:
                    printMatrix(ev);
                    break;
                case GridSimTags.END_OF_SIMULATION:
                    end();
                    break;
                default:
                    System.out.println(getName() + " has no idea what's the data is");
                    break;
            }
        }

    }

    private void printMatrix(Sim_event ev)
    {
        int[][] A = (int[][]) extractMessageFromEvent(ev).obj;
        System.out.println("Slave " + getName() + " has received a matrix : ");
        MatrixEngine.printMat(A);
    }

    private void computeDeterminant(Sim_event ev)
    {
        IndexedMessage msg = extractMessageFromEvent(ev);
        int[][] A = (int[][]) msg.obj;
        int index = msg.index;

        System.out.println("Slave " + getName() + " has received a matrix : ");
        int det = MatrixEngine.det(A, A.length);

        sendToMaster(det, index, GridNode.SEND_MSG);
    }

    /**
     * Terminates node entity and ports.
     * Ends all slave nodes first.
     */
    public void end()
    {
        for (GridNode slave : slaveList)
            slave.end();
        System.out.println(getName() + " has been stopped");
        // shut down I/O ports
        shutdownUserEntity();
        terminateIOEntities();
    }

    public void addSlave(GridNode slave)
    {
        this.slaveList.add(slave);
    }

    public GridNode getSlave(int i)
    {
        return slaveList.get(i);    /// TODO: handle exception
    }

    public int getSlaveCount()
    {
        return slaveList.size();
    }
}
