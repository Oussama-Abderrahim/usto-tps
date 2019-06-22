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
    private static final int SEND_MSG = 1;

    private ArrayList<GridNode> slaveList;

    public GridNode(String s) throws Exception
    {
        super(s);
        this.slaveList = new ArrayList<>();
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

    public Object receive()
    {
        Object obj = null;
        obj = super.receiveEventObject();
        return obj;
    }

    public void sendData(Object msg, int slaveIndex)
    {
        GridNode dist = this.slaveList.get(slaveIndex);

        IO_data data = new IO_data(msg, PACKET_SIZE, GridSim.getEntityId(dist.getName()));
        System.out.println(getName() + ".body(): Sending " + msg +
                                   ", at time = " + GridSim.clock() + " to " +
                                   dist.getName());

        // sends through Output buffer of this entity
        super.send(dist.getName(), GridSimTags.SCHEDULE_NOW, GridNode.SEND_MSG, data);
    }

    @Override
    public void body()
    {
        IO_data obj = (IO_data) this.receive();
        if (obj != null) {
            System.out.println("Received " + obj.getData().toString());
        }

    }

    public void end()
    {
        for (GridNode slave : slaveList)
            slave.end();
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
