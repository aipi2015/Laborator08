package ro.pub.cs.aipi.lab08.main;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import ro.pub.cs.aipi.lab08.general.Constants;
import ro.pub.cs.aipi.lab08.mapper.InvoiceValueMapper;
import ro.pub.cs.aipi.lab08.mapper.UserExpensesMapper;
import ro.pub.cs.aipi.lab08.reducer.InvoiceValueReducer;
import ro.pub.cs.aipi.lab08.reducer.UserExpensesFileReducer;
import ro.pub.cs.aipi.lab08.reducer.UserExpensesTableReducer;
import ro.pub.cs.aipi.lab08.utilities.CustomFileInputFormat;

public class BookStore {

	public static void main(String[] args) throws Exception {
		Configuration configuration = HBaseConfiguration.create();
		configuration.set(Constants.KEY_VALUE_SEPARATOR_PROPERTY_NAME, Constants.KEY_VALUE_SEPARATOR_VALUE);

		Job invoiceValue = Job.getInstance(configuration, Constants.INVOICE_VALUE_JOB_NAME);
		invoiceValue.setJarByClass(BookStore.class);
		Scan scan = new Scan();
		scan.setCaching(Constants.CACHING_VALUE);
		scan.setCacheBlocks(Constants.CACHE_BLOCKS_VALUE);
		TableMapReduceUtil.initTableMapperJob(Constants.INVOICE_LINE_TABLE_NAME, scan, InvoiceValueMapper.class,
				Text.class, DoubleWritable.class, invoiceValue);
		invoiceValue.setReducerClass(InvoiceValueReducer.class);
		invoiceValue.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
		FileOutputFormat.setOutputPath(invoiceValue, new Path(Constants.INVOICE_VALUE_OUTPUT_PATH));
		boolean invoiceValueResult = invoiceValue.waitForCompletion(true);
		if (!invoiceValueResult) {
			throw new IOException(Constants.INVOICE_VALUE_EXCEPTION_MESSAGE);
		}

		Job userExpenses = Job.getInstance(configuration, Constants.USER_EXPENSES_JOB_NAME);
		userExpenses.setJarByClass(BookStore.class);
		switch (Constants.RESULTS_DESTINATION) {
		case Constants.FILE:
			userExpenses.setMapperClass(UserExpensesMapper.class);
			userExpenses.setReducerClass(UserExpensesFileReducer.class);
			userExpenses.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
			userExpenses.setOutputFormatClass(TextOutputFormat.class);
			userExpenses.setOutputKeyClass(Text.class);
			userExpenses.setOutputValueClass(DoubleWritable.class);
			FileOutputFormat.setOutputPath(userExpenses, new Path(Constants.USER_EXPENSES_OUTPUT_PATH));
			break;
		case Constants.TABLE:
			userExpenses.setMapOutputKeyClass(Text.class);
			userExpenses.setMapOutputValueClass(DoubleWritable.class);
			TableMapReduceUtil.initTableReducerJob(Constants.STATISTICS_TABLE_NAME, UserExpensesTableReducer.class,
					userExpenses);
			userExpenses.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
			break;
		}
		FileInputFormat.setInputPaths(userExpenses, new Path(Constants.INVOICE_VALUE_OUTPUT_PATH));
		userExpenses.setInputFormatClass(CustomFileInputFormat.class);
		boolean userExpensesResult = userExpenses.waitForCompletion(true);
		if (!userExpensesResult) {
			throw new IOException(Constants.USER_EXPENSES_EXCEPTION_MESSAGE);
		}
	}
}
