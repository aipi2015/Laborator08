package ro.pub.cs.aipi.lab08.utilities;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class CustomFileInputFormatRecordReader extends RecordReader<Text, DoubleWritable> {
	
	private final LineRecordReader lineRecordReader;
	private byte separator = (byte)'\t';
	private Text lineValue;
	private Text key;
	private DoubleWritable value;
	
	public Class<Text> getKeyClass() {
		return Text.class;
	}
	
	public Class<DoubleWritable> getValueClass() {
		return DoubleWritable.class;
	}
	
	public CustomFileInputFormatRecordReader() {
		lineRecordReader = new LineRecordReader();
	}
	
	public void initialize(InputSplit genericSplit, TaskAttemptContext context) throws IOException {
		lineRecordReader.initialize(genericSplit, context);
	}
	
	public static int findSeparator(byte[] utf, int start, int length, byte separator) {
		for (int index = start; index < (start + length); index++) {
			if (utf[index] == separator) {
				return index;
			}
		}
		return -1;
	}
	
	public static void setKeyValue(Text key, DoubleWritable value, byte[] line, int lineLength, int position) {
		if (position == -1) {
			key.set(line, 0, lineLength);
			value.set(0);
		} else {
			key.set(line, 0, position);
			Text textValue = new Text();
			textValue.set(line, position + 1, lineLength - position - 1);
			value.set(Double.parseDouble(textValue.toString()));
		}
	}
	
	public synchronized boolean nextKeyValue() throws IOException {
		byte[] lineContent = null;
		int lineLength = -1;
		if (lineRecordReader.nextKeyValue()) {
			lineValue = lineRecordReader.getCurrentValue();
			lineContent = lineValue.getBytes();
			lineLength = lineValue.getLength();
		} else {
			return false;
		}
		if (lineContent == null) {
			return false;
		}
		if (key == null) {
			key = new Text();
		}
		if (value == null) {
			value = new DoubleWritable();
		}
		int position = findSeparator(lineContent, 0, lineLength, this.separator);
		setKeyValue(key, value, lineContent, lineLength, position);
		return true;
	}
	
	public Text getCurrentKey() {
		return key;
	}
	
	public DoubleWritable getCurrentValue() {
		return value;
	}
	
	public float getProgress() throws IOException {
		return lineRecordReader.getProgress();
	}
	
	public synchronized void close() throws IOException { 
		lineRecordReader.close();
	}
}
