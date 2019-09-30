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

public class ManagePresets extends WrappedSelenium{

	@Keyword
	def getCurrentSearchLists(){

		def logHeader

		def searchLists = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def sectionsBy = By.cssSelector('search-preset-selector .available-search-presets-wrapper div')

			def headersBy = By.cssSelector('h4')

			def searchNamesBy = By.cssSelector('li label')

			def sections = findElements(sectionsBy)

			if(sections){

				for(int i = 0 ; i < sections.size() ; i++){

					def tempSearchNameList = []

					def headers = findElements(headersBy, sections[i], 1)

					def headerName = headers ? getText(headers[0]) : 'Section ' + ((i + 1) as String) + ' header not found'

					def searchNames = findElements(searchNamesBy, sections[i], 1)

					if(searchNames){

						for(int x = 0 ; x < searchNames.size() ; x++){

							def searchName = getText(searchNames[x])

							tempSearchNameList.add(searchName)
						}
					}

					searchLists[headerName] = tempSearchNameList
				}

				KeywordUtil.logInfo(logHeader + 'Search lists found: ' + searchLists as String)
			}
			else{

				throw new Exception('\'Add Opportunity Search\' sections not found')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		searchLists
	}

	enum MaybeNotNeeded{
		SHARE(['by': By.cssSelector('[icon="\'share\'"]')]),
		UNSHARE(['by':By.cssSelector('[icon="\'unshare\'"]')]),
		DELETE(['by':By.cssSelector('[icon="\'trash\'"]')])

		def getValue

		MaybeNotNeeded(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}