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

import general.*

import org.openqa.selenium.MutableCapabilities
import org.openqa.selenium.chrome.ChromeOptions

class RemoteCapabilities {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def initializingRemoteCapabilities(TestCaseContext testCaseContext) {
		
		try{
			
			GlobalVariable.Browser = (RunConfiguration.getDriverPreferencesProperties()["WebUI"]["browserName"]).toLowerCase()
			
			def testCaseName = getTestCaseName(testCaseContext.getTestCaseId())
			
			MutableCapabilities sauceCaps = new MutableCapabilities()
			
			sauceCaps.setCapability("name", testCaseName)
			
			//sauceCaps.setCapability("extendedDebugging", true) // Uncomment when Sauce Labs fixes error
			
			if(GlobalVariable.Browser == "googlechrome"){
				
				ChromeOptions chOpts = new ChromeOptions();
				
				chOpts.setExperimentalOption("w3c", true);
				
				RunConfiguration.setWebDriverPreferencesProperty(ChromeOptions.CAPABILITY, chOpts)
			}
			else{
				
				sauceCaps.setCapability("username", GlobalVariable.SauceLabs_Username)
				
				sauceCaps.setCapability("accessKey", GlobalVariable.SauceLabs_AccessKey)
				
				sauceCaps.setCapability("seleniumVersion", "3.11.0")
			}
			
			RunConfiguration.setWebDriverPreferencesProperty('sauce:options', sauceCaps)
		}
		catch (Exception e){
			
			//Not running remote causes exception.  Better ways to handle this, but may refactor later.
		}
	}
	
	def getTestCaseName(String testCaseName){
		
			new TestObjects()  // This is so GlobalVariable.GoodSlash will be generated first
			
			def start
						
			start = testCaseName.lastIndexOf(GlobalVariable.GoodSlash) + 1 // Only includes test case name
			
			testCaseName.substring(start, testCaseName.size())
	}
}