package ro.pub.cs.aipi.lab08.mapper;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import ro.pub.cs.aipi.lab08.general.Constants;

public class InvoiceValueMapper extends TableMapper<Text, DoubleWritable> {

	public void map(ImmutableBytesWritable invoiceLineIdentifier, Result invoiceLineValue, Context context)
			throws IOException, InterruptedException {
		Text invoiceHeaderIdentifier = new Text(invoiceLineValue.getValue(Constants.REFERRENCE_FAMILY_COLUMN.getBytes(),
				Constants.INVOICE_HEADER_IDENTIFIER_COLUMN.getBytes()));
		String bookPresentationIdentifier = new String(invoiceLineValue.getValue(
				Constants.CONTENT_FAMILY_NAME.getBytes(), Constants.BOOK_PRESENTARION_IDENTIFIER_COLUMN.getBytes()));
		String bookQuantity = new String(invoiceLineValue.getValue(Constants.CONTENT_FAMILY_NAME.getBytes(),
				Constants.QUANTITY_COLUMN.getBytes()));

		Configuration configuration = HBaseConfiguration.create();

		// TODO (exercise 5a): get the book presentation price from the
		// corresponding HBase table

		double invoiceLineBookPresentationValue = 0;
		context.write(invoiceHeaderIdentifier, new DoubleWritable(invoiceLineBookPresentationValue));
	}
}
