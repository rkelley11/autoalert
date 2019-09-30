package general

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.Utilities
import general.TestObjectNames as GeneralTestObjectNames
import dealsheet.TestObjectNames as DealsheetTestObjectNames

public class Housekeeping extends WrappedSelenium{

	def dialogOkButtonObjName = GeneralTestObjectNames.Housekeeping.Dialog.OK

	def driver, className

	def Housekeeping(){

		this.className = this.class.getName()

		this.driver = DriverFactory.getWebDriver()
	}

	def clearDialogOLD(){

		def okButton = returnTestObject(dialogOkButtonObjName)

		KeywordUtil.logInfo(this.className + " - Test Object Returned: " + dialogOkButtonObjName)

		def dialogIsVisible = waitForElementVisible(okButton, 10)

		KeywordUtil.logInfo(this.className + " - Announcement dialog present: " + dialogIsVisible as String)

		if (dialogIsVisible){

			if (verifyElementClickable(okButton)){

				KeywordUtil.logInfo(this.className + " - Clicked OK on Announcement dialog")

				WebUI.click(okButton)
			}
		}
	}

	def clearDialog(){

		def logHeader

		def dialogCleared = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			//def changeMgmtLiveWebinarCancelButton = returnTestObject(TestObjectNames.Housekeeping.ChangeMgmtLiveWebinar.Buttons.CANCEL)

			//clearModal(changeMgmtLiveWebinarCancelButton) // Attempt to clear the Change Mgmt Live Webinar popup after login

			def announcementOkButton = returnTestObject(dialogOkButtonObjName)

			clearModal(announcementOkButton) // Attempt to clear the Announcement popup after login
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		dialogCleared
	}

	def clearModal(TestObject testObject){

		def logHeader

		def modalCleared = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def modalIsVisible = waitForElementVisible(testObject, 5)

			KeywordUtil.logInfo(this.className + " - Modal present: " + modalIsVisible as String)

			if (modalIsVisible){

				if (verifyElementClickable(testObject)){

					WebUI.click(testObject)

					KeywordUtil.logInfo(this.className + " - Cleared modal")
				}
			}

			modalCleared = true
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		modalCleared
	}

	def clearModalOLD(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def modalIsVisible = waitForElementVisible(testObject, 5)

		KeywordUtil.logInfo(this.className + " - Modal present: " + modalIsVisible as String)

		if (modalIsVisible){

			if (verifyElementClickable(testObject)){

				WebUI.click(testObject)

				KeywordUtil.logInfo(this.className + " - Cleared modal")
			}
		}
	}
}
