package opportunities.preowned

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Preowned {

	def iFrameFinderBy = By.tagName("iframe")
	def scrollableAreasBy = By.xpath("//*[@id=\"scrollable-area\"]/table/tbody")
	def rowsBy = By.tagName("tr")

	@Keyword
	def returnNumberOfRows(Reports reportName){

		getTableRows(reportName.value).size()
	}

	def getTableRows(int reportIndex){

		def size, rowsCount = 0
		def timeToWait = 10000
		def frames, scrollableAreas, rowsCollection
		def startTime = System.currentTimeMillis();
		def driver = DriverFactory.getWebDriver()

		driver.switchTo().defaultContent()

		// Making sure frames are switch-able before attempting to switch
		while(size == 0 || (System.currentTimeMillis() - startTime) < timeToWait) {

			frames = driver.findElements(iFrameFinderBy)

			size = frames.size()

		}

		KeywordUtil.logInfo("Number of frames: " + size as String)

		for (int i = 0; i < size; i++){

			driver.switchTo().defaultContent()

			KeywordUtil.logInfo("Looking in frames: " + i as String)

			driver.switchTo().frame(i)

			// Each report has the same Id, so need to grab all and then select which one to use
			scrollableAreas = driver.findElements(scrollableAreasBy)

			def foundStatus = scrollableAreas.size() > 0 ? "found" : "NOT found"

			KeywordUtil.logInfo("Reports " + foundStatus + " in frame")
		}

		KeywordUtil.logInfo("Report index: " + reportIndex as String)

		rowsCollection = scrollableAreas[reportIndex].findElements(rowsBy)

		rowsCount = rowsCollection.size()

		KeywordUtil.logInfo("Number of table rows: " + rowsCount as String)

		rowsCollection
	}

	public enum Reports{

		TOPUSEDCARSALESOPPORTUNITIES(0),
		FASTESTSELLINGINVENTORY(1),
		ONEONONE(2)

		Reports(int value) {

			this.value = value
		}

		private final int value

		int getValue() {

			value
		}
	}
}
