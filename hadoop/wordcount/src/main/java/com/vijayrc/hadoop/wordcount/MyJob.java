package com.vijayrc.hadoop.wordcount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyJob {
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(MyJob.class);

        FileInputFormat.addInputPath(job, new Path("/home/vijayrc/dump/hadoop/wordcount/input/rajni.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/home/vijayrc/dump/hadoop/wordcount/output"));

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
