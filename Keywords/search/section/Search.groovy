package search.section

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

public class Search extends WrappedSelenium{

	@Keyword
	def checkAlert(Alerts alert){

		setAlert(alert, true)
	}

	@Keyword
	def uncheckAlert(Alerts alert){

		setAlert(alert, false)
	}

	def setAlert(Alerts alert, Boolean checked){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def description = alert.getValue()['description']

			def letterBlock = alert.getValue()['letter']

			def alerts = getAlerts()

			if(checked){

				successful = check(alerts[letterBlock])
			}
			else{

				successful = uncheck(alerts[letterBlock])
			}

			KeywordUtil.logInfo(logHeader + (checked ? 'Checking' : 'Unchecking') + ' the ' + description + ' alert WAS' + (successful ? ' ' : ' NOT ') + 'successful')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def getAlerts(){

		def logHeader, letterBlock

		def alerts = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def byContainers = By.cssSelector('ui-checkbox-list .floatl:nth-child(1) .searchCheckBoxListContainer')

			def byLetterBlocks = By.cssSelector('alert .alerts-letterblock')

			def byInputs = By.cssSelector('input')

			def containers = findElements(byContainers)

			if(containers){

				for(int i = 0 ; i < containers.size() ; i++){

					def alertInfo = getAlertInfo(containers[i])

					if(alertInfo['letterBlock']){

						letterBlock = alertInfo['letterBlock']
					}
					else{

						letterBlock = 	'null-' + alerts.size() as String
					}

					alerts.put(letterBlock, alertInfo['input'])
				}
			}
			else{

				throw new Exception('No opportunities found')
			}

			KeywordUtil.logInfo(logHeader + 'Alerts found: ' + alerts as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		alerts
	}

	def getAlertInfo(WebElement element){

		def logHeader, letterBlock, input

		def alertInfo = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def byLetterBlocks = By.cssSelector('alert .alerts-letterblock')

			def byInputs = By.cssSelector('input')

			def letterBlocks = findElements(byLetterBlocks, element, 1)

			if(letterBlocks){

				alertInfo['letterBlock'] = getText(letterBlocks[0])
			}
			else{

				alertInfo['letterBlock'] = null
			}

			def inputs = findElements(byInputs, element, 1)

			if(inputs){

				alertInfo['input'] = inputs[0]
			}
			else{

				alertInfo['input'] = null
			}

			alertInfo[letterBlock] = input

			KeywordUtil.logInfo(logHeader + 'Alert info found: ' + alertInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		alertInfo
	}

	def getDropdownOptions(TestObject testObject){

		def logHeader

		def optionsList

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('li')

			def options = findElements(by, testObject)

			if(options){

				optionsList = []

				for(int i = 0 ; i < options.size() ; i++){

					def optionInfo = []

					def optionText = getText(options[i])

					if(optionText){

						optionInfo.add(optionText)

						optionInfo.add(options[i])

						optionsList.add(optionInfo)
					}
				}
			}
			else{

				throw new Exception('No dropdown options found')
			}

			def numberOfOptions = optionsList ? optionsList.size() : 0

			KeywordUtil.logInfo(logHeader + (numberOfOptions as String) + ' options found: ' + optionsList as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		optionsList
	}
}