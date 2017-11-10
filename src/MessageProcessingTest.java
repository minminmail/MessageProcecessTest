import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

public class MessageProcessingTest {

	PrintHelper printHelper = new PrintHelper();

	@Test

	public void IsDoubleNumberFunctionAssertIsDoubbleTrueTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		boolean isDouble = processMessageToSale.isDoubleNumber("5.6");

		assertTrue(isDouble);
	}

	@Test

	public void IsDoubleNumberFunctionAssertIsNotDoubbleFalseTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		boolean isDouble = processMessageToSale.isDoubleNumber("okay223");

		assertFalse(isDouble);
	}

	@Test

	public void IsIntegerNumberFunctionAssertIsIntegerTrueTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		boolean isInteger = processMessageToSale.isIntegerNumber("789");

		assertTrue(isInteger);
	}

	@Test

	public void IsIntegerNumberFunctionAssertIsNotIntegerFalseTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		boolean isInteger = processMessageToSale.isIntegerNumber("23.56");

		assertFalse(isInteger);
	}

	@Test

	public void IsAddFunctionAssertIsAddTrueTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		boolean isAdd = processMessageToSale.isAdd("add");

		assertTrue(isAdd);
	}

	@Test

	public void IsAddFunctionAssertIsNotAddFalseTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		boolean isAdd = processMessageToSale.isAdd("Orange");

		assertFalse(isAdd);
	}

	@Test

	public void SaveReportItemEntityListAssertMoreThanOneSalesNumberReportValueCorrectTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		ArrayList<SaleEntity> sales = setupSalesEntities();
		ArrayList<ReportItemEntity> reports = processMessageToSale.saveReportItemList(sales);

		assertEquals("Apple", reports.get(0).productType);
		assertEquals(19, reports.get(0).valueTotal, 2);
		assertEquals(3, reports.get(0).salesNumber);
	}

	@Test

	public void SaveReportItemEntityEntityListAssertOneSaleNumberReportValueCorrectTest() {

		ProcessMessageToSale processMessageToSale = new ProcessMessageToSale(printHelper);

		ArrayList<SaleEntity> sales = setupSalesEntities();
		ArrayList<ReportItemEntity> reports = processMessageToSale.saveReportItemList(sales);

		assertEquals("Strawberry", reports.get(2).productType);
		assertEquals(8.11, reports.get(2).valueTotal, 2);
		assertEquals(1, reports.get(2).salesNumber);
	}

	@Test

	public void getSalesInformationAssertMessageTypeOneTest() {

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);
		String message = "Add 20p grapes";

		SaleInformationEntity infoEntity = new SaleInformationEntity();
		infoEntity = processMessage.getSalesInformation(message);
		assertTrue(infoEntity.isAdd);
		assertEquals(infoEntity.salesNumber, 0);
		assertEquals(20, infoEntity.pound, 2);
		assertEquals("grape", infoEntity.productType);
	}

	@Test

	public void IsNoTInProductTypeListAssertTrueTest() {

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);
		ArrayList<ReportItemEntity> reportList = setupReportEntities();
		boolean isNotINProductTypeList = processMessage.isNotInProductTypeList("pineapple", reportList);
		assertTrue(isNotINProductTypeList);
	}

	@Test

	public void IsNoTInProductTypeListAssertFalseTest() {

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);
		ArrayList<ReportItemEntity> reportList = setupReportEntities();
		boolean isNotINProductTypeList = processMessage.isNotInProductTypeList("apple", reportList);
		assertFalse(isNotINProductTypeList);
	}

	@Test

	public void getSalesInformationAssertMessageTypeTwoTest() {

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);
		String message = "20 sales of apples at 10p each";

		SaleInformationEntity infoEntity = new SaleInformationEntity();
		infoEntity = processMessage.getSalesInformation(message);
		assertFalse(infoEntity.isAdd);
		assertEquals(infoEntity.salesNumber, 20);
		assertEquals(10, infoEntity.pound, 2);
		assertEquals("apple", infoEntity.productType);
	}

	@Test

	public void getSalesInformationAssertMessageTypeThreeTest() {

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);
		String message = "apple at 10p";

		SaleInformationEntity infoEntity = new SaleInformationEntity();
		infoEntity = processMessage.getSalesInformation(message);
		assertFalse(infoEntity.isAdd);
		assertEquals(10, infoEntity.pound, 2);
		assertEquals("apple", infoEntity.productType);
	}

	@Test

	public void ProcessMessageInBatchAssertAdjustmentSaleNumberTest() {

		ArrayList<SaleEntity> sales = new ArrayList<SaleEntity>();
		ArrayList<SaleEntity> adjustmentsSales = new ArrayList<SaleEntity>();
		ArrayList<ReportItemEntity> reports = new ArrayList<ReportItemEntity>();

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);

		String[] messages = setupTwentyTwoMessages();

		processMessage.processMessageInBatch(messages, sales, adjustmentsSales, reports);

		assertEquals(7, adjustmentsSales.size());

	}

	@Test
	public void ProcessMessageInBatchAssertSalesNumberMessageTwoTest() {

		ArrayList<SaleEntity> sales = new ArrayList<SaleEntity>();
		ArrayList<SaleEntity> adjustmentsSales = new ArrayList<SaleEntity>();
		ArrayList<ReportItemEntity> reports = new ArrayList<ReportItemEntity>();

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);

		String[] messages = setupTwoMessages();

		PrintEntity printEntity = processMessage.processMessageInBatch(messages, sales, adjustmentsSales, reports);

		ReportItemEntity testReportItem = new ReportItemEntity();

		for (ReportItemEntity report : printEntity.reports) {
			if (report.getProductType().equalsIgnoreCase("apple"))
				testReportItem = report;

		}

		assertEquals(1, printEntity.reports.size());
		assertEquals(21, testReportItem.getSalesNumber(), 2);
		assertEquals(210, testReportItem.getValueTotal(), 2);

	}

	@Test
	public void ProcessMessageInBatchAssertSalesNumberMessageFiveTest() {

		ArrayList<SaleEntity> sales = new ArrayList<SaleEntity>();
		ArrayList<SaleEntity> adjustmentsSales = new ArrayList<SaleEntity>();
		ArrayList<ReportItemEntity> reports = new ArrayList<ReportItemEntity>();

		ProcessMessageToSale processMessage = new ProcessMessageToSale(printHelper);

		String[] messages = setupFiveMessages();

		PrintEntity printEntity = processMessage.processMessageInBatch(messages, sales, adjustmentsSales, reports);

		ReportItemEntity testReportItem = new ReportItemEntity();

		for (ReportItemEntity report : printEntity.reports) {
			if (report.getProductType().equalsIgnoreCase("apple"))
				testReportItem = report;

		}

		assertEquals(2, printEntity.reports.size());
		assertEquals(21, testReportItem.getSalesNumber(), 2);
		assertEquals(630, testReportItem.getValueTotal(), 2);

	}

	@Test

	public void PrintReportTenMessagesTest() throws Exception {

		// create a mock of printHelper
		PrintHelper printHelperMock = Mockito.mock(PrintHelper.class);

		// create a process object
		ProcessMessageToSale process = new ProcessMessageToSale(printHelperMock);

		PrintEntity printEntity = setupPrintEntityTen();
		process.printReport(printEntity);

		Mockito.verify(printHelperMock, Mockito.times(1)).printTenMessages(printEntity.getReports());

	}

	@Test

	public void PrintReportFiftyMessagesTest() throws Exception {

		// create a mock of printHelper
		PrintHelper printHelperMock = Mockito.mock(PrintHelper.class);

		// create a process object
		ProcessMessageToSale process = new ProcessMessageToSale(printHelperMock);

		PrintEntity printEntity = setupPrintEntityFifty();
		process.printReport(printEntity);

		Mockito.verify(printHelperMock, Mockito.times(5)).printTenMessages(printEntity.getReports());
		Mockito.verify(printHelperMock, Mockito.times(1)).printFiftyMessages(printEntity.getAdjustmentsSales());

	}

	@Test

	public void PrintReportOneHuandredMessagesTest() throws Exception {

		// create a mock of printHelper
		PrintHelper printHelperMock = Mockito.mock(PrintHelper.class);

		// create a process object
		ProcessMessageToSale process = new ProcessMessageToSale(printHelperMock);

		PrintEntity printEntity = setupPrintEntityOneHundred();
		process.printReport(printEntity);

		Mockito.verify(printHelperMock, Mockito.times(10)).printTenMessages(printEntity.getReports());
		Mockito.verify(printHelperMock, Mockito.times(2)).printFiftyMessages(printEntity.getAdjustmentsSales());

	}

	protected String[] setupTwoMessages() {
		String[] messages = new String[] { "apple at 10p", "20 sales of apples at 10p each" };

		return messages;
	}

	protected String[] setupFiveMessages() {
		String[] messages = new String[] { "apple at 10p", "20 sales of apples at 10p each", "Add 20p apples",
				"orange at 10p", "20 sales of oranges at 10p each" };

		return messages;
	}

	protected String[] setupTenMessages() {
		String[] messages = new String[] { "apple at 10p", "20 sales of apples at 10p each", "Add 20p apples",
				"orange at 10p", "20 sales of oranges at 10p each", "Add 20p oranges", "kiwi at 10p", "apple at 10p",
				"20 sales of apples at 10p each", "Add 20p apples" };

		return messages;
	}

	protected String[] setupTwentyTwoMessages() {
		String[] messages = new String[] { "apple at 10p", "20 sales of apples at 10p each", "Add 20p apples",
				"orange at 10p", "20 sales of oranges at 10p each", "Add 20p oranges", "kiwi at 10p",
				"20 sales of kiwis at 10p each", "Add 20p kiwis", "banana at 10p", "20 sales of bananas at 10p each",
				"Add 20p banana", "grape at 10p", "20 sales of grapes at 10p each", "Add 20p grapes", "mango at 10p",
				"20 sales of mangos at 10p each", "Add 20p mangos", "apple at 10p", "20 sales of apples at 10p each",
				"Add 20p apples", "orange at 10p" };

		return messages;

	}

	protected ArrayList<SaleEntity> setupSalesEntities() {

		ArrayList<SaleEntity> sales = new ArrayList<SaleEntity>();

		SaleEntity saleEntityOne = new SaleEntity();
		saleEntityOne.setProductType("Apple");
		saleEntityOne.setValue(6.5);

		sales.add(saleEntityOne);

		SaleEntity saleEntityTwo = new SaleEntity();
		saleEntityTwo.setProductType("Orange");
		saleEntityTwo.setValue(3.5);
		sales.add(saleEntityTwo);

		SaleEntity saleEntityThree = new SaleEntity();
		saleEntityThree.setProductType("Apple");
		saleEntityThree.setValue(2.5);
		sales.add(saleEntityThree);

		SaleEntity saleEntityFour = new SaleEntity();
		saleEntityFour.setProductType("Strawberry");
		saleEntityFour.setValue(8.11);
		sales.add(saleEntityFour);

		SaleEntity saleEntityFive = new SaleEntity();
		saleEntityFive.setProductType("Orange");
		saleEntityFive.setValue(5);
		sales.add(saleEntityFive);

		SaleEntity saleEntitySix = new SaleEntity();
		saleEntitySix.setProductType("Apple");
		saleEntitySix.setValue(10);
		sales.add(saleEntitySix);

		return sales;
	}

	protected PrintEntity setupPrintEntityTen() {

		PrintEntity printEntity = new PrintEntity();
		ArrayList<SaleEntity> adjustmentsSales = setupSalesEntities();
		ArrayList<ReportItemEntity> reports = setupReportEntities();

		int messageCounter = 10;
		printEntity.setMessageCount(messageCounter);
		printEntity.setAdjustmentsSales(adjustmentsSales);
		printEntity.setReports(reports);

		return printEntity;
	}

	protected PrintEntity setupPrintEntityFifty() {

		PrintEntity printEntity = new PrintEntity();
		ArrayList<SaleEntity> adjustmentsSales = setupSalesEntities();
		ArrayList<ReportItemEntity> reports = setupReportEntities();

		int messageCounter = 50;
		printEntity.setMessageCount(messageCounter);
		printEntity.setAdjustmentsSales(adjustmentsSales);
		printEntity.setReports(reports);

		return printEntity;
	}

	protected PrintEntity setupPrintEntityOneHundred() {

		PrintEntity printEntity = new PrintEntity();
		ArrayList<SaleEntity> adjustmentsSales = setupSalesEntities();
		ArrayList<ReportItemEntity> reports = setupReportEntities();

		int messageCounter = 100;
		printEntity.setMessageCount(messageCounter);
		printEntity.setAdjustmentsSales(adjustmentsSales);
		printEntity.setReports(reports);

		return printEntity;
	}

	protected ArrayList<ReportItemEntity> setupReportEntities() {

		ArrayList<ReportItemEntity> reports = new ArrayList<ReportItemEntity>();

		ReportItemEntity reportEntityOne = new ReportItemEntity();
		reportEntityOne.setProductType("apple");
		reportEntityOne.setSalesNumber(3);
		reportEntityOne.setValueTotal(23.68);
		reports.add(reportEntityOne);

		ReportItemEntity reportEntityTwo = new ReportItemEntity();
		reportEntityTwo.setProductType("orange");
		reportEntityTwo.setSalesNumber(5);
		reportEntityTwo.setValueTotal(20);
		reports.add(reportEntityTwo);

		ReportItemEntity reportEntityThree = new ReportItemEntity();
		reportEntityThree.setProductType("stawberry");
		reportEntityThree.setSalesNumber(2);
		reportEntityThree.setValueTotal(5);
		reports.add(reportEntityThree);

		ReportItemEntity reportEntityFour = new ReportItemEntity();
		reportEntityFour.setProductType("kiwi");
		reportEntityFour.setSalesNumber(6);
		reportEntityFour.setValueTotal(12.56);
		reports.add(reportEntityFour);

		ReportItemEntity reportEntityFive = new ReportItemEntity();
		reportEntityFive.setProductType("mange");
		reportEntityFive.setSalesNumber(10);
		reportEntityFive.setValueTotal(35.68);
		reports.add(reportEntityFive);

		return reports;
	}
}
