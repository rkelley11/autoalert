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

import general.*

import java.util.logging.*
import javax.swing.JOptionPane

import groovy.time.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Login extends WrappedSelenium{

	def usernameObjName = "input_username"

	def passwordObjName = "input_password"

	def loginButtonObjName = "button_login"

	def quickLinksHeaderObjName = "Quick Links - Header"

	def housekeeper, testObjects

	Login(){

		this.housekeeper = new Housekeeping()

		GlobalVariable.SessionId = DriverFactory.getWebDriver().getSessionId() as String

		KeywordUtil.logInfo("Session ID: " + GlobalVariable.SessionId)
	}

	@Keyword
	def toAutoAlert(String username, String password, Boolean clearDialogs){

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def waitingToComplete = true

			def usernameField = returnTestObject(usernameObjName)
			def passwordField = returnTestObject(passwordObjName)
			def loginButton = returnTestObject(loginButtonObjName)

			clearDialogs = clearDialogs ? clearDialogs : true

			WebUI.setText(usernameField, username);

			WebUI.setEncryptedText(passwordField, password)

			WebUI.click(loginButton, FailureHandling.STOP_ON_FAILURE)

			def timeExpired = false

			def elementsHaveBeenFound = false

			def timeoutPeriod = new TimeDuration(0,0,10,0)

			def timeStart = new Date()

			while(!elementsHaveBeenFound && !timeExpired){

				def by = getByLocator(returnTestObject(quickLinksHeaderObjName))

				def elementsFound = findElements(by, 1)

				if(elementsFound){

					elementsHaveBeenFound = true
				}
				else{

					timeExpired = isTimeExpired(timeStart, timeoutPeriod)
				}
			}

			if(elementsHaveBeenFound){

				if (clearDialogs){

					this.housekeeper.clearDialog()
				}
			}
			else{

				def driver = DriverFactory.getWebDriver()

				driver.quit()

				WebUI.openBrowser(GlobalVariable.Url_Login)

				if (GlobalVariable.Maximize_Browsers) WebUI.maximizeWindow()

				toAutoAlert(username, password, true)
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}
	}

	def readySetGo() {

		if(RunConfiguration.getExecutionProfile() == 'default'){

			JOptionPane.showMessageDialog(null, "Please select an environment and re-start test(s)", "Environment Not Selected", JOptionPane.WARNING_MESSAGE)

			System.exit(0)
		}

		def username = GlobalVariable.Username_TestSuite ? GlobalVariable.Username_TestSuite : GlobalVariable.Username

		def password = GlobalVariable.Password_TestSuite ? GlobalVariable.Password_TestSuite : GlobalVariable.Password

		KeywordUtil.logInfo("Credentials being used - Username: " + username + ", Password: " + password)

		def driver, foundTestObject, attempts

		def headerFound = false

		try{

			driver = DriverFactory.getWebDriver()

			KeywordUtil.logInfo("Driver exists - no need to login")

			//Start at home page

			driver.navigate().to(GlobalVariable.Url_Login)

			//def testObject = findTestObject('Header/AutoAlert Logo/AutoAlert Logo Image') // Failed when FMDEMO was selected.  Different Logo.

			def testObject = findTestObject('Header/Dealer Select/Dealer Select - Link')

			foundTestObject = WebUI.waitForElementVisible(testObject, 10)

			if(!foundTestObject){

				driver.quit()  //Workaround for now. Might be better to find why logo is not visible.  On wrong page?  @AfterTestCase to make sure state is set instead?

				throw new Exception("AutoAlert Logo not found")
			}
		}
		catch (Exception e){

			KeywordUtil.logInfo("Driver doesn't exist - need to login")

			WebUI.openBrowser(GlobalVariable.Url_Login)

			if (GlobalVariable.Maximize_Browsers) WebUI.maximizeWindow()

			toAutoAlert(username, password, true)
		}

		WebUI.waitForPageLoad(20)  // Waiting for initial page to load completely before attempting to move on
	}
}
