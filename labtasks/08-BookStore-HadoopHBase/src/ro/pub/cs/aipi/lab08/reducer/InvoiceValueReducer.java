package ro.pub.cs.aipi.lab08.reducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class InvoiceValueReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

	public void reduce(Text invoiceHeaderIdentifier, Iterable<DoubleWritable> invoiceLineBookPresentationValues,
			Context context) throws IOException, InterruptedException {
		double invoiceValue = 0;
		for (DoubleWritable invoiceLineBookPresentationValue : invoiceLineBookPresentationValues) {
			// TODO (exercise 5b): calculate invoiceValue
		}

		Configuration configuration = HBaseConfiguration.create();

		// TODO (exercise 5c): get the user identifier of the user who has paid
		// the bill

		// TODO (exercise 5d): get the user appellation (first name & last name)
		// of the user with the user identifier previously obtained

		String userAppellation = "dummy";
		context.write(new Text(userAppellation.getBytes()), new DoubleWritable(invoiceValue));
	}
}
