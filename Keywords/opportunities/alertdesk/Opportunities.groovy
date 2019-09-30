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

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*
import dealsheet.*
import sql.Queries

public class Opportunities extends WrappedSelenium{

	def String iframeId = "autoalertiframe"
	def String gridId = "GridData"
	def String rowClass = "PagerDataRow"

	def gridDataObjName = "Opportunities Grid Data"

	def driver

	Opportunities(){

		this.driver = DriverFactory.getWebDriver()
	}

	def findTheRows(){

		int size = this.driver.findElements(By.tagName("iframe")).size()

		println (".....Number of frames: " + size as String)

		this.driver.switchTo().frame(0)

		WebElement table = this.driver.findElement(By.id(gridId))

		List<WebElement> rows_table = table.findElements(By.className(rowClass))

		int rows_count = rows_table.size()

		println (".....Number of table rows: " + rows_count as String)

		return rows_table
	}

	def returnDesiredTableRow(String fullName){

		def desiredRow, foundName, tableRows, message

		def tableRowsSelector = "[class*=\"PagerDataRow\"]"

		def fullNameSelector = ".text.fullNameColumn span"

		this.driver.switchTo().frame("autoalertiframe")

		tableRows = findElements(By.cssSelector(tableRowsSelector))

		desiredRow = tableRows && fullName == "" ? tableRows[0] : null

		if(!desiredRow){

			for(int i = 0; i < tableRows.size(); i++){

				foundName = (tableRows[i].findElement(By.cssSelector(fullNameSelector))).getText()

				if(foundName == fullName){

					desiredRow = tableRows[i]

					break
				}
			}
		}

		message = desiredRow ? "" : "not "

		KeywordUtil.logInfo("Table row " + message + "found for " + (fullName ? fullName : "first available"))

		this.driver.switchTo().defaultContent()

		desiredRow
	}

	@Keyword
	def checkIfFlagged(fullName){

		def flagged, dataValue

		def flagSelector = "[data-action-id=\"watch\"]"

		def desiredRow = returnDesiredTableRow(fullName)

		this.driver.switchTo().frame("autoalertiframe")

		def flag = findElements(By.cssSelector(flagSelector), desiredRow)

		if(flag.size() > 0){

			dataValue = (getAttribute(flag[0], "data-value")).toLowerCase()

			flagged = (dataValue == 'true') ? true : false
		}
		else{

			flagged = null
		}

		this.driver.switchTo().defaultContent()

		flagged
	}

	@Keyword
	def returnTotalFoundFromSearch(){

		// This code is almost exactly like the same method in Search.  Need to refactor.

		def testObject = returnTestObject('Total Number in Opportunities Search Bottom Text')

		waitForElementVisible(testObject, 10)

		def bottomSearchNumberText = getText(testObject)

		KeywordUtil.logInfo("Search Number Text (Full): " + bottomSearchNumberText + ", Length: " + bottomSearchNumberText.size() as String)

		def trimmedNumberText = bottomSearchNumberText.trim().replaceAll("\u00A0", "");

		KeywordUtil.logInfo("Search Number Text (Trimmed): " + trimmedNumberText + ", Length: " + trimmedNumberText.size() as String)

		def subTrimmedNumberText = trimmedNumberText.substring(trimmedNumberText.lastIndexOf(" ") + 1)

		KeywordUtil.logInfo("Search Number Text (SubTrimmed): " + subTrimmedNumberText + ", Length: " + subTrimmedNumberText.size() as String)

		subTrimmedNumberText as Integer
	}

	@Keyword
	def findDealsheetWithOffer(){

		def sql = new Queries()

		def dealsheet = new dealsheet.Common()

		def offerFound = false

		def query = "select top 1 aac.URL from AutoAlert_Calculation.dbo.vw_Search aac" + " where " +
				"aac._DealerID=4024" + " and " +
				"aac.[Replacement New Used]='New'" + " and " +
				"aac.[New Used]='Used'"

		def possibleOfferUrls = sql.executeQuery(query)

		while (possibleOfferUrls.next()) {

			def url = possibleOfferUrls.getString("URL")

			navigateToUrl(url)

			dealsheet.waitToLoad()

			def offers = findElements(By.cssSelector('[title="Print Offer Statement"]'), 2)

			if(offers.size() > 0){

				println(url + "\nOffer found!")

				offerFound = true

				break
			}
			else{

				println(url + "\nOffer not found")
			}
		}

		offerFound
	}

	@Keyword
	def clickFirstRow(){

		def row = returnDesiredTableRow("")

		this.driver.switchTo().frame("autoalertiframe")
		
		def by = By.cssSelector('td')
		
		def rowCells = findElements(by, row)
		
		if(rowCells){
			
			click(rowCells[0])
		}
		else{

			click(row)			
		}

		this.driver.switchTo().defaultContent()
	}
}