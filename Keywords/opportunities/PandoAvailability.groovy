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

import general.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*
import groovy.time.*

import com.kms.katalon.core.util.KeywordUtil

public class PandoAvailability extends WrappedSelenium{

	def dropdownObjName = TestObjectNames.PandoAvailability.DROPDOWN

	def iconObjName = TestObjectNames.PandoAvailability.ICON

	def cancelButtonObjName = TestObjectNames.PandoAvailability.CANCEL_BUTTON

	def updateButtonObjName = TestObjectNames.PandoAvailability.UPDATE_BUTTON

	def notAvailableOptionObjName = TestObjectNames.PandoAvailability.NOT_AVAILABLE_OPTION

	def busyOptionObjName = TestObjectNames.PandoAvailability.BUSY_OPTION

	def busyForBoxObjName = TestObjectNames.PandoAvailability.BUSY_FOR

	def dropdownPopupObjName = TestObjectNames.PandoAvailability.POPUP

	@Keyword
	def getCurrentPandoAvailability(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def currentStatus

		def icon = returnTestObject(iconObjName)

		def availability = getAttribute(icon, "src")

		def status = availability.substring(availability.lastIndexOf('-') + 1, availability.size() - 4)

		KeywordUtil.logInfo(logHeader + "Availability found: " + (status as String))

		switch (status){

			case "available":

				currentStatus = Status.AVAILABLE

				break

			case "unavailable":

				currentStatus = Status.NOT_AVAILABLE

				break

			case "busy":

				currentStatus = Status.BUSY

				break

			default:

				currentStatus = Status.NOT_FOUND
		}

		KeywordUtil.logInfo("Pando availability: " + currentStatus as String)

		currentStatus
	}

	@Keyword
	def getCurrentPandoAvailabilityOLD(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def currentStatus

		def icon = returnTestObject(iconObjName)

		def availability = getAttribute(icon, "ng-if")

		def status = availability.substring(availability.lastIndexOf('.') + 1, availability.size())

		KeywordUtil.logInfo(logHeader + "Availability found: " + (status as String))

		switch (status){

			case "Available":

				currentStatus = Status.AVAILABLE

				break

			case "NotAvailable":

				currentStatus = Status.NOT_AVAILABLE

				break

			case "Busy":

				currentStatus = Status.BUSY

				break

			default:

				currentStatus = Status.NOT_FOUND
		}

		KeywordUtil.logInfo("Pando availability: " + currentStatus as String)

		currentStatus
	}

	def getCurrentPandoAvailabilityStatusFromDropdown(){

		def currentDropdownStatus, overallStatus, iconAndDropdownMatch

		def currentPandoAvailabilityIcon = getCurrentPandoAvailabilityFromIcon()

		if (currentPandoAvailabilityIcon == Status.NOT_AVAILABLE){

			currentDropdownStatus = Status.NOT_AVAILABLE.toString()
		}
		else{

			def icon = returnTestObject(iconObjName)

			def cancelButton = returnTestObject(cancelButtonObjName)

			click(icon)

			def attributeToCheck = "class"

			def classValueToHideOption = "ng-hide"

			def notAvailableOption = getAttribute(returnTestObject(notAvailableOptionObjName), attributeToCheck)

			def busyOption = getAttribute(returnTestObject(busyOptionObjName), attributeToCheck)

			if (notAvailableOption == classValueToHideOption && busyOption != classValueToHideOption){

				currentDropdownStatus = Status.BUSY.toString()
			}
			else{

				currentDropdownStatus = Status.AVAILABLE.toString()
			}

			click(cancelButton)
		}

		KeywordUtil.logInfo("Current Pando Availability from dropdown: " + currentDropdownStatus)

		iconAndDropdownMatch = currentPandoAvailabilityIcon.toString().trim() == currentDropdownStatus.toUpperCase().trim()

		KeywordUtil.logInfo("Current Pando Availability icon and dropdown match: " + iconAndDropdownMatch as String)

		if (iconAndDropdownMatch){

			overallStatus = currentPandoAvailabilityIcon
		}
		else{

			overallStatus = Status.MISMATCH
		}

		overallStatus
	}

	def getCurrentPandoAvailabilityFromIcon(){

		def currentIconStatus

		def icon = returnTestObject(iconObjName)

		def imageSource = getAttribute(icon, "ng-if")

		if(imageSource.contains("userAvailability.NotAvailable")){

			currentIconStatus = Status.NOT_AVAILABLE
		}
		else{

			if(imageSource.contains("userAvailability.Available")){

				currentIconStatus = Status.AVAILABLE
			}
			else{

				if(imageSource.contains("userAvailability.Busy")){

					currentIconStatus = Status.BUSY
				}
				else{

					currentIconStatus = Status.NOT_FOUND
				}
			}
		}

		KeywordUtil.logInfo("Current Pando Availability from icon: " + currentIconStatus as String)

		currentIconStatus
	}

	@Keyword
	def validateStatusReturnsToAvailable(int busyPeriod){

		def currentStatus, isAvailable, timeElapsed

		//def waitingTime = Math.round((((busyPeriod * 60) + 30) / 60) * 100.0) / 100.0 // Adding 50% to wait time

		def waitingTime = Math.round((((busyPeriod * 60)) / 60) * 100.0) / 100.0 // Matches busy period

		def timeStart = new Date()

		KeywordUtil.logInfo("Waiting " + (waitingTime as String) + " minute" + (waitingTime == 1 ? "" : "s") + " before checking availability")

		sleep((waitingTime * 60000) as Integer)

		def increaseWaitBy = 5

		def timeoutPeriod = new TimeDuration(0, busyPeriod + increaseWaitBy, 0, 0)

		while(currentStatus != Status.AVAILABLE && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriod)){

			currentStatus = getCurrentPandoAvailability()

			sleep(1000)
		}

		timeElapsed = TimeCategory.minus(new Date(), timeStart)

		isAvailable = currentStatus == Status.AVAILABLE

		if(isAvailable){

			KeywordUtil.logInfo("Status changed to AVAILABLE after " + timeElapsed + " from Busy Period of " + (busyPeriod as String) + " minute(s)")
		}
		else{

			KeywordUtil.logInfo("Status DID NOT change to AVAILABLE after waiting for " + timeElapsed)
		}

		isAvailable
	}

	@Keyword
	def selectPandoAvailability(Status desiredStatus, Reasons reason, String reasonDetail, int busyPeriod){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		try{

			def isPopupOpen, icon

			def by = By.cssSelector('#availabilityTogglerDropdown')

			def useDropdown = true

			def originalStatus = getCurrentPandoAvailability()

			KeywordUtil.logInfo(logHeader + "Desired pando availability : " + desiredStatus)

			KeywordUtil.logInfo(logHeader + "Original pando availability: " + originalStatus)

			if(originalStatus != desiredStatus || (originalStatus == desiredStatus && originalStatus == Status.BUSY)){

				isPopupOpen = getAttribute(returnTestObject(dropdownPopupObjName), "class").contains("open")

				KeywordUtil.logInfo(logHeader + "Pando Set Availability popup IS" + (isPopupOpen ? " " : " NOT ") + "open")

				if(!isPopupOpen){

					icon = returnTestObject(iconObjName)

					click(by)

					sleep(5000) // Waiting for clicking to have an effect.  Refactor to something better than sleep.

					switch(desiredStatus){

						case Status.AVAILABLE:

							/*if(originalStatus == Status.NOT_AVAILABLE){

								useDropdown = true // Single icon click already sets status to available
							}*/

							break

						case Status.BUSY:

							/*if(originalStatus == Status.NOT_AVAILABLE){

								def goodStatusChange = statusChanged(originalStatus)

								if(!goodStatusChange) throw new Exception("Unable to change pando availability status")

								click(by) // An additional click is needed to open popup
							}*/

							break

						case Status.NOT_AVAILABLE:

							break // Nothing additional needed

						default:

							throw new Exception("Status not found")
					}
				}

				if(useDropdown){

					makeDropdownSelectionAndUpdate(desiredStatus, reason, reasonDetail, busyPeriod)
				}

				waitForPopupToClose()

				def endStatus = getCurrentPandoAvailability()

				KeywordUtil.logInfo(logHeader + "End status    : " + endStatus as String)

				KeywordUtil.logInfo(logHeader + "Desired status: " + desiredStatus as String)

				def match = endStatus == desiredStatus

				KeywordUtil.logInfo(logHeader + "Match         : " + match as String)

				if(!match) selectPandoAvailability(desiredStatus, reason, reasonDetail, busyPeriod) //Having issues with status not taking effect right away
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}
	}

	@Keyword
	def selectPandoAvailabilityOLD(Status desiredStatus, Reasons reason, String reasonDetail, int busyPeriod){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		try{

			def isPopupOpen, icon

			def by = By.cssSelector('#availabilityTogglerDropdown')

			def useDropdown = true

			def originalStatus = getCurrentPandoAvailability()

			KeywordUtil.logInfo(logHeader + "Desired pando availability : " + desiredStatus)

			KeywordUtil.logInfo(logHeader + "Original pando availability: " + originalStatus)

			if(originalStatus != desiredStatus || (originalStatus == desiredStatus && originalStatus == Status.BUSY)){

				isPopupOpen = getAttribute(returnTestObject(dropdownPopupObjName), "class").contains("open")

				KeywordUtil.logInfo(logHeader + "Pando Set Availability popup IS" + (isPopupOpen ? " " : " NOT ") + "open")

				if(!isPopupOpen){

					icon = returnTestObject(iconObjName)

					click(by)

					sleep(5000) // Waiting for clicking to have an effect.  Refactor to something better than sleep.

					switch(desiredStatus){

						case Status.AVAILABLE:

							if(originalStatus == Status.NOT_AVAILABLE){

								useDropdown = false // Single icon click already sets status to available
							}

							break

						case Status.BUSY:

							if(originalStatus == Status.NOT_AVAILABLE){

								def goodStatusChange = statusChanged(originalStatus)

								if(!goodStatusChange) throw new Exception("Unable to change pando availability status")

								click(by) // An additional click is needed to open popup
							}

							break

						case Status.NOT_AVAILABLE:

							break // Nothing additional needed

						default:

							throw new Exception("Status not found")
					}
				}

				if(useDropdown){

					makeDropdownSelectionAndUpdate(desiredStatus, reason, reasonDetail, busyPeriod)
				}

				waitForPopupToClose()

				def endStatus = getCurrentPandoAvailability()

				KeywordUtil.logInfo(logHeader + "End status    : " + endStatus as String)

				KeywordUtil.logInfo(logHeader + "Desired status: " + desiredStatus as String)

				def match = endStatus == desiredStatus

				KeywordUtil.logInfo(logHeader + "Match         : " + match as String)

				if(!match) selectPandoAvailability(desiredStatus, reason, reasonDetail, busyPeriod) //Having issues with status not taking effect right away
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}
	}

	def waitForPopupToClose(){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def popupClosed = false

		try{

			def timeout = 15

			popupClosed = WebUI.waitForElementNotVisible(returnTestObject(updateButtonObjName), timeout)

			KeywordUtil.logInfo(logHeader + "Popup DID" + (popupClosed ? " " : " NOT ") + "close" + (popupClosed ? "" : " within " + (timeout as String) + " seconds"))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		popupClosed
	}

	def statusChanged(Status originalStatus){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def change = false

		try{

			def timeExpired = false

			def timeoutPeriod = new TimeDuration(0, 0, 10, 0)

			def timeStart = new Date()

			while(!change && !timeExpired){

				def currentStatus = getCurrentPandoAvailabilityFromIcon()

				change = originalStatus != currentStatus

				if(!change){

					timeExpired = isTimeExpired(timeStart, timeoutPeriod)

					if(!timeExpired){

						sleep(1000)
					}
				}
			}

			KeywordUtil.logInfo("Pando availability status DID" + (change ? " " : " NOT ") + "change" +
					(timeExpired ? (" before " + (timeoutPeriod as String) + " expired") : ""))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		change

	}

	def makeDropdownSelectionAndUpdate(Status desiredStatus, Reasons reason, String reasonDetail, int busyPeriod){

		def dropdown, updateButton, busyForBox, statusLabel

		dropdown = returnTestObject(dropdownObjName)

		updateButton = returnTestObject(updateButtonObjName)

		statusLabel = desiredStatus.getValue()

		KeywordUtil.logInfo("Desired status (enum) : " + desiredStatus)

		KeywordUtil.logInfo("Desired status (label): " + statusLabel)
		
		selectOptionByLabel(dropdown, statusLabel, false)

		if(desiredStatus != Status.AVAILABLE){

			selectReason(desiredStatus, reason)
		}

		if(desiredStatus == Status.BUSY){

			busyForBox = returnTestObject(busyForBoxObjName)

			clearText(busyForBox)

			sendKeys(busyForBox, busyPeriod as String)
		}

		click(updateButton)

		def popup = returnTestObject(dropdownPopupObjName)

		def isPopupOpen = true

		while(isPopupOpen){

			isPopupOpen = getAttribute(popup, "class").contains("open")
		}
	}

	def makeDropdownSelectionAndUpdateOLD(Status desiredStatus, Reasons reason, String reasonDetail, int busyPeriod){

		def dropdown, updateButton, busyForBox, statusLabel

		dropdown = returnTestObject(dropdownObjName)

		updateButton = returnTestObject(updateButtonObjName)

		statusLabel = desiredStatus.getValue()

		KeywordUtil.logInfo("Desired status (enum) : " + desiredStatus)

		KeywordUtil.logInfo("Desired status (label): " + statusLabel)

		selectOptionByLabel(dropdown, statusLabel, false)

		if(desiredStatus != Status.AVAILABLE){

			selectReason(desiredStatus, reason)
		}

		if(desiredStatus == Status.BUSY){

			busyForBox = returnTestObject(busyForBoxObjName)

			clearText(busyForBox)

			sendKeys(busyForBox, busyPeriod as String)
		}

		click(updateButton)

		def popup = returnTestObject(dropdownPopupObjName)

		def isPopupOpen = true

		while(isPopupOpen){

			isPopupOpen = getAttribute(popup, "class").contains("open")
		}
	}

	def selectReason(Status desiredStatus, Reasons reason){

		def nameValue, optionToSelect

		def optionTestObject

		switch(desiredStatus){

			case Status.NOT_AVAILABLE:

				nameValue = "unavailableOption"

				optionTestObject = returnTestObject(notAvailableOptionObjName)

				break

			case Status.BUSY:

				nameValue = "busyOption"

				optionTestObject = returnTestObject(busyOptionObjName)

				break

			default:

				throw new Exception("Status not understood")
		}

		switch(reason){

			case Reasons.WITHACUSTOMER:

				optionToSelect = 0

				break

			case Reasons.INAMEETING:

				optionToSelect = 1

				break

			case Reasons.ONBREAK:

				optionToSelect = 2

				break

			case Reasons.OTHER:

				optionToSelect = 3

				break

			default:

				throw new Exception("Reason not understood")
		}

		KeywordUtil.logInfo("")

		//def optionsList = findElements(By.name(nameValue), optionTestObject)

		def optionsList = findElements(By.name(nameValue))

		KeywordUtil.logInfo("Calling click(Web Element)")

		click(optionsList[optionToSelect])
	}

	def enum Status{
		AVAILABLE("Available"),
		BUSY("Busy"),
		NOT_AVAILABLE("Not Available"),
		MISMATCH("Mismatch"),
		NOT_FOUND("Not Found")

		def String getValue

		Status(String getValue){

			this.getValue = getValue
		}

		def getValue(){

			return getValue
		}
	}

	def enum Reasons{
		WITHACUSTOMER,
		INAMEETING,
		ONBREAK,
		OTHER,
		NONE
	}
}
