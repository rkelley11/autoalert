package reports

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

import java.time.*
import java.time.temporal.TemporalAdjusters
import java.time.format.DateTimeFormatter

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*
import general.Enums.Switch

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

public class MonthlyPerformance extends WrappedSelenium{

	def title1 = TestObjectNames.Report.MonthlyPerformance.TITLE1

	def link = TestObjectNames.Report.MonthlyPerformance.LINK

	def dateDropdown = TestObjectNames.Report.MonthlyPerformance.DATE_DROPDOWN

	def driver

	MonthlyPerformance(){

		this.driver = DriverFactory.getWebDriver()
	}

	/**
	 * 
	 * @return returns true if title of the Monthly Performance Report is correct, false if not
	 */
	@Keyword
	def isTitleCorrect(){

		def logHeader, isCorrect

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def formatter = DateTimeFormatter.ofPattern("M/d/YYYY")

			def previousMonth = LocalDate.now().minusMonths(1)

			def start = formatter.format(previousMonth.with(TemporalAdjusters.firstDayOfMonth()))

			def end = formatter.format(previousMonth.with(TemporalAdjusters.lastDayOfMonth()))

			def expectedTitle = "YOUR PERFORMANCE AS OF " + (start as String) + " - " + (end as String)

			KeywordUtil.logInfo(logHeader + "Expected title: " + expectedTitle)

			def actualTitle = getText(returnTestObject(title1))

			isCorrect = actualTitle == expectedTitle

			KeywordUtil.logInfo(logHeader + "Actual & Expected titles DO" + (isCorrect ? " " : " NOT ") + "match")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		isCorrect
	}

	/**
	 * 
	 * @return returns true if submitted linkText is correct, false if not
	 */
	@Keyword
	def isLinkTextCorrect(){

		def logHeader, textIsCorrect

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def title = "MONTHLY PERFORMANCE REPORT"

			def previousDate = LocalDateTime.now().minusMonths(1)

			def previousMonth = previousDate.getMonth()

			def previousYear = previousDate.getYear()

			def expectedText = title + "\n" + previousMonth + " " + previousYear

			KeywordUtil.logInfo(logHeader + "Expected link text:\t" + expectedText)

			def actualText = getText(returnTestObject(link))

			KeywordUtil.logInfo(logHeader + "Actual link text:\t" + actualText)

			textIsCorrect = actualText == expectedText

			KeywordUtil.logInfo(logHeader + "Submitted link text IS" + (textIsCorrect ? " " : " NOT ") + "correct")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		textIsCorrect
	}

	/**
	 * 
	 * @return returns true if date dropdown is correct, false if not
	 */
	@Keyword
	def isDateDropdownCorrect(){

		def logHeader, isCorrect

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def expectedList = []

			def today = LocalDateTime.now()

			for (int i = 1 ; i <= 6 ; i++){

				def previousDate = today.minusMonths(i)

				def previousMonth = previousDate.getMonth() as String

				previousMonth = previousMonth.substring(0,1).toUpperCase() + previousMonth.substring(1).toLowerCase();

				def previousYear = previousDate.getYear() as String

				expectedList.add(previousMonth + " " + previousYear)
			}

			KeywordUtil.logInfo(logHeader + "Expected date dropdown list: " + expectedList as String)

			def actualList = trimList(getAllOptions(returnTestObject(dateDropdown)))

			KeywordUtil.logInfo(logHeader + "Actual date dropdown list:\t" + actualList)

			isCorrect = expectedList.containsAll(actualList) // Handles dropdowns with less than 6 previous month options

			KeywordUtil.logInfo(logHeader + "Submitted date dropdown list IS" + (isCorrect ? " " : " NOT ") + "correct")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		isCorrect
	}

	/**
	 * @params testObject - Pass in the TestObject needed
	 * @return returns true if the current url matches the expected url, false if not
	 */
	@Keyword
	def verifyLearnMoreOpensNewWindow(TestObject testObject){

		def windowHandles, numberOfWindowsBeforeClicking, numberOfWindowsAfterClicking, currentUrl, logHeader

		def correctUrl = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			windowHandles = this.driver.getWindowHandles()

			numberOfWindowsBeforeClicking = windowHandles.size()

			KeywordUtil.logInfo(logHeader + "Number of window handles found BEFORE clicking Learn More: " + numberOfWindowsBeforeClicking as String)

			click(testObject)

			windowHandles = this.driver.getWindowHandles()

			numberOfWindowsAfterClicking = windowHandles.size()

			KeywordUtil.logInfo(logHeader + "Number of window handles found AFTER clicking Learn More: " + numberOfWindowsAfterClicking as String)

			this.driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 1])

			currentUrl = this.driver.getCurrentUrl()

			KeywordUtil.logInfo(logHeader + "Current Url : " + currentUrl)

			def expectedUrl = determineLearnMoreExpectedUrl(testObject)

			correctUrl = currentUrl == expectedUrl

			KeywordUtil.logInfo(logHeader + "Current and Expected Url's DO" + (correctUrl ? " " : " NOT ") + "match")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}
		finally{

			this.driver.close()

			this.driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 2])
		}

		correctUrl
	}

	def determineLearnMoreExpectedUrl(TestObject testObject){

		def logHeader, expectedUrl, linkName

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def expectedUrlOne2One = 'https://www.autoalert.com/intelligent-marketing-automotive-dealership-digital-mailer/'

			def expectedUrlRepPro = 'https://www.autoalert.com/reputation-management-automotive-dealership-online-google-yelp-reviews/'

			def linkOne2One = 'One to One'

			def linkRepPro = 'Reputation Pro'

			def objectId = testObject.getObjectId()

			if(objectId.contains(linkOne2One)){

				linkName = linkOne2One
			}
			else if(objectId.contains(linkRepPro)){

				linkName = linkRepPro
			}

			switch(linkName){

				case linkOne2One:

					expectedUrl = expectedUrlOne2One

					break

				case linkRepPro:

					expectedUrl = expectedUrlRepPro

					break

				default:

					throw new Exception("TestObject not recognized as a Learn More link.  No expected url returned.")
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		if(expectedUrl) KeywordUtil.logInfo(logHeader + "Expected Url: " + expectedUrl as String)

		expectedUrl
	}
}
