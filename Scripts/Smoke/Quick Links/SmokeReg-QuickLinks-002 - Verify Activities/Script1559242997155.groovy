import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import begin.Initialize

// Login
new Initialize().readySetGo()

// Verify Activities Quick Link
def quickLink = findTestObject('Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - Activities')

def elementVisible = WebUI.waitForElementVisible(quickLink, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if(elementVisible){
	def elementText = WebUI.getText(quickLink)
	
	WebUI.verifyEqual(elementText, 'Activities', FailureHandling.CONTINUE_ON_FAILURE)
	
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(quickLink)
	
	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify Activities page Heading
def heading = findTestObject('Opportunities/Alert Desk/Activities/Activities Header')

elementVisible = WebUI.waitForElementVisible(heading, 10)

if(elementVisible){
	def elementText = WebUI.getText(heading)
	
	WebUI.verifyEqual(elementText, 'Activities', FailureHandling.CONTINUE_ON_FAILURE)
}