package begin

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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

import internal.GlobalVariable

import general.UserAccounts
import general.UserAccounts.UserAccountField

import javax.swing.JOptionPane

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Initialize{

	def readySetGo() {

		def username = GlobalVariable.Username_TestSuite ? GlobalVariable.Username_TestSuite : GlobalVariable.Username

		readySetGo(username)
	}

	def readySetGo(String username) {

		def logHeader, password

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def userAccounts = new UserAccounts()

			password = userAccounts.getFieldValue(username, UserAccountField.PASSWORD)

			KeywordUtil.logInfo("Credentials being used - Username: " + username + ", Password: " + password)

			def driver = DriverFactory.getWebDriver()

			KeywordUtil.logInfo("Driver exists but will now attempt to determine if the currently logged in user is the same as the desired user")

			def currentUserGood = isCurrentlySignedInUserGood(userAccounts.getFieldValue(username, UserAccountField.NAME))

			if(!currentUserGood){

				throw new Exception('Wrong user signed-in.  Quitting browser and relogging in')
			}

			//Start at home page

			driver.navigate().to(GlobalVariable.Url_Login)

			def testObject = findTestObject('Object Repository/Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - Academy')

			def foundTestObject = WebUI.waitForElementVisible(testObject, 10)

			if(!foundTestObject){

				driver.quit()

				throw new Exception("Academy quick link not found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())

			KeywordUtil.logInfo("Driver doesn't exist - need to login")

			WebUI.openBrowser(GlobalVariable.Url_Login)

			if (GlobalVariable.Maximize_Browsers) WebUI.maximizeWindow(FailureHandling.CONTINUE_ON_FAILURE)

			new Login().toAutoAlert(username, password, true)
		}

		WebUI.waitForPageLoad(20)  // Waiting for initial page to load completely before attempting to move on
	}

	def isCurrentlySignedInUserGood(String name){

		def logHeader

		def currentlySignedInUserGood = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def testObject = findTestObject('Object Repository/Header/Profile/Username')

			def currentlySignedInUser = WebUI.getText(testObject)

			currentlySignedInUserGood = currentlySignedInUser.toLowerCase() == name.toLowerCase()

			KeywordUtil.logInfo(logHeader + 'The currently signed-in user (' + (currentlySignedInUser as String) + ') ' +
					(currentlySignedInUserGood ? 'matches' : 'does not match') + ' the desired user (' + (name) + ')')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		currentlySignedInUserGood
	}
}
