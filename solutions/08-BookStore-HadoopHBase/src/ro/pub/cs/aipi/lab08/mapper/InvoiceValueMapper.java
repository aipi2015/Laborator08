package ro.pub.cs.aipi.lab08.mapper;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
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
		Connection connection = ConnectionFactory.createConnection(configuration);

		Table bookPresentationTable = connection.getTable(TableName.valueOf(Constants.BOOK_PRESENTATION_TABLE_NAME));
		Get bookPresentationGet = new Get(bookPresentationIdentifier.getBytes());
		bookPresentationGet.addColumn(Constants.INVENTORY_FAMILY_COLUMN.getBytes(), Constants.PRICE_COLUMN.getBytes());
		Result result = bookPresentationTable.get(bookPresentationGet);
		String bookPresentationPrice = new String(
				result.getValue(Constants.INVENTORY_FAMILY_COLUMN.getBytes(), Constants.PRICE_COLUMN.getBytes()));
		bookPresentationTable.close();
		
		connection.close();

		double invoiceLineBookPresentationValue = Double.parseDouble(bookPresentationPrice)
				* Long.parseLong(bookQuantity);
		context.write(invoiceHeaderIdentifier, new DoubleWritable(invoiceLineBookPresentationValue));
	}
}
