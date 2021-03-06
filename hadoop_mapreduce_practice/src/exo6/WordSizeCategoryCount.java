package exo6;

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

public class WordSizeCategoryCount {

    public static class Map
            extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1); // type of output value

        private Text keyText = new Text("");

        //private String separatorsRegex = "[\.,\";\s]+";
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] words = value.toString().split("[-?!:,;\\s\\.\n\"\']+");
            for (String word : words) {
                switch (word.length()) {
                    case 0:
                        continue;
                    case 1:
                        keyText.set("1");
                        break;
                    case 2:
                    case 3:
                    case 4:
                        keyText.set("2-4");
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        keyText.set("5-9");
                        break;
                    default: // > 10
                        keyText.set("+10");
                }
                context.write(keyText, one);     // create a pair <keyword, 1>
            }
        }
    }

    public static class Reduce
            extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0; // initialize the sum for each keyword
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);

            context.write(key, result); // create a pair <keyword, number of occurences>
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

        // create a job with name "wordcount"
        final String JOB_NAME = WordSizeCategoryCount.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(WordSizeCategoryCount.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        // uncomment the following line to add the Combiner
        job.setCombinerClass(Reduce.class);


        // set output key type
        job.setOutputKeyClass(Text.class);
        // set output value type
        job.setOutputValueClass(IntWritable.class);

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
