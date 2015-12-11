package ro.pub.cs.aipi.lab08.utilities;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class CustomFileInputFormat extends FileInputFormat<Text,DoubleWritable> {
 
    @Override
    public RecordReader<Text, DoubleWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new CustomFileInputFormatRecordReader();
    }

}