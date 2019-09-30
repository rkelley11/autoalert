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

public class CertificationAndTraining extends WrappedSelenium{

	def driver

	CertificationAndTraining(){

		this.driver = DriverFactory.getWebDriver()
	}

	/**
	 * @params view - Enum View to be checked is passed in
	 * @return returns true if the columns are as expected, false if not
	 */
	@Keyword
	def areColumnsCorrect(View view){

		def logHeader

		def columnsAreCorrect = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def viewValues = view.getValue()

			def cssBuilder = "#" + viewValues['id'] + " thead tr th[role=\"columnheader\"]:not([style*=\"display: none\"]):not([style*=\"display:none\"])"

			def by = By.cssSelector(cssBuilder)

			def columns = findElements(by)

			def actualColumnNames = []

			for(int i = 0 ; i < columns.size() ; i++){

				def byName = By.cssSelector('a')

				def columnNameElements = findElements(byName, columns[i], 1)

				def columnName

				if (columnNameElements){

					columnName = getText(columnNameElements[0]).trim()
				}
				else{

					columnName = 'COLUMN NAME NOT FOUND'
				}

				actualColumnNames.add(columnName)
			}

			KeywordUtil.logInfo(logHeader + "Actual column names: " + actualColumnNames as String)

			def expectedColumnNames = determineExpectedColumns(view)

			columnsAreCorrect = actualColumnNames == expectedColumnNames

			KeywordUtil.logInfo(logHeader + "Actual & Expected column names ARE" + (columnsAreCorrect ? " " : " NOT ") + "the same")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		columnsAreCorrect
	}

	def determineExpectedColumns(View view){

		def logHeader

		def expectedColumnNames = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def viewValues = view.getValue()

			def viewColumnNames = viewValues['columnNames']

			if (view == View.USER && GlobalVariable.Current_DealerCode.size() == 1){

				for(int i = 0 ; i < viewColumnNames.size() ; i++){

					if (viewColumnNames[i] != 'Dealer'){

						expectedColumnNames.add(viewColumnNames[i])
					}
				}
			}
			else{

				expectedColumnNames = viewColumnNames
			}

			KeywordUtil.logInfo(logHeader + "Expected column names: " + expectedColumnNames as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		expectedColumnNames
	}

	enum View{
		USER(['id': 'certification-grid', 'columnNames': [
				'Name',
				'Dealer',
				'Date Enabled',
				'Live Webinars',
				'Recorded Webinars',
				'Certifications',
				'Certification Date',
				'Score'
			]]),
		WEBINAR(['id': 'webinar-attendance-grid', 'columnNames': [
				'Webinar',
				'Type',
				'Total Attended'
			]])

		def getValue

		View(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return getValue
		}
	}
}