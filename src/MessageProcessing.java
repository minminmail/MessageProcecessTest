import java.util.ArrayList;

public class MessageProcessing {

	public static void main(String[] args) {

		String[] messages = receiveMessages();

		ArrayList<SaleEntity> sales = new ArrayList<SaleEntity>();
		ArrayList<SaleEntity> adjustmentsSales = new ArrayList<SaleEntity>();
		ArrayList<ReportItemEntity> reports = new ArrayList<ReportItemEntity>();
		PrintHelper printHelper = new PrintHelper();
		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);

		PrintEntity printEntity = processMessage.processMessageInBatch(messages, sales, adjustmentsSales, reports);

		processMessage.printReport(printEntity);
	}

	// make up a receiveMessages from other input system
	private static String[] receiveMessages() {

		String[] messages = new String[] { "apple at 10p", "20 sales of apples at 10p each", "Add 20p apples",
				"orange at 10p", "20 sales of oranges at 10p each", "Add 20p oranges", "kiwi at 10p",
				"20 sales of kiwis at 10p each", "Add 20p kiwis", "banana at 10p", "20 sales of bananas at 10p each",
				"Add 20p banana", "grape at 10p", "20 sales of grapes at 10p each", "Add 20p grapes", "mango at 10p",
				"20 sales of mangos at 10p each", "Add 20p mangos", "apple at 10p", "20 sales of apples at 10p each",
				"Add 20p apples", "orange at 10p", "apple at 10p", "20 sales of apples at 10p each", "Add 20p apples",
				"orange at 10p", "20 sales of oranges at 10p each", "Add 20p oranges", "kiwi at 10p",
				"20 sales of kiwis at 10p each", "Add 20p kiwis", "banana at 10p", "20 sales of bananas at 10p each",
				"Add 20p banana", "grape at 10p", "20 sales of grapes at 10p each", "Add 20p grapes", "mango at 10p",
				"20 sales of mangos at 10p each", "Add 20p mangos", "apple at 10p", "20 sales of apples at 10p each",
				"Add 20p apples", "orange at 10p", "apple at 10p", "20 sales of apples at 10p each", "Add 20p apples",
				"orange at 10p", "20 sales of oranges at 10p each", "Add 20p oranges", "kiwi at 10p",
				"20 sales of kiwis at 10p each", "Add 20p kiwis", "banana at 10p", "20 sales of bananas at 10p each",
				"Add 20p banana", "grape at 10p", "20 sales of grapes at 10p each", "Add 20p grapes", "mango at 10p",
				"20 sales of mangos at 10p each", "Add 20p mangos", "apple at 10p", "20 sales of apples at 10p each",
				"Add 20p apples", "orange at 10p" };

		return messages;
	}
}

class MessageEntity {

	private String messagText;

	public void setMessageText(String text) {
		this.messagText = text;
	}

	public String getMessageText() {

		return this.messagText;
	}

}

class SaleEntity {

	String productType;
	double value;

	public void setProductType(String type) {
		this.productType = type;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {

		return this.value;
	}
}

class ReportItemEntity {

	String productType;
	double valueTotal;
	int salesNumber;

	public void setProductType(String type) {
		this.productType = type;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setValueTotal(double valueTotal) {
		this.valueTotal = valueTotal;
	}

	public double getValueTotal() {
		return this.valueTotal;
	}

	public void setSalesNumber(int salesNumber) {
		this.salesNumber = salesNumber;
	}

	public int getSalesNumber() {
		return this.salesNumber;
	}
}

class SaleInformationEntity {

	String productType;
	double pound;
	int salesNumber;
	boolean isAdd;

	public void setProductType(String type) {
		this.productType = type;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setPound(double value) {
		this.pound = value;
	}

	public double getPound() {

		return this.pound;
	}

	public void setSalesNumber(int salesNumber) {
		this.salesNumber = salesNumber;
	}

	public int getSalesNumber() {

		return this.salesNumber;
	}

	public void setIsADD(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public boolean getIsAdd() {

		return this.isAdd;
	}
}

class PrintEntity {

	int messageCount;
	ArrayList<SaleEntity> adjustmentsSales;
	ArrayList<ReportItemEntity> reports;

	public void setMessageCount(int count) {
		this.messageCount = count;
	}

	public void setAdjustmentsSales(ArrayList<SaleEntity> adjustmentsSales) {
		this.adjustmentsSales = adjustmentsSales;

	}

	public void setReports(ArrayList<ReportItemEntity> reports) {
		this.reports = reports;
	}

	public int getMessageCount() {
		return this.messageCount;
	}

	public ArrayList<SaleEntity> getAdjustmentsSales() {
		return this.adjustmentsSales;
	}

	public ArrayList<ReportItemEntity> getReports() {
		return this.reports;

	}
}

class PrintHelper {

	public void printFiftyMessages(ArrayList<SaleEntity> adjustmentsSales) {
		System.out.println("The application is pausing and it will not accept new message !!! ");
		System.out.println("-------------------Adjustments Report------------------------ ");
		System.out.println("-----Product type--------Adjustment Value ");
		for (SaleEntity sale : adjustmentsSales) {
			System.out.println("-------------" + sale.getProductType() + "-------------" + sale.getValue());
		}
	}

	public void printTenMessages(ArrayList<ReportItemEntity> reports) {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("|   Product type   |   Number of Sales   |  Total Value  |");
		for (ReportItemEntity item : reports) {
			System.out.println("|--------------------------------------------------------|");
			System.out.println("|         " + item.productType + "             " + item.salesNumber + "                "
					+ item.valueTotal + "     |");
		}
	}

}

class ProcessMessageToSale {

	private PrintHelper printHelper;

	public ProcessMessageToSale(PrintHelper printHelper) {
		this.printHelper = printHelper;

	}

	public PrintEntity processMessageInBatch(String[] messages, ArrayList<SaleEntity> salesPass,
			ArrayList<SaleEntity> adjustmentsSalesPass, ArrayList<ReportItemEntity> reportsPass) {

		int messageCounter = 0;

		ArrayList<SaleEntity> sales = salesPass;
		ArrayList<SaleEntity> adjustmentsSales = adjustmentsSalesPass;
		ArrayList<ReportItemEntity> reports = reportsPass;

		for (String message : messages) {
			messageCounter = messageCounter + 1;
			// System.out.println(message);
			SaleInformationEntity saleInformatoin = getSalesInformation(message);

			// System.out.println("
			// *******saleInformatoin********"+saleInformatoin.productType+saleInformatoin.getPound()+saleInformatoin.isAdd+saleInformatoin.salesNumber);
			/*
			 * Message Type 3 :contains the details of a sale and an adjustment
			 * operation to be applied to all stored sales of this product type.
			 * Operations can be add, subtract, or multiply
			 */

			if (saleInformatoin.isAdd == true) {

				// add adjustmentSale record for later report
				SaleEntity adjustmentSale = new SaleEntity();
				adjustmentSale.setProductType(saleInformatoin.getProductType());
				adjustmentSale.setValue(saleInformatoin.getPound());
				adjustmentsSales.add(adjustmentSale);

				// System.out.println(" *******to handle isAdd
				// SalesInformation********");
				if (sales != null) {
					for (SaleEntity sale : sales) {
						if (sale.productType.equals(saleInformatoin.productType)) {
							sale.value = sale.value + saleInformatoin.pound;

							// System.out.println(" *******to handle isAdd
							// SalesInformation********"+sale.value+sale.productType);
						}
					}
				}
			} else {

				// System.out.println(" *******add in not true in
				// SalesInformation********");
				// Message Type 2, contains the details of a sale and the number
				// of occurrences of that sale.
				if (saleInformatoin.salesNumber != 0) {
					// System.out.println(" *******saleInformatoin.salesNumber
					// != 0********");
					for (int i = 0; i < saleInformatoin.salesNumber; i++) {
						SaleEntity sale = new SaleEntity();
						sale.productType = saleInformatoin.productType;
						sale.value = saleInformatoin.pound;
						// System.out.println("add new item from add
						// type"+sale.productType+sale.value);
						sales.add(sale);
					}
				} else {
					// contains the details of 1 sale
					SaleEntity sale = new SaleEntity();

					sale.productType = saleInformatoin.productType;
					sale.value = saleInformatoin.pound;
					sales.add(sale);
				}
			}
			reports = saveReportItemList(sales);

		}

		PrintEntity printEntity = new PrintEntity();
		printEntity.setMessageCount(messageCounter);
		printEntity.setAdjustmentsSales(adjustmentsSales);
		printEntity.setReports(reports);

		return printEntity;
	}

	public void printReport(PrintEntity printEntity) {

		int messageCounter = printEntity.getMessageCount();

		ArrayList<SaleEntity> adjustmentsSales = printEntity.getAdjustmentsSales();
		ArrayList<ReportItemEntity> reports = printEntity.getReports();

		for (int i = 1; i <= messageCounter; i++) {
			if (i % 10 == 0) {

				printHelper.printTenMessages(reports);				
				
				if (i % 50 == 0) {
					printHelper.printFiftyMessages(adjustmentsSales);
				}
				
			}
		}
	}

	public boolean isDoubleNumber(String word) {

		try {
			Double.parseDouble(word);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	public boolean isIntegerNumber(String word) {

		try {
			Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public boolean isAdd(String word) {

		if (word.equalsIgnoreCase("add"))

			return true;

		else

			return false;
	}

	public SaleInformationEntity getSalesInformation(String message) {

		SaleInformationEntity saleInformatoin = new SaleInformationEntity();

		String[] words = message.split("\\s+");

		String firstWord = words[0];

		// System.out.println("firstWord="+firstWord);
		String secondWord = words[1];
		String poundString;

		/*
		 * Since there is no more other information about how to get product
		 * type, we will strictly get each sale informaiton according to the
		 * message format
		 */

		// To check if it is the message type 2
		if (isIntegerNumber(firstWord) && secondWord.equalsIgnoreCase("sales")) {

			saleInformatoin.setIsADD(false);
			saleInformatoin.salesNumber = Integer.parseInt(firstWord);

			// words[3] is supposed the product type, delete the last s to get
			// real product type
			saleInformatoin.productType = words[3].substring(0, words[3].length() - 1);

			// delete the end p to get pound value
			poundString = words[5].substring(0, words[5].length() - 1);

			if (isDoubleNumber(poundString)) {
				saleInformatoin.pound = Double.parseDouble(poundString);
			}
		}
		// To check if it is message type 3
		else if (firstWord.equalsIgnoreCase("add")) {

			saleInformatoin.setIsADD(true);
			// words[1] should be the value
			poundString = words[1].substring(0, words[1].length() - 1);
			if (isDoubleNumber(poundString)) {
				saleInformatoin.pound = Double.parseDouble(poundString);
			}
			// word[2] should be the product, but need to delete s

			saleInformatoin.productType = words[2].substring(0, words[2].length() - 1);
		} else
		// it should be message type 1
		{
			saleInformatoin.isAdd = false;
			saleInformatoin.productType = words[0];
			// words[2] should be the pound
			poundString = words[2].substring(0, words[2].length() - 1);
			if (isDoubleNumber(poundString)) {
				saleInformatoin.pound = Double.parseDouble(poundString);
			}
		}

		// System.out.println("saleInformatoin="+"type"+saleInformatoin.productType+"
		// "+saleInformatoin.pound+"add"+saleInformatoin.isAdd);
		return saleInformatoin;
	}

	// To calculate different product type ,sales number and total value
	public ArrayList<ReportItemEntity> saveReportItemList(ArrayList<SaleEntity> sales) {
		ArrayList<ReportItemEntity> reportList = new ArrayList<ReportItemEntity>();

		// System.out.println("sales.size()"+sales.size());
		for (int i = 0; i < sales.size(); i++) {

			String type = sales.get(i).productType;
			// System.out.println("type"+type);
			if (isNotInProductTypeList(type, reportList)) {
				double value = 0;
				int salesNumber = 0;
				ReportItemEntity newItem = new ReportItemEntity();
				newItem.productType = type;
				value = 0;
				// System.out.println("value"+value);

				for (SaleEntity sale : sales) {
					if (sale.productType.equalsIgnoreCase(type)) {
						value = value + sale.getValue();
						salesNumber = salesNumber + 1;
					}
				}

				newItem.valueTotal = value;
				newItem.salesNumber = salesNumber;

				reportList.add(newItem);
			}
		}
		return reportList;
	}

	public boolean isNotInProductTypeList(String producttype, ArrayList<ReportItemEntity> reportList) {
		boolean isNotInList = true;
		for (ReportItemEntity item : reportList) {

			if (item.productType.equalsIgnoreCase(producttype))

				isNotInList = false;
		}

		return isNotInList;
	}
}
