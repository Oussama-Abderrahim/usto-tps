import eduni.simjava.Sim_event;
import gridsim.IO_data;

/**
 * Created by Oussama on 22/06/2019.
 */
public class GridNodeMaster extends GridNode
{
    private final int[][] A;
    private final int[] B;
    private final int n;

    public GridNodeMaster(String s, int[][] A, int B[], int n) throws Exception
    {
        super(s);
        this.A = A;
        this.B = B;
        this.n = n;
    }

    @Override
    public void body()
    {
        for (int i = 0; i < n; i++) {
            GridManager.getInstance().createSlave(this);
        }

        super.gridSimHold(1);

//        SOLVING
        int detA = MatrixEngine.det(A, n);

        int[] X = new int[n];

        // Sending sub matrices to slave to solve
        for (int i = 0; i < n; i++) {
            sendData(MatrixEngine.generateMatrixB(A, n, i, B), i, GridNode.CALC_DET);
        }

        // Receiving determinants of sub matrices
        for (int i = 0; i < n; i++) {
            Sim_event obj = receive();
            int detI = (int) ((IO_data) (obj.get_data())).getData();

            X[i] = detI/detA;
        }
        System.out.println("Resultat : ");
        MatrixEngine.printArr(X, n);

//
//        for (int i = 0; i < n; i++) {
//            sendData(A, i, GridNode.CALC_DET);
//        }

    }
}