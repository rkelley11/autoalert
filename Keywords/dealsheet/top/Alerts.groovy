package dealsheet.top

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

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Alerts extends WrappedSelenium{

	def alertsBy = By.cssSelector(".qa-ds-alerts .qa-alerts-opportunities .opportunity")

	//Same method as what's in AlertsAndScripts.  Need to refactor.
	@Keyword
	def getOpportunitiesDisplayed(){

		def logHeader

		def opportunitiesDisplayed = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def alertsDisplayed = findElements(this.alertsBy, 1)

			if(alertsDisplayed){

				def numberOfAlerts = alertsDisplayed.size()

				for(int i = 0 ; i < numberOfAlerts ; i++){

					def opportunityText = getText(alertsDisplayed[i])

					opportunitiesDisplayed.add(opportunityText)
				}

				KeywordUtil.logInfo(logHeader + "Opportunities displayed: " + opportunitiesDisplayed as String)
			}
			else{

				throw new Exception("No opportunities displayed")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		opportunitiesDisplayed
	}

	@Keyword
	def areOpportunitiesDisplayed(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def alertsDisplayed = findElements(this.alertsBy, 1)

		def alertsFound = alertsDisplayed.size() > 0

		KeywordUtil.logInfo(logHeader + "Alerts WERE" + (alertsFound ? " " : " NOT ") + "found")

		alertsFound
	}

	//Same method as what's in AlertsAndScripts.  Need to refactor.
	@Keyword
	def getAlertInfo(Enums.Alerts alert){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def alertInfo

		try{

			KeywordUtil.logInfo(logHeader + "Attempting to get " + alert.name() + " alert information")

			def relevantInfo = gatherRelevantInfo()

			def alertLetter = alert.getValue()['letter']

			alertInfo = relevantInfo[alertLetter]

			KeywordUtil.logInfo(logHeader + alert.name() + ": " + alertInfo)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		alertInfo
	}

	@Keyword
	def gatherRelevantInfo(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def alertInfo = [:]

		try{

			def by = By.cssSelector(".alerts-letterblock")

			def alerts = findElements(this.alertsBy, 1)

			if(alerts){

				def alertSize = alerts.size()

				for(int i = 0 ; i < alertSize ; i++){

					def letterBlock = findElements(by, alerts[i])

					if(letterBlock){

						def letter = getText(letterBlock[0])

						def title = getAttribute(letterBlock[0], 'title')

						alertInfo[letter] = title
					}
				}

				KeywordUtil.logInfo(logHeader + "Tooltip information was gathered for " + (alertSize as String) + " alert" + (alertSize > 1 ? "s" : "") + " found")
			}
			else{

				throw new Exception("No alerts found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "Alert info returned: " + alertInfo as String)

		alertInfo
	}
}
