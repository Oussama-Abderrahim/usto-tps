package exo4;

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

public class StatistiquesPersonnel {

    public static class Map
            extends Mapper<LongWritable, Text, Text, Text> {

        private Text keyText = new Text("");
        private Text valueText = new Text("");

        //private String separatorsRegex = "[\.,\";\s]+";
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] words = value.toString().split(" \\| ");

            String age = words[1];
            String salaire = words[3];


            keyText.set(age);
            valueText.set("" + 1 + " | " + salaire + " | " + salaire);
            context.write(keyText, valueText);     // create a pair <keyword, 1>

        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        private Text result = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int minSalaire = -1;
            int maxSalaire = -1;
            int count = 0;
            for (Text val : values) {
                String[] words = val.toString().split(" \\| ");

                int cpt = Integer.parseInt(words[0]);
                int salaireMin = Integer.parseInt(words[1]);
                int salaireMax = Integer.parseInt(words[2]);

                count += cpt;

                if (minSalaire == -1 || salaireMin < minSalaire)
                    minSalaire = salaireMin;

                if(maxSalaire < salaireMax)
                    maxSalaire = salaireMax;
            }
            result.set("" + count + " | " + minSalaire + " | " + maxSalaire);

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

        // create a job with name "exo4.StatistiquesPersonnel"
        final String JOB_NAME = StatistiquesPersonnel.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(StatistiquesPersonnel.class);
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
