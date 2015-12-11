package ro.pub.cs.aipi.lab08.reducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ro.pub.cs.aipi.lab08.general.Constants;

public class InvoiceValueReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

	public void reduce(Text invoiceHeaderIdentifier, Iterable<DoubleWritable> invoiceLineBookPresentationValues,
			Context context) throws IOException, InterruptedException {
		double invoiceValue = 0;
		for (DoubleWritable invoiceLineBookPresentationValue : invoiceLineBookPresentationValues) {
			invoiceValue += invoiceLineBookPresentationValue.get();
		}

		Configuration configuration = HBaseConfiguration.create();
		Connection connection = ConnectionFactory.createConnection(configuration);

		Table invoiceHeaderTable = connection.getTable(TableName.valueOf(Constants.INVOICE_HEADER_TABLE_NAME));
		Get getInvoiceHeader = new Get(invoiceHeaderIdentifier.toString().getBytes());
		getInvoiceHeader.addColumn(Constants.CONSUMER_FAMILY_COLUMN.getBytes(), Constants.USER_ID_COLUMN.getBytes());
		Result resultInvoiceHeader = invoiceHeaderTable.get(getInvoiceHeader);
		String userIdentifier = new String(resultInvoiceHeader.getValue(Constants.CONSUMER_FAMILY_COLUMN.getBytes(),
				Constants.USER_ID_COLUMN.getBytes()));
		invoiceHeaderTable.close();

		Table usersTable = connection.getTable(TableName.valueOf(Constants.USERS_TABLE_NAME));
		Get getUser = new Get(userIdentifier.getBytes());
		getUser.addColumn(Constants.APPELLATION_FAMILY_COLUMN.getBytes(), Constants.LAST_NAME_COLUMN.getBytes());
		getUser.addColumn(Constants.APPELLATION_FAMILY_COLUMN.getBytes(), Constants.FIRST_NAME_COLUMN.getBytes());
		Result resultUser = usersTable.get(getUser);
		String userAppellation = new String(resultUser.getValue(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),
				Constants.FIRST_NAME_COLUMN.getBytes())) + " "
				+ new String(resultUser.getValue(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),
						Constants.LAST_NAME_COLUMN.getBytes()));
		usersTable.close();
		
		connection.close();

		context.write(new Text(userAppellation.getBytes()), new DoubleWritable(invoiceValue));
	}
}
