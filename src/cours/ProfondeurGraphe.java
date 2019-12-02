package cours;

import org.apache.hadoop.conf.Configuration;
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

            String[] node = value.toString().split(" ");

            String id = node[0];
            String[] children = node[1].substring(1, node[1].length() - 1).split(",");
            String couleur = node[2];
            int profondeur = Integer.parseInt(node[3]);

            if (couleur.equals("GRIS")) {

                for (String child : children) {
                    keyText.set(child);
                    resultValue.set("() " + "GRIS" + " " + profondeur + 1);
                    context.write(keyText, resultValue);
                }

                keyText.set(id);
                resultValue.set(node[1] + " NOIR " + profondeur);
                context.write(keyText, resultValue);
            } else {
                keyText.set(id);
                resultValue.set(node[1] + " " + couleur + " " + profondeur);
                context.write(keyText, resultValue);
            }
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            Text[] valuesArray = StreamSupport.stream(values.spliterator(), false).toArray(Text[]::new);

            String children = "()";
            int profondeur = -1;
            String couleur = "BLANC";

            for (Text value : valuesArray) {
                String[] node = value.toString().split(" ");

                if (node[0].length() > children.length()) children = node[0];

                if (node[1].equals("NOIR") || (node[1].equals("GRIS") && couleur.equals("BLANC"))) couleur = node[1];

                profondeur = Math.max(profondeur, Integer.parseInt(node[2]));
            }

            context.write(key, new Text("" + children + " " + couleur + " " + profondeur));

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

        for(int i = 5; i > 0; i--)
        {
            Path outputPath = new Path(otherArgs[1] + "/" + JOB_NAME + i);
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

            inputPath = outputPath;
        }

        //Wait till job completion
    }
}
