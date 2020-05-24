package cours;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

/**
 * @input : "id (fils,...) couleur profondeur
 * @output :
 */
public class ProfondeurGraphe {


    public static class Map extends Mapper<LongWritable, Text, Text, Text> {

        private Text keyText = new Text("");
        private Text resultValue = new Text("");

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] node = value.toString().split("\\s");


            int n = node.length;
            String id = node[n - 4];
            String[] children = node[n - 3].replace("[", "").replace("]", "").split(",");
            String couleur = node[n - 2];
            int profondeur = Integer.parseInt(node[n - 1]);


            if (couleur.equals("GRIS")) {

                for (String child : children) {
                    if (!child.isEmpty()) {
                        keyText.set("" + child);
                        resultValue.set(child + " [] " + "GRIS" + " " + (profondeur + 1));
                        context.write(keyText, resultValue);
                    }
                }

                keyText.set("" + id);
                resultValue.set(id + " " + node[n - 3] + " NOIR " + profondeur);
                context.write(keyText, resultValue);
            }
            keyText.set("" + id);
            resultValue.set(value);
            context.write(keyText, resultValue);
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


            String children = "[]";
            int profondeur = -1;
            String couleur = "BLANC";

            for (Text value : values) {
                String[] node = value.toString().split("\\s");

                int n = node.length;

                if (node[n - 3].length() > children.length()) {
                    children = node[n - 3];
                }

                if (node[n - 2].contains("NOIR") || (node[2].contains("GRIS") && couleur.contains("BLANC"))) {
                    couleur = node[n - 2];
                }

                profondeur = Math.max(profondeur, Integer.parseInt(node[n - 1]));
            }

            context.write(key, new Text(key.toString() + " " + children + " " + couleur + " " + profondeur));

        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); // get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: cours.ProfondeurGraphe <in> <out>");
            System.exit(2);
        }

        // create a job with name "cours.ProfondeurGraphe"
        final String JOB_NAME = ProfondeurGraphe.class.getSimpleName();
        boolean done = false;

        Path inputPath = new Path(otherArgs[0] + "/" + JOB_NAME + ".txt");
        Path outputPath = new Path(otherArgs[1] + "/" + JOB_NAME);

        /**
         * Simuler X iterations
         */
        final int MAX_ITERATIONS = 2;
        int i;
        for (i = MAX_ITERATIONS; i >= 0; i--) {
            outputPath = new Path(otherArgs[1] + "/" + JOB_NAME + i);
            Job job = new Job(conf, JOB_NAME);
            job.setJarByClass(ProfondeurGraphe.class);
            job.setMapperClass(Map.class);
            job.setReducerClass(Reduce.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileSystem fs = FileSystem.get(new Configuration());
            FileInputFormat.addInputPath(job, inputPath);
            fs.delete(outputPath, true);
            FileOutputFormat.setOutputPath(job, outputPath);
            job.waitForCompletion(true);

            inputPath = new Path(otherArgs[1] + "/" + JOB_NAME + i + "/part-r-00000");

            String outputContent = readFile(inputPath, fs);

            if (outputContent.contains("BLANC") || outputContent.contains("GRIS")) {
                // continuer
            } else {
                break;
            }
        }

        /**
         * Dernier job pour avoir une sortie dans les dossiers output usuels :
         */
        inputPath = new Path(otherArgs[1] + "/" + JOB_NAME + (i + 1) + "/part-r-00000");
        outputPath = new Path(otherArgs[1] + "/" + JOB_NAME);

        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(ProfondeurGraphe.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileSystem fs = FileSystem.get(new Configuration());
        FileInputFormat.addInputPath(job, inputPath);
        fs.delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        //Wait till job completion
    }

    static String readFile(Path path, FileSystem fs) throws IOException {
        //Init input stream
        FSDataInputStream inputStream = fs.open(path);
//Classical input stream usage
        String out = IOUtils.toString(inputStream, "UTF-8");
        inputStream.close();
        return out;
    }
}
