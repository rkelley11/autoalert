package opportunities.alertdesk

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
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class QuickSearch extends WrappedSelenium{

	def driver

	def tableBodyObjName = "Quick Search Table Body"

	def by = By.tagName("tr")

	def byRowCell = By.tagName("td")

	QuickSearch(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def openFirstDealsheetFromSearch(){

		def rowCells

		def successful = false

		this.driver.switchTo().frame("autoalertiframe")

		def tableRows = findElements(by, returnTestObject(tableBodyObjName))

		KeywordUtil.logInfo("Number of table rows found: " + tableRows.size() as String)

		if (tableRows.size() > 0){

			//Clicking on row cell instead of full row due to Firefox bug https://bugzilla.mozilla.org/show_bug.cgi?id=1448825

			rowCells = findElements(byRowCell, tableRows[0])

			successful = click(rowCells[0])

			//successful = click(tableRows[0]) //Original to click on full row. May go back once Firefox issue fixed
		}

		this.driver.switchTo().defaultContent()

		successful
	}

	@Keyword
	def getNameFromSearchResult(Integer resultNumber){

		def rowCells, customerName

		this.driver.switchTo().frame("autoalertiframe")

		def tableRows = findElements(by, returnTestObject(tableBodyObjName))

		KeywordUtil.logInfo("Number of table rows found: " + tableRows.size() as String)

		if (tableRows.size() > 0 && tableRows.size() >= resultNumber){

			rowCells = findElements(byRowCell, tableRows[resultNumber - 1])

			KeywordUtil.logInfo("Number of cells found on row " + (resultNumber as String) + ": " + rowCells.size() as String)

			customerName = getText(rowCells[1])
		}

		this.driver.switchTo().defaultContent()

		customerName
	}
}