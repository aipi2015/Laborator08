package ro.pub.cs.aipi.lab08.reducer;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UserExpensesFileReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

	public void reduce(Text userAppellation, Iterable<DoubleWritable> invoiceValues, Context context)
			throws IOException, InterruptedException {
		double userExpense = 0;
		for (DoubleWritable invoiceValue : invoiceValues) {
			userExpense += invoiceValue.get();
		}

		context.write(userAppellation, new DoubleWritable(userExpense));
	}

}
