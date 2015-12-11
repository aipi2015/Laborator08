package ro.pub.cs.aipi.lab08.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

public class UserExpensesTableReducer extends TableReducer<Text, DoubleWritable, ImmutableBytesWritable> {

	public void reduce(Text userAppellation, Iterable<DoubleWritable> invoiceValues, Context context)
			throws IOException, InterruptedException {

		// TODO (exercise 6): calculate the user invoices' value and write them
		// into the expenses table

	}
}
