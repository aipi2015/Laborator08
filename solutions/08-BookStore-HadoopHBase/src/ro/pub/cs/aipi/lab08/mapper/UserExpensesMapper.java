package ro.pub.cs.aipi.lab08.mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserExpensesMapper extends Mapper<Text, DoubleWritable, Text, DoubleWritable> {

	public void map(ImmutableBytesWritable userAppellation, DoubleWritable invoiceValue, Context context)
			throws IOException, InterruptedException {
		context.write(new Text(userAppellation.get()), invoiceValue);
	}
}