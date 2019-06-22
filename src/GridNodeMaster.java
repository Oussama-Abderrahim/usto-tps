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

        super.gridSimHold(5);

        int[] C = MatrixEngine.solve(A, B, n);

        System.out.println("Resultat : ");
        MatrixEngine.printArr(C, n);

        for (int i = 0; i < n; i++) {
            sendData("Finished ", i);
        }
    }
}