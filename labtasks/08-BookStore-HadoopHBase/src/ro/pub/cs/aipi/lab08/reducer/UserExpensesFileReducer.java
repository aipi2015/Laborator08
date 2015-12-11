package ro.pub.cs.aipi.lab08.reducer;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UserExpensesFileReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

	public void reduce(Text userAppellation, Iterable<DoubleWritable> invoiceValues, Context context)
			throws IOException, InterruptedException {

		// TODO (exercise 5f): calculate the client invoices' value and write
		// them to the context

	}

}
