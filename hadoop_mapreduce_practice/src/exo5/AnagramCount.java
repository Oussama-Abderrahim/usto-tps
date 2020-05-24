package exo5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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

public class AnagramCount {

    public static class Map
            extends Mapper<LongWritable, Text, Text, Text> {

        private final static IntWritable one = new IntWritable(1); // type of output value

        private Text keyText = new Text("");
        private Text valueText = new Text("");

        //private String separatorsRegex = "[\.,\";\s]+";
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] words = value.toString().split("[-?!:,;\\s\\.\n\"\']+");
            for (String word : words) {
                char[] letters = word.toCharArray();
                Arrays.sort(letters);
                keyText.set(String.valueOf(letters));
                valueText.set(word);
                context.write(keyText, valueText);     // create a pair <keyword, 1>
            }
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        static int count = 0;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder words = new StringBuilder();
            for (Text txt : values) {
                words.append(" ").append(txt);
            }
            count++;
            context.write(new Text(String.valueOf(count)), new Text(words.toString())); // create a pair <keyword, number of occurences>
        }

    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); // get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: exo1.WordCount <in> <out>");
            System.exit(2);
        }

        // create a job with name "exo5.AnagramCount"
        final String JOB_NAME = AnagramCount.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(AnagramCount.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        // uncomment the following line to add the Combiner
        job.setCombinerClass(Reduce.class);


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
