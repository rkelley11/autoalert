package dealsheet.bottom

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

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class AlertsAndScripts extends WrappedSelenium{

	def alertAndScriptsRowsBy = By.cssSelector(".dealsheet-body .ds-tab-area .alert-scripts .opportunities tbody tr")

	def phoneMessage = 'Phone Message'

	def scripts = [:]

	def scriptsHeadingObjName = TestObjectNames.AlertsAndScripts.Scripts.HEADING

	AlertsAndScripts(){

		this.scripts['A'] = ['Alert Script']
		this.scripts['E'] = ['Engaged Script']
		this.scripts['S'] = ['Service Script']
		this.scripts['W'] = ['Warranty Script']
		this.scripts['C'] = [
			'Contract End Script',
			'Lease End Script'
		]
		this.scripts['M'] = ['Mileage Script']
		this.scripts['P'] = ['Pending Service Script']
		this.scripts['I'] = ['In-Market Script']
		this.scripts['F'] = [
			'Alert Script'] // Alpana (5/24/19) to look into this to make sure it's not supposed to say 'Flex Script' instead
		this.scripts[this.phoneMessage] = ['Phone Script']
	}

	@Keyword
	def verifyEachOpportunity(){

		def logHeader

		def allOpportunitiesGood = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def scriptHeadingTestObject = returnTestObject(scriptsHeadingObjName)

			def allOpportunities = getAllOpportunities()

			if(allOpportunities){

				for (int i = 0 ; i < allOpportunities.size() ; i++){

					click(allOpportunities[i]['element'])

					def scriptHeading = getText(scriptHeadingTestObject)

					allOpportunitiesGood = this.scripts[allOpportunities[i]['type']].contains(scriptHeading)

					KeywordUtil.logInfo(logHeader + "Script heading IS" + (allOpportunitiesGood ? " " : " NOT ") + "correct")

					if(!allOpportunitiesGood) break // No need to continue if any script heading fails

						def classes = getAttribute(allOpportunities[i]['element'], 'class')

					allOpportunitiesGood = classes.contains('alert-active')

					KeywordUtil.logInfo(logHeader + "Tab IS" + (allOpportunitiesGood ? " " : " NOT ") + "active")

					if(!allOpportunitiesGood) break // Tab is expected to be active, so no need to continue if it is not active

						allOpportunitiesGood = getArrowStatus(allOpportunities[i]['element'])

					KeywordUtil.logInfo(logHeader + "Arrow/Triangle IS" + (allOpportunitiesGood ? " " : " NOT ") + "active")

					if(!allOpportunitiesGood) break // Arrow is expected to be active, so no need to continue if it is not active
				}
			}
			else{

				throw new Exception("No opportunities found")
			}

			def phoneMessageLast = allOpportunities[allOpportunities.size() - 1]['text'] == this.phoneMessage

			KeywordUtil.logInfo(logHeader + "Phone message IS" + (phoneMessageLast ? " " : " NOT ") + "the last opportunity")

			allOpportunitiesGood = phoneMessageLast

			KeywordUtil.logInfo(logHeader + "All opportunities ARE" + (allOpportunitiesGood ? " " : " NOT ") + "good")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allOpportunitiesGood
	}

	def getAllOpportunities(){

		def logHeader

		def opportunities = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			//def opportunityDetails = [:]

			def by = By.cssSelector('.ds-tab-body alert-script-type-select tbody tr')

			def alerts = findElements(by)

			if(alerts){

				for (int i = 0 ; i < alerts.size() ; i++){

					/*def classes = getAttribute(alerts[i], 'class')
					 opportunityDetails['element'] = alerts[i]
					 opportunityDetails['type'] = getOpportunityType(alerts[i])
					 opportunityDetails['text'] = getOpportunityText(alerts[i])
					 opportunities.add(opportunityDetails)*/

					def details = getDetails(alerts[i])

					opportunities.add(details)
				}

				KeywordUtil.logInfo(logHeader + "Opportunities found: " + opportunities.size() as String)
			}
			else{

				throw new Exception("No opportunities found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		opportunities
	}

	def getDetails(WebElement element){

		def logHeader

		def opportunityDetails = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def classes = getAttribute(element, 'class')

			opportunityDetails['element'] = element

			opportunityDetails['type'] = getOpportunityType(element)

			opportunityDetails['text'] = getOpportunityText(element)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		opportunityDetails
	}

	def getOpportunityText(WebElement element){

		def logHeader

		def opportunityText

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to find opportunity text")

			def by, textElement

			def phoneClass = 'qa-alert-script-select-phone'

			def classes = getAttribute(element, 'class')

			def phoneMessage = classes.contains(phoneClass)

			if(phoneMessage){

				by = By.cssSelector('[translate="phoneMessage"]')
			}
			else{

				by = By.cssSelector('alert-relevant-info span')
			}

			textElement = findElements(by, element)

			if(textElement){

				opportunityText = getText(textElement[0])
			}
			else{

				throw new Exception("No opportunity text found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		opportunityText
	}

	def getOpportunityType(WebElement element){

		def logHeader

		def opportunityType

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to determine opportunity type")

			def phoneClass = 'qa-alert-script-select-phone'

			def classes = getAttribute(element, 'class')

			def phoneMessage = classes.contains(phoneClass)

			if(phoneMessage){

				opportunityType = this.phoneMessage
			}
			else{

				opportunityType = getAlertLetter(element)
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		opportunityType
	}

	def getAlertLetter(WebElement element){

		def logHeader

		def alertLetter

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to find the opportunity/alert letter")

			def by = By.cssSelector('.alerts-letterblock')

			def alertLetters = findElements(by, element)

			if(alertLetters){

				alertLetter = getText(alertLetters[0])
			}
			else{

				throw new Exception("No opportunity/alert letter found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		alertLetter
	}

	def getArrowStatus(WebElement element){

		def logHeader

		def arrowActive = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to determine the arrow status")

			def js = new JavaScript()

			def javaScript = "return window.getComputedStyle(arguments[0]).getPropertyValue('display');"

			def arrowsBy = By.cssSelector('icon[class*="aa-icon-arrow"]')

			def arrows = findElements(arrowsBy, element)

			// Find the displayed arrow
			for(int x = 0 ; x < arrows.size() ; x++){

				def display = js.execute(arrows[x], javaScript)

				if(display != 'none'){

					def classes = getAttribute(arrows[x], 'class')

					arrowActive = classes.contains('arrowfilledin')

					break // Found displayed arrow icon, so no need to continue looking
				}
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		arrowActive
	}

	//Same method as what's in Alerts.  Need to refactor.
	//@Keyword
	def getAlertInfo(Alerts alert){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def alertInfo

		try{

			KeywordUtil.logInfo(logHeader + "Attempting to get " + alert.name() + " alert information")

			def relevantInfo = gatherAlertsRelevantInfo()

			def alertLetter = alert.getValue()['letter']

			alertInfo = relevantInfo[alertLetter]

			KeywordUtil.logInfo(logHeader + alert.name() + ": " + alertInfo)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		alertInfo
	}

	//@Keyword
	def gatherAlertsRelevantInfo(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def alertInfo = [:]

		try{

			def rows = findElements(this.alertAndScriptsRowsBy, 1)

			if(rows){

				def by = By.cssSelector(".alerts-letterblock")

				for(int i = 0 ; i < rows.size() ; i++){

					def letterBlock = findElements(by, rows[i], 1)

					if(letterBlock){

						def letter = getText(letterBlock[0])

						def relevantInfoBy = By.cssSelector('alert-relevant-info')

						def relevantInfoElement = findElements(relevantInfoBy, rows[i], 1)

						def relevantInfo = getText(relevantInfoElement[0])

						alertInfo[letter] = relevantInfo
					}
				}

				KeywordUtil.logInfo(logHeader + "Relevant alert information was gathered for " + (alertInfo.size() as String) + " alert" + (alertInfo.size() > 1 ? "s" : "") + " found")
			}
			else{

				throw new Exception("No rows found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "Alert info returned: " + alertInfo as String)

		alertInfo
	}
}
