import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ${} on 16/12/2017.
 */
public class ProgramRunner
{
    private static String consoleOutput = "";
    private String sourceCode;

    public ProgramRunner(String sourceCode)
    {
        this.sourceCode = sourceCode;
    }

    public String getConsoleOutput()
    {
        consoleOutput = "";
        compileAndRun();
        return consoleOutput;
    }

    private void compileAndRun()
    {
        String FILENAME = "Main";
        try
        {
            File f = FileManager.createTempFile(FILENAME+".java");
            FileManager.writeToFile(f, sourceCode);
            runProcess("javac "+FILENAME+".java");
            consoleOutput = readStream(runProcess("java "+FILENAME));

            FileManager.deleteFile(FILENAME+".java");
            FileManager.deleteFile(FILENAME+".class");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String readStream(InputStream ins) throws Exception
    {
        String line = null;
        String str = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null)
        {
            str += line;
        }

        return str;
    }

    private static InputStream runProcess(String command) throws Exception
    {
        Process pro = Runtime.getRuntime().exec(command);
        System.err.println(readStream(pro.getErrorStream()));
        return pro.getInputStream();
    }
}
