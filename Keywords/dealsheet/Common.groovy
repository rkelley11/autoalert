package dealsheet

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
import static Queries

import groovy.time.*
import java.util.concurrent.TimeUnit
import java.sql.ResultSet

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Common extends WrappedSelenium {

	def sql

	def spinnerTestObject = findTestObject('Object Repository/Dealsheet/Dealsheet Spinner')

	def dealerListTestObject = findTestObject('Object Repository/Dealsheet/Modals/Reassign Client/Reassign Client Modal - Unordered List of Dealers')

	def submitButtonTestObject = findTestObject('Object Repository/Dealsheet/Modals/Reassign Client/Reassign Client Modal - Button - Submit')

	def markAsSoldCheckboxTestObject = findTestObject('Object Repository/Dealsheet/Car Comparison Section/Existing Vehicle/Mark as Sold/Car Comparison - Existing Vehicle - Mark as Sold - Checkbox')

	def genericModalYesButtonTestObject = findTestObject('Object Repository/Dealsheet/Modals/Dealsheet Modal - Button - Yes')

	def dealsheetStatusTestObject = findTestObject('Object Repository/Dealsheet/Top Section/Status/Dealsheet - Status')

	def logActivityTabTestObject = findTestObject('Object Repository/Dealsheet/Top Section/Log Activity/Log Activity - Tab')

	def logActivityMarkAsSoldTestObject = findTestObject('Object Repository/Dealsheet/Top Section/Log Activity/Log Activity - Radio Button - Mark As Sold')

	def logActivitySubmitButtonTestObject = findTestObject('Object Repository/Dealsheet/Top Section/Log Activity/Log Activity - Button - Submit')

	Common(){

		this.sql = new Database()
	}

	@Keyword
	def determineVIN(String vin){

		def logHeader

		def scrubbedVIN

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def end = vin.indexOf(' (')

			if(end > 0){

				scrubbedVIN = vin.substring(0, end)
			}
			else{

				scrubbedVIN = vin
			}

			KeywordUtil.logInfo(logHeader + "VIN: " + scrubbedVIN)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		scrubbedVIN
	}

	@Keyword
	def reopen(){

		def logHeader, currentStatus

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('.reopen')

			def reopenLink = findElement(by)

			if(reopenLink){

				successful = click(reopenLink)

				if(successful){

					successful = acceptReopenDialog()

					if(successful){

						successful = waitToLoad()

						if(successful){

							currentStatus = getText(dealsheetStatusTestObject)

							successful = currentStatus == 'Open'
						}
						else{

							throw new Exception("Unable to successfully wait for the dealsheet to load")
						}
					}
					else{

						throw new Exception("Unable to successfully close the re-open dialog")
					}
				}
				else{

					throw new Exception("Unable to successfully click the re-open link")
				}
			}
			else{

				throw new Exception("Unable to locate the re-open link")
			}

			KeywordUtil.logInfo(logHeader + "Re-opening the dealsheet WAS" + (successful ? " " : " NOT ") + "successful." + (successful ? "" : " Current status: " + currentStatus))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def acceptReopenDialog(){

		def logHeader

		def successful

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			successful = click(genericModalYesButtonTestObject)

			KeywordUtil.logInfo(logHeader + "Re-opening the dealsheet WAS" + (successful ? " " : " NOT ") + "successful")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def selectRandomDropdown(TestObject testObject){

		def logHeader

		def selection

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def random = new Random()

			def allOptions = getAllOptions2(testObject)

			if(allOptions){

				def goodOptionSelected = false

				def selectedText, selectedValue

				while(!goodOptionSelected){

					def randomIndex = random.nextInt(allOptions.size())

					selection = allOptions[randomIndex]

					selectedText = selection['text']

					selectedValue = selection['value']

					if(selectedText.contains('Choose Option') || selectedValue == '-1'){

						KeywordUtil.markWarning(logHeader + 'Invalid option randomly selected: ' + selection as String)
					}
					else{

						goodOptionSelected = true
					}
				}

				selectOptionByLabel(testObject, selectedText, true)
			}

			KeywordUtil.logInfo(logHeader + "Selected option: " + selection as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		selection
	}

	@Keyword
	def selectBottomSectionTab(BottomSectionTabs tab){

		def successful

		def by = By.cssSelector("deal-sheet-tab-select ul li a")

		def waitingForTabToBecomeActive = true

		def availableTabs = findElements(by)

		def tabIndex = tab.getValue()

		def timeoutPeriod = new TimeDuration(0,0,10,0)

		def timeStart = new Date()

		while(waitingForTabToBecomeActive && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriod)){

			try{

				KeywordUtil.logInfo("Attempting to click on the " + (tab as String) + " tab (tab position: " + ((tabIndex + 1) as String) + ")")

				click(availableTabs[tabIndex])

				def classAttribute = getAttribute(availableTabs[tabIndex], "class")

				KeywordUtil.logInfo("Class attribute of tab: " + classAttribute as String)

				waitingForTabToBecomeActive = !(classAttribute == "tab-active")

				successful = waitingForTabToBecomeActive == false
			}
			catch(Exception e){

				KeywordUtil.markError("Exception thrown!")

				successful = false
			}
		}

		KeywordUtil.logInfo("Successfully clicked on the " + (tab as String) + " tab: " + (successful as String))

		successful
	}

	// NEED TO REFACTOR OUT AND REPLACE WITH waitForOverlayToClear() ON UTILITIES CLASS
	@Keyword
	def waitToLoad(){

		def spinnerClass, spinners

		def dealsheetLoaded, spinnerShowing = false

		def driver = DriverFactory.getWebDriver()

		def byLocator = getByLocator(spinnerTestObject)

		def timeStart = new Date()

		def timeoutPeriodToStart = new TimeDuration(0,0,3,0)

		def timeoutPeriodToStop = new TimeDuration(0,0,90,0)

		// Wait for spinner to start.  Sometimes there's a slight delay
		while (!spinnerShowing && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriodToStart)){

			spinners = driver.findElements(byLocator)

			if(spinners.size() > 0){

				spinnerClass = spinners[0].getAttribute("class")

				KeywordUtil.logInfo("Spinner classes: " + spinnerClass)

				if (!spinnerClass.contains("ng-hide")){

					spinnerShowing = true
				}
				else{

					sleep(1000) // Wait 1 second before polling again
				}
			}
		}

		timeStart = new Date()

		// Wait for spinner to stop
		while (!dealsheetLoaded && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriodToStop)){

			spinners = driver.findElements(byLocator)

			if(spinners.size() > 0){

				spinnerClass = spinners[0].getAttribute("class")

				KeywordUtil.logInfo("Classes: " + spinnerClass)

				if (spinnerClass.contains("ng-hide")){

					dealsheetLoaded = true
				}
				else{

					sleep(1000) // Wait 1 second before polling again
				}
			}
		}

		dealsheetLoaded
	}

	@Keyword
	def assignDealer(String dealerName){

		KeywordUtil.logInfo("Submitted dealer name: " + dealerName)

		def returnedDealerName = selectDealer(dealerName)

		click(submitButtonTestObject)

		waitToLoad()

		KeywordUtil.logInfo("Returned dealer name: " + returnedDealerName)

		returnedDealerName
	}

	def selectDealer(String dealerName){

		def byLocator = By.tagName("li")

		def listOfDealers = findElements(byLocator, dealerListTestObject)

		if (dealerName){

			listOfDealers.each{

				if(it.getText() == dealerName){

					click(it)
				}
			}
		}
		else{

			def random = new Random()

			def randomIndex = random.nextInt(listOfDealers.size())

			click(listOfDealers[randomIndex])

			dealerName = listOfDealers[randomIndex].getText()
		}

		dealerName
	}

	@Keyword
	def setStatus(DealsheetStatus status){

		def successful, elementVisible

		KeywordUtil.logInfo("Dealsheet status desired: '" + (status.getValue() as String) + "'")

		try{

			elementVisible = WebUI.waitForElementVisible(dealsheetStatusTestObject, 10)

			if (!elementVisible) throw new Exception()

			def originalStatus = getText(dealsheetStatusTestObject)

			KeywordUtil.logInfo("Dealsheet status original: '" + (originalStatus as String) + "'")

			KeywordUtil.logInfo("Dealsheet desired and current status are the same: " + ((status.getValue() == originalStatus) as String))

			if(status.getValue() != originalStatus){

				elementVisible = WebUI.waitForElementVisible(markAsSoldCheckboxTestObject, 10)

				if (!elementVisible) throw new Exception()

				click(markAsSoldCheckboxTestObject)

				def testObjectModalYes = genericModalYesButtonTestObject

				elementVisible = WebUI.waitForElementVisible(testObjectModalYes, 10)

				if (!elementVisible) throw new Exception()

				click(testObjectModalYes)

				waitToLoad()

				successful = true
			}
			else{

				successful = true
			}
		}
		catch (Exception e){

			KeywordUtil.logInfo("Exception thrown! " + e as String)

			successful = false
		}

		successful
	}

	@Keyword
	def setStatusViaLogActivity(DealsheetStatus status){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def successful = false

		def elementVisible

		KeywordUtil.logInfo("Dealsheet status desired: '" + (status.getValue() as String) + "'")

		try{

			def testObjectStatus = dealsheetStatusTestObject

			elementVisible = WebUI.waitForElementVisible(testObjectStatus, 10)

			if (!elementVisible) throw new Exception()

			def originalStatus = getText(testObjectStatus)

			KeywordUtil.logInfo("Dealsheet status original: '" + (originalStatus as String) + "'")

			KeywordUtil.logInfo("Dealsheet desired and current status are the same: " + ((status.getValue() == originalStatus) as String))

			if(status.getValue() != originalStatus){

				switch(status){

					case DealsheetStatus.OPEN:

						throw new Exception("Opening a dealsheet fromn Log Activity not an option")

						break

					case DealsheetStatus.MARKED_AS_SOLD:

						def logActivityTab = logActivityTabTestObject

						def logActivityMarkAsSold = logActivityMarkAsSoldTestObject

						def logActivitySubmitButton = logActivitySubmitButtonTestObject

						elementVisible = WebUI.waitForElementVisible(logActivityTab, 10)

						if (!elementVisible) throw new Exception()

						click(logActivityTab)

						elementVisible = WebUI.waitForElementVisible(logActivityMarkAsSold, 10)

						if (!elementVisible) throw new Exception()

						click(logActivityMarkAsSold)

						elementVisible = WebUI.waitForElementVisible(logActivitySubmitButton, 10)

						if (!elementVisible) throw new Exception()

						click(logActivitySubmitButton)

						break

					default:

						break
				}

				waitToLoad()

				successful = true
			}
			else{

				successful = true
			}
		}
		catch (Exception e){

			KeywordUtil.logInfo(logHeader + e as String)
		}

		successful
	}

	/**
	 * Runs a query to determine the database value of a selected detail.
	 * @param detail - Using the enum Details pass which detail to query
	 * @return A hash map containing existing details
	 */
	@Keyword
	def queryCurrentDatabaseValue(Enum detail){

		def results = [:]

		def query, columnNames

		def entityId = (new UpperIcons()).getEntityId()

		switch (detail){

			case ExistingVehicle.Details.TRIM_INFO:



				break

			case ExistingVehicle.Details.CONTRACT_INFO:



				break

			case ExistingVehicle.Details.RECENT_RO:



				break

			case ExistingVehicle.Details.SERVICE_APPT:



				break

			case ExistingVehicle.Details.MILEAGE:



				break

			case ExistingVehicle.Details.TRADE:

				columnNames = ["Trade Value"]

				query = "select top 1 [Trade Value] " +
						"from AutoAlert_Calculation.dbo.vw_Search vw " +
						"where vw._EntityID='" + entityId + "'"

				break

			case ExistingVehicle.Details.CASH_DOWN:



				break

			case ExistingVehicle.Details.PAYOFF:



				break

			case ExistingVehicle.Details.MATURITY_DATE:



				break

			case ExistingVehicle.Details.WARRANTY:



				break

			default:

				throw new Exception("Enum not understood: " + detail as String)
				break
		}

		results = this.sql.executeQuery(query, columnNames)

		KeywordUtil.logInfo("Query results: " + results as String)

		results
	}

	@Keyword
	def getRandomDealsheetURL(){

		def logHeader

		def url

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to get a random dealsheet url')

			def fullUrl = getRandomQueriedDealsheetData(Queries.Dealsheet.URLS, 'URL')

			if(fullUrl){

				def start = fullUrl.indexOf('.com/') + 4

				url = GlobalVariable.Url_Login + fullUrl.substring(start, fullUrl.length())

				KeywordUtil.logInfo(logHeader + 'Generated dealsheet url: ' + url)
			}
			else{

				throw new Exception('Random dealsheet URL not returned')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		url
	}

	@Keyword
	def getRandomQueriedDealsheetData(String query, String columnName){

		def logHeader

		def randomData

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to get random dealsheet data (' + columnName + ')')

			def sql = new Database()

			def randomGen = new RandomGenerator()

			def results = sql.executeQuery(query)

			if(results){

				def arrayResults = putResultsIntoArray(results, columnName)

				if(arrayResults){

					def randomStart, randomEnd

					if(GlobalVariable.TestSuiteId < 1){

						KeywordUtil.markWarning(logHeader + 'Test Suite Id is ' + (GlobalVariable.TestSuiteId as String) + ' and may not have been set')

						randomStart = 0

						randomEnd = arrayResults.size() - 1
					}
					else{

						randomStart = ((GlobalVariable.TestSuiteId - 1) * 100) > (arrayResults.size() - 1) ? (arrayResults.size() - 1) : (GlobalVariable.TestSuiteId - 1) * 100

						randomEnd = (randomStart + 99) > (arrayResults.size() - 1) ? arrayResults.size() - 1 : randomStart + 99
					}

					KeywordUtil.logInfo(logHeader + 'Random range: ' + (randomStart as String) + ' through ' + (randomEnd as String))

					def index = randomGen.generateRandomNumber(randomStart, randomEnd)

					randomData = arrayResults[index]
				}

				KeywordUtil.logInfo(logHeader + 'Random dealsheet data (' + columnName + '): ' + randomData as String)
			}
			else{

				throw new Exception('No results received from query')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		randomData
	}

	@Keyword
	def getRandomMVODealsheetURL(){

		def logHeader

		def url

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to get a random dealsheet url that contains a multiple vehicle offer')

			//def entityId = getRandomQueriedDealsheetData(Queries.Dealsheet.MULTIPLE_VEHICLE_OFFERS, 'EntityID') // Uses DealerId=1290 CHDEMO
			
			def entityId = getRandomQueriedDealsheetData(Queries.Dealsheet.MULTIPLE_VEHICLE_OFFERS_ANY_DEALER, 'EntityID')
			
			if(entityId){

				url = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.DEALSHEET + '/' + entityId

				KeywordUtil.logInfo(logHeader + 'Generated dealsheet url: ' + url)
			}
			else{

				throw new Exception('Random dealsheet URL not returned')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		url
	}

	def putResultsIntoArray(ResultSet results, String columnName){

		def logHeader

		def arrayResults = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			while(results.next()){

				arrayResults.add(results.getString(columnName))
			}

			KeywordUtil.logInfo(logHeader + 'Number of rows returned from query: ' + arrayResults.size() as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		arrayResults
	}

	@Keyword
	def generateDealsheetUrl(String entityId){

		def logHeader

		def url

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			url = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.DEALSHEET + '/' + entityId

			KeywordUtil.logInfo(logHeader + 'Generated dealsheet url: ' + url as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		url
	}

	enum DealsheetStatus{

		OPEN ("Open"),
		MARKED_AS_SOLD ("Marked as Sold")

		def String getValue

		DealsheetStatus(String getValue){

			this.getValue = getValue
		}

		def getValue(){

			return getValue
		}
	}

	enum BottomSectionTabs{

		ALERTS_AND_SCRIPTS(0),
		EXISTING_VEHICLE_DETAILS(1),
		REPLACEMENTS_DETAILS(2),
		FEATURE_COMPARISON(3),
		ACTIVITY_AND_NOTES(4),
		SERVICE_HISTORY(5),
		OTHER_VEHICLES_OWNED(6),
		CHANGE_LOG(7)

		def int getValue

		BottomSectionTabs(int getValue){

			this.getValue = getValue
		}

		def getValue(){

			return getValue
		}
	}
}
