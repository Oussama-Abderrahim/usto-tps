package exo7;

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

public class RelationnalMapReduce4 {

    public static class Map
            extends Mapper<LongWritable, Text, Text, Text> {

        private Text keyText = new Text("");
        private Text resultValue = new Text("");

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // l'input est key i , j : aij
            String[] inputs = value.toString().split(" ");

            int A = Integer.parseInt(inputs[1]);
            int B = Integer.parseInt(inputs[2]);

            keyText.set(A + " " + B);
            resultValue.set("");
            context.write(keyText, resultValue);

        }
    }

    public static class Reduce
            extends Reducer<Text, Text, Text, Text> {

        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            context.write(key, new Text(""));
        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); // get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: exo7.RelationnalMapReduce4 <in> <out>");
            System.exit(2);
        }

        // create a job with name "exo7.RelationnalMapReduce"
        final String JOB_NAME = RelationnalMapReduce4.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(RelationnalMapReduce4.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        // uncomment the following line to add the Combiner
//        job.setCombinerClass(Reduce.class);

        // set output key type
        job.setOutputKeyClass(Text.class);
        // set output value type
        job.setOutputValueClass(Text.class);

        /* Setting Input/Output Paths */
        FileSystem fs = FileSystem.get(new Configuration());

        //set the HDFS path of the input data
        FileInputFormat.addInputPath(job, new Path(otherArgs[0] + "/" + JOB_NAME + ".txt"));
        // set the HDFS path for the output
        Path outputPath = new Path(otherArgs[1] + "/" + JOB_NAME);
        fs.delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        //Wait till job completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
