package exo1;

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

/**
 * Lancer un Job MapReduce pour calculer le nombre d'occurence des tailles des mots dans un texte.
 * @input Texte (mots séparés par des espaces/ponctuation)
 * @output { key: taille(mot), value: nbr_occ((taille_mot)) }
 */
public class WordSizeCount {

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1); // type of output value
        private Text keyText = new Text("");

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] words = value.toString().split("[-?!:,;\\s\\.\n\"\']+");

            for (String word : words) {
                keyText.set(String.valueOf(word.length()));
                context.write(keyText, one);     // create a pair <keyword, 1>
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;

            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);

            context.write(key, result);
        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); // get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: exo1.WordSizeCount <in> <out>");
            System.exit(2);
        }

        final String JOB_NAME = WordSizeCount.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(WordSizeCount.class);
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
