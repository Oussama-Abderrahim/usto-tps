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
import java.util.stream.StreamSupport;

public class SocialNetwork {

    /**
     * @input :
     * @output : (key: L1, Value : "L2, L3")
     */
    public static class Map extends Mapper<LongWritable, Text, Text, Text> {

        private Text keyText = new Text("");
        private Text resultValue = new Text("");

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] users = value.toString().split(" ");

            String user = users[0];

            for (int i = 1; i < users.length; i++) {
                final String friend = users[i];

                if (user.charAt(0) < friend.charAt(0)) // clé Ami-User ordonnée alphabetiquement
                    keyText.set(user + "-" + friend);
                else
                    keyText.set(friend + "-" + user);

                String[] others = Arrays.stream(users).filter(s -> !s.equals(user) && !s.equals(friend)).toArray(String[]::new);
                resultValue.set(String.join(" ", others));

                context.write(keyText, resultValue);
            }


        }
    }

    public static class Reduce
            extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            Text[] valuesArray = StreamSupport.stream(values.spliterator(), false).toArray(Text[]::new);


            StringBuilder listeAmis = new StringBuilder();

            if(valuesArray.length != 2)
            {
                // Ne doit pas arriver
            }
            else {
                for(String ami : valuesArray[0].toString().split(" "))
                {
                    if(valuesArray[1].toString().contains(ami))
                    {
                        listeAmis.append(ami).append(" ");
                    }
                }

                context.write(key, new Text(listeAmis.toString()));
            }

        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); // get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: cours.SocialNetwork <in> <out>");
            System.exit(2);
        }

        // create a job with name "cours.SocialNetwork"
        final String JOB_NAME = SocialNetwork.class.getSimpleName();
        Job job = new Job(conf, JOB_NAME);
        job.setJarByClass(SocialNetwork.class);
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
