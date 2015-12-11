package ro.pub.cs.aipi.lab08.general;

public interface Constants {

	final static String FILE = "file";
	final static String TABLE = "table";
	final static String RESULTS_DESTINATION = TABLE; // available options: FILE, TABLE

	final static int REDUCE_TASKS_NUMBER = 1;

	final static int CACHING_VALUE = 100;
	final static boolean CACHE_BLOCKS_VALUE = false;

	final static String KEY_VALUE_SEPARATOR_PROPERTY_NAME = "mapreduce.input.keyvaluelinerecordreader.key.value.separator";
	final static String KEY_VALUE_SEPARATOR_VALUE = "\t";
	
	final static int THREAD_POOL_SIZE = 1000;

	final static String INVOICE_HEADER_TABLE_NAME = "invoice_header";
	final static String CONSUMER_FAMILY_COLUMN = "consumer";
	final static String USER_ID_COLUMN = "user_id";

	final static String INVOICE_LINE_TABLE_NAME = "invoice_line";
	final static String REFERRENCE_FAMILY_COLUMN = "referrence";
	final static String INVOICE_HEADER_IDENTIFIER_COLUMN = "invoice_header_id";
	final static String CONTENT_FAMILY_NAME = "content";
	final static String BOOK_PRESENTARION_IDENTIFIER_COLUMN = "book_presentation_id";
	final static String QUANTITY_COLUMN = "quantity";

	final static String BOOK_PRESENTATION_TABLE_NAME = "book_presentation";
	final static String INVENTORY_FAMILY_COLUMN = "inventory";
	final static String PRICE_COLUMN = "price";

	final static String USERS_TABLE_NAME = "user";
	final static String APPELLATION_FAMILY_COLUMN = "appellation";
	final static String FIRST_NAME_COLUMN = "first_name";
	final static String LAST_NAME_COLUMN = "last_name";

	final static String STATISTICS_TABLE_NAME = "statistics";
	final static String EXPENSE_FAMILY_COLUMN = "expense";
	final static String VALUE_COLUMN = "value";

	final static String INVOICE_VALUE_JOB_NAME = "invoicevalue";
	final static String INVOICE_VALUE_OUTPUT_PATH = "hdfs://localhost:9000/user/aipi2015/invoiceValues";
	final static String INVOICE_VALUE_EXCEPTION_MESSAGE = "Error running invoice value job!";

	final static String USER_EXPENSES_JOB_NAME = "userexpenses";
	final static String USER_EXPENSES_OUTPUT_PATH = "hdfs://localhost:9000/user/aipi2015/userExpenses";
	final static String USER_EXPENSES_EXCEPTION_MESSAGE = "Error running user expenses job!";

}
