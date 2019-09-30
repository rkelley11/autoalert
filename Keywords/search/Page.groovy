package search

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

import general.*
import general.Enums.Alerts
import test.Assistant.AfterFailure
import dealsheet.bottom.TestObjects as DealsheetTestObjects

import sql.*
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit
import java.time.temporal.ChronoUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Page extends WrappedSelenium{

	@Keyword
	def expandSection(SearchPageSections section){

		toggleSection(section, true)
	}

	@Keyword
	def collapseSection(SearchPageSections section){

		toggleSection(section, false)
	}

	def toggleSection(SearchPageSections section, Boolean expand){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def enumValue = section.getValue()

			def testObject = enumValue['testObject']

			def classes = WebUI.getAttribute(testObject, 'class')

			if(classes){

				if((classes.contains('chevron-down') && !expand) || (classes.contains('chevron-right') && expand)){

					click(testObject)

					classes = WebUI.getAttribute(testObject, 'class')

					successful = (classes.contains('chevron-down') && expand) || (classes.contains('chevron-right') && !expand)

					KeywordUtil.logInfo(logHeader + enumValue['header'] + ' section was' + (successful ? ' ': ' NOT ') + 'successfully ' +
							(expand ? 'expanded.' : 'collapsed.'))
				}
				else{

					successful = true

					KeywordUtil.logInfo(logHeader + enumValue['header'] + ' section was already ' + (expand ? 'expanded' : 'collapsed') + '. Nothing done.')
				}
			}
			else{

				throw new Exception('Class attribute is empty')
			}

			KeywordUtil.logInfo(logHeader + '')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	enum SearchPageSections{
		SEARCH(['header':'Search', 'testObject': findTestObject('Object Repository/Search Page/Section/Search/Chevron')]),
		VEHICLE(['header':'Vehicle', 'testObject': findTestObject('Object Repository/Search Page/Section/Vehicle/Chevron')]),
		CONTRACT(['header':'Contract', 'testObject': findTestObject('Object Repository/Search Page/Section/Contract/Chevron')]),
		OPPORTUNITY(['header':'Opportunity', 'testObject': findTestObject('Object Repository/Search Page/Section/Opportunity/Chevron')]),

		def getValue

		SearchPageSections(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}