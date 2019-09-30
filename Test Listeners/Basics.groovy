import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import javax.swing.JOptionPane

class Basics {
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	//@BeforeTestCase
	def readySetGo(TestCaseContext testCaseContext) {
				
		def username = GlobalVariable.Username_TestSuite ? GlobalVariable.Username_TestSuite : GlobalVariable.Username
		
		def password = GlobalVariable.Password_TestSuite ? GlobalVariable.Password_TestSuite : GlobalVariable.Password
			
		KeywordUtil.logInfo("Credentials being used - Username: " + username + ", Password: " + password)
		
		def driver, foundTestObject, attempts
		
		def headerFound = false
		
		try{
			
			driver = DriverFactory.getWebDriver()
		
			KeywordUtil.logInfo("Driver exists - no need to login")
						
			driver.navigate().to(GlobalVariable.Url_Login)
						
			def testObject = findTestObject('Header/Dealer Select/Dealer Select - Link')
			
			foundTestObject = WebUI.waitForElementVisible(testObject, 10)
			
			if(!foundTestObject){
				
				driver.quit()
				
				throw new Exception("AutoAlert Logo not found")
			}
		}
		catch (Exception e){
			
			KeywordUtil.logInfo("Driver doesn't exist - need to login")
			
			WebUI.openBrowser(GlobalVariable.Url_Login)
		
			if (GlobalVariable.Maximize_Browsers) WebUI.maximizeWindow()
			
			CustomKeywords.'begin.Login.toAutoAlert'(username, password, true)			
		}
			
		WebUI.waitForPageLoad(20)  // Waiting for initial page to load completely before attempting to move on
	}	
}