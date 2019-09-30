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
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class ChangeLog extends WrappedSelenium{

	@Keyword
	def getLog(Integer index){

		def logHeader

		def log

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def logs = getLogs()

			if(logs){

				def rowsSize = logs.size()

				KeywordUtil.logInfo(logHeader + (rowsSize as String) + " log" + (rowsSize == 1 ? "" : "s") + " found")

				if(rowsSize >= index){

					log = getLogDetails(logs[index - 1])
				}
				else{

					throw new Exception("Index outside of possible range (1" + (rowsSize == 1 ? "" : " - " + rowsSize as String) + ")")
				}
			}
			else{

				throw new Exception("No change logs found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		log
	}

	def getLogs(){

		def logHeader

		def logs

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('.change-log-wrapper tbody tr')

			logs = findElements(by)

			if(logs){

				def numberOfLogs = logs.size()

				KeywordUtil.logInfo(logHeader + (numberOfLogs as String) + " log" + (numberOfLogs == 1 ? "" : "s") + " found")
			}
			else{

				throw new Exception("No change logs found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		logs
	}

	def getLogDetails(WebElement log){

		def logHeader

		def logDetails = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('td')

			def fields = findElements(by, log)

			if(fields){

				def numberOfFields = fields.size()

				KeywordUtil.logInfo(logHeader + (numberOfFields as String) + " log field" + (numberOfFields == 1 ? "" : "s") + " found")

				for(int i = 0 ; i < numberOfFields ; i++){

					def ngBindAttribute = getAttribute(fields[i], 'ng-bind')

					def start = ngBindAttribute.indexOf('.') + 1

					def key = ngBindAttribute.substring(start, ngBindAttribute.length())

					def text = getText(fields[i])

					def value

					if(key.contains('localeDateTime')){

						key = 'localeDateTime'

						value = new Utilities().normalizeDateTime(text)
					}
					else{

						value = text
					}

					logDetails[key] = value
				}

				KeywordUtil.logInfo(logHeader + "Row details: " + logDetails as String)
			}
			else{

				throw new Exception("No change logs found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		logDetails
	}

	@Keyword
	def selectFilters(ArrayList filters){

		def logHeader

		def successful = false
		
		def successfulCounts = 0

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('[class*="qa-change-log-selection"]')

			def filterCheckboxes = findElements(by)

			if(filterCheckboxes){

				def numberOfFilters = filterCheckboxes.size()

				KeywordUtil.logInfo(logHeader + (numberOfFilters as String) + " filter" + (numberOfFilters == 1 ? "" : "s") + " found")

				def filtersString = (filters as String).toLowerCase()

				for(int i = 0 ; i < numberOfFilters ; i++){

					def filterText = getAttribute(filterCheckboxes[i], 'id')

					if(filtersString.contains(filterText.toLowerCase())){

						KeywordUtil.logInfo(logHeader + "Attempting to CHECK the " + filterText + " filter")

						def successfulCheck = check(filterCheckboxes[i])
						
						if(successfulCheck) successfulCounts += 1
					}
					else{

						KeywordUtil.logInfo(logHeader + "Attempting to UNCHECK the " + filterText + " filter")

						def successfulUncheck = uncheck(filterCheckboxes[i])

						if(successfulUncheck) successfulCounts += 1
					}
				}

				KeywordUtil.logInfo(logHeader + '# of filters: ' + (numberOfFilters as String) + ' vs. # of successful checks/unchecks: ' + (successfulCounts as String))
				
				successful = numberOfFilters == successfulCounts
			}
			else{

				throw new Exception("No change logs found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	enum ChangeLogFilters{
		OPPORTUNITY([]),
		ACTIVITY([]),
		CONTACTS([]),
		ACCESS([]),

		def getValue

		ChangeLogFilters(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}