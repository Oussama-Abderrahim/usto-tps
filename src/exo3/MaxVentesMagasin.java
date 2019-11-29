package exo3;

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

public class MaxVentesMagasin {

    public static class Map
            extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1); // type of output value

        private Text keyText = new Text("");
        private IntWritable valueText = new IntWritable(0);

        //private String separatorsRegex = "[\.,\";\s]+";
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] words = value.toString().split(" \\| ");

//            String date = words[0];
//            String temps = words[1];
            String magasin = words[2];
//            String produit = words[3];
//            String count = words[4];
            String paiement = words[5];

            keyText.set(String.valueOf(magasin));
            valueText.set(Integer.parseInt(paiement));
            context.write(keyText, valueText);     // create a pair <keyword, 1>

        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int max = 0; // initialize the sum for each keyword
            for (IntWritable val : values) {
                if(val.get() > max) max = val.get();
            }
            result.set(max);

            context.write(key, result);
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

        // create a job with name "exo3.VentesMagasin"
        final String JOB_NAME = MaxVentesMagasin.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(MaxVentesMagasin.class);
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
