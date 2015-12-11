package ro.pub.cs.aipi.lab08.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import ro.pub.cs.aipi.lab08.general.Constants;

public class UserExpensesTableReducer extends TableReducer<Text, DoubleWritable, ImmutableBytesWritable> {

	public void reduce(Text userAppellation, Iterable<DoubleWritable> invoiceValues, Context context)
			throws IOException, InterruptedException {
		double userExpense = 0;
		for (DoubleWritable invoiceValue : invoiceValues) {
			userExpense += invoiceValue.get();
		}

		Put put = new Put(Bytes.toBytes(userAppellation.toString()));
		put.addColumn(Constants.EXPENSE_FAMILY_COLUMN.getBytes(), Constants.VALUE_COLUMN.getBytes(),
				Double.toString(userExpense).getBytes());
		context.write(null, put);
	}
}
