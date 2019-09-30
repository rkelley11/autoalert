package opportunities

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

import java.time.format.DateTimeFormatter as DateTimeFormatter
import groovy.time.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class DealerSelect extends WrappedSelenium{

	def dealerSelectObjName = TestObjectNames.DealerSelect.DEALER_SELECT_LINK

	def removeAllObjName = TestObjectNames.DealerSelect.REMOVE_ALL_LINK

	def inputTextboxObjName = TestObjectNames.DealerSelect.TEXTBOX

	def submitButtonObjName = TestObjectNames.DealerSelect.Button.SUBMIT

	def cancelButtonObjName = TestObjectNames.DealerSelect.Button.CANCEL

	def dealerResultsBy = By.cssSelector('.ui-select-choices li [ng-bind="dealer.displayText"]')

	def counter = 0

	def processCounter = 0

	def maxProcessAttempts = 3

	def selectDealer(String dealers){

		KeywordUtil.logInfo("Select Dealer 1")

		selectDealer([dealers])
	}

	@Keyword
	def selectDealer(List dealers){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def successfullDealerSelection = false

		try{

			KeywordUtil.logInfo(logHeader + "Attempting to select dealer" + (dealers.size() > 1 ? "s: ": ": ") + dealers as String)

			def dealersAlreadyMatch = matchesCurrentlySelectedDealerList(dealers)

			if(dealersAlreadyMatch){

				successfullDealerSelection = true

				KeywordUtil.logInfo(logHeader + "Current dealer selected already matches choice.  Nothing done.")
			}
			else{

				successfullDealerSelection = processDealerSelection(dealers)
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		if(successfullDealerSelection) GlobalVariable.Current_DealerCode = dealers

		successfullDealerSelection
	}

	def processDealerSelection(List dealers){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def successfulDealerSelection = false

		try{

			KeywordUtil.logInfo(logHeader + "Processing dealer selection")

			def dealerLinkClicked, removeAllLinkClicked, currentShortDealer

			def maxAttempts = 3

			def removeAllTestObject = returnTestObject(removeAllObjName)

			def inputTextboxTestObject = returnTestObject(inputTextboxObjName)

			def submitButtonTestObject = returnTestObject(submitButtonObjName)

			dealerLinkClicked = openDealerSelect()

			if(dealerLinkClicked){

				KeywordUtil.logInfo(logHeader + "Attempting to click the Remove All link")

				removeAllLinkClicked = click(removeAllTestObject)

				//Start of entering dealer name(s)

				for (String dealer : dealers){

					waitForElementVisible(inputTextboxTestObject, 10)

					clearText(inputTextboxTestObject)

					sendKeys(inputTextboxTestObject, dealer)

					def resultsList = findElements(dealerResultsBy, 3)

					if(resultsList.size() > 0){

						KeywordUtil.logInfo(logHeader + "Dealer results found.  Will attempt to select and submit.")

						click(resultsList[0])

						sleep(1000)
					}
					else{

						throw new Exception(logHeader + "No dealer results found")
					}
				}

				click(submitButtonTestObject)

				sleep(2000) //Put in place because there appears to be several overlays and then a full page reload after clicking submit

				//new Overlays().waitForOverlayToClear(By.cssSelector("div:not(reports) [class*=\"cg-busy cg-busy-animation ng-scope\"]"))

				sleep(5000) // Temp until above overlay wait is fixed

				//waitForElementVisible(returnTestObject(dealerSelectObjName))

				waitForDealerSelect() // Attempting to get passed the issue where it hitting submit for a dealer hangs on loading

				currentShortDealer = currentShortDealerSelected().toLowerCase()

				successfulDealerSelection = matchesCurrentlySelectedDealerList(dealers)

				if (!successfulDealerSelection){

					counter += 1

					if(counter < maxAttempts){

						KeywordUtil.logInfo(logHeader + "Attempts to change dealer: "  + (counter as String) + " of " + (maxAttempts as String) + ".  Re-attempting to change dealer.")

						selectDealer(dealers)
					}
					else{

						throw new Exception(logHeader + "Maximum attempts (" + (maxAttempts as String) + ") reached to change dealer without success.")
					}
				}

				GlobalVariable.Current_DealerCode = currentShortDealer
			}
			else{

				successfulDealerSelection = false
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())

			processCounter += 1

			if (processCounter <= maxProcessAttempts){

				KeywordUtil.logInfo(logHeader + "Attempt #" + (processCounter as String) + " failed at selecting dealer(s)")

				WebUI.refresh()

				successfulDealerSelection = processDealerSelection(dealers)
			}
			else{

				KeywordUtil.markWarning(logHeader + "Max number of attempts (" + (maxProcessAttempts as String) + ") to select dealer(s) reached")
			}
		}

		KeywordUtil.logInfo(logHeader + "Dealer selection WAS" + (successfulDealerSelection ? " " : " NOT ") + "successful")

		successfulDealerSelection
	}

	def waitForDealerSelect(){

		try{

			def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('.dealer-name-window .spinner .cg-busy-animation')

			def timeExpired = false

			def spinnerVisible = false

			def timeoutPeriod = new TimeDuration(0,0,3,0)

			def timeStart = new Date()

			while(!spinnerVisible && !timeExpired){

				KeywordUtil.logInfo(logHeader + "Waiting for Loading spinner to display")

				def spinners = findElements(by, 1)

				if(spinners){

					spinnerVisible = true
				}
				else{

					timeExpired = isTimeExpired(timeStart, timeoutPeriod)

					if(!timeExpired){

						KeywordUtil.logInfo(logHeader + "Sleep for 0.5 second before looking for Loading spinner again")

						sleep(500)
					}
				}
			}

			if(spinnerVisible){

				timeExpired = false

				timeoutPeriod = new TimeDuration(0,0,3,0)

				timeStart = new Date()

				while(spinnerVisible && !timeExpired){

					KeywordUtil.logInfo(logHeader + "Waiting for Loading spinner to disappear")

					def spinners = findElements(by, 1)

					if(!spinners){

						spinnerVisible = false
					}
					else{

						timeExpired = isTimeExpired(timeStart, timeoutPeriod)

						if(!timeExpired){

							KeywordUtil.logInfo(logHeader + "Sleep for 0.5 second before looking for Loading spinner again")

							sleep(500)
						}
					}
				}

				if(spinnerVisible){

					KeywordUtil.logInfo(logHeader + "Loading spinner appears to be stuck.  Refreshing browser.")

					WebUI.refresh()
				}
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}
	}

	def waitForDealerSelectOLD(){

		try{

			//def by = getByLocator(returnTestObject(dealerSelectObjName))

			def by = By.cssSelector('.dealer-name-window .spinner .cg-busy-animation')

			def dealerSelect = findElements(by, 3)

			if(!dealerSelect){

				WebUI.refresh()
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}
	}

	def matchesCurrentlySelectedDealerList(List dealers){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def isCorrect = false

		def dealerList

		dealers.sort()

		try{

			def multipleDealers = dealers.size() > 1

			if(multipleDealers){

				KeywordUtil.logInfo(logHeader + "Multiple dealers submitted")

				dealerList = getDealerList()
			}
			else{

				KeywordUtil.logInfo(logHeader + "Single dealer submitted")

				dealerList = [currentShortDealerSelected()]
			}

			def dealersSize = dealers.size()

			def dealerListSize = dealerList ? dealerList.size() : 0

			isCorrect = dealersSize == dealerListSize

			KeywordUtil.logInfo(logHeader + "Submitted dealer list size:\t" + (dealersSize as String))

			KeywordUtil.logInfo(logHeader + "Found dealer list size:\t\t" + (dealerListSize as String))

			if(isCorrect){

				def nameSplitList = splitShortAndFullDealerNames(dealerList)

				for(int i = 0; i < dealersSize; i++){

					KeywordUtil.logInfo(logHeader + "Dealer desired:\t" + dealers[i] as String)

					KeywordUtil.logInfo(logHeader + "Dealer found:\t\t" + nameSplitList[i]['short'] as String)

					isCorrect = dealers[i] == nameSplitList[i]['short']

					if(!isCorrect) break
				}
			}

			KeywordUtil.logInfo(logHeader + "Desired and found dealers DO" + (isCorrect ? " " : " NOT ") + "match")
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		isCorrect
	}

	def splitShortAndFullDealerNames(List dealers){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to split short name from full dealer name list")

		def shortName

		def dealerList = []

		for (String dealer : dealers){

			def dealerListDetails = [:]

			shortName = getShortDealerName(dealer)

			dealerListDetails['short'] = shortName

			dealerListDetails['full'] = dealer

			dealerList.add(dealerListDetails)
		}

		KeywordUtil.logInfo(logHeader + "Dealer List created: " + dealerList as String)

		dealerList
	}

	def openDealerSelect(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def successful = false

		try{

			def dealerSelectWindows

			def byDealerSelectLink = By.cssSelector(".dealer-name-link")

			def byDealerSelectWindow = By.cssSelector('.dealer-name-window')

			KeywordUtil.logInfo(logHeader + "Checking if Dealer Select Window is currently open")

			dealerSelectWindows = findElements(byDealerSelectWindow, 1)

			KeywordUtil.logInfo(logHeader + "Dealer Select Window is " + (dealerSelectWindows ? "OPEN" : "CLOSED"))

			if(!dealerSelectWindows){

				KeywordUtil.logInfo(logHeader + "Attempting to open the dealer select window")

				def dealerLinkClicked = click(byDealerSelectLink)

				if(dealerLinkClicked){

					KeywordUtil.logInfo(logHeader + "Verifying dealer select window is open")

					dealerSelectWindows = findElements(byDealerSelectWindow)

					successful = dealerSelectWindows ? true : false
				}
			}
			else{

				KeywordUtil.logInfo(logHeader + "Dealer select window already opened.  Doing nothing.")

				successful = true
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "Dealer select window IS" + (successful ? " " : " NOT ") + "open")

		successful
	}

	def getDealerList(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def dealerList = []

		def by = By.cssSelector('[ng-bind="$item.displayText"]')

		def opened = openDealerSelect()

		if(opened){

			def elements = findElements(by, 2)

			for(int i = 0; i < elements.size(); i++){

				def elementText = getText(elements[i])

				dealerList.add(elementText)
			}

			KeywordUtil.logInfo(logHeader + "Dealers currently selected: " + dealerList as String)

			click(returnTestObject(cancelButtonObjName))
		}
		else{

			KeywordUtil.markWarning(logHeader + "Unable to get currently selected dealer list")
		}

		dealerList
	}

	def getShortDealerName(String fullDealer){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def startIndex, endIndex, shortDealer

		KeywordUtil.logInfo(logHeader + "Attempting to get short name from " + fullDealer)

		try{
			
			if(fullDealer.contains('(') && fullDealer.contains(')')){

				startIndex = fullDealer.indexOf('(') + 1
	
				endIndex = fullDealer.indexOf(')')
	
				shortDealer = fullDealer.substring(startIndex, endIndex)
			}
			else{

				shortDealer = fullDealer				
			}

			KeywordUtil.logInfo(logHeader + "Returning: " + shortDealer as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		shortDealer
	}

	def currentShortDealerSelected(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to get current dealer short name")

		def dealerSelectTestObject = returnTestObject(dealerSelectObjName)

		def fullDealer = getText(dealerSelectTestObject)

		getShortDealerName(fullDealer)
	}
}