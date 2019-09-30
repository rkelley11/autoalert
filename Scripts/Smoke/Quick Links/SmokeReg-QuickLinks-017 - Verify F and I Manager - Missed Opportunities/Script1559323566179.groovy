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
import com.kms.katalon.core.util.KeywordUtil
import begin.Initialize

// Login
new Initialize().readySetGo()

// Verify Quick Link
def quickLink = findTestObject('Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - FI Manager')

def elementVisible = WebUI.waitForElementVisible(quickLink, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if(elementVisible){
	def elementText = WebUI.getText(quickLink)
	
	WebUI.verifyEqual(elementText, 'F&I Manager', FailureHandling.CONTINUE_ON_FAILURE)
	
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(quickLink)
	
	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify Heading
def heading = findTestObject('Opportunities/Alert Desk/F and I Manager/F and I Manager Header')

elementVisible = WebUI.waitForElementVisible(heading, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if(elementVisible){
	def elementText = WebUI.getText(heading)
	
	WebUI.verifyEqual(elementText, 'F&I Manager', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify tab
def tab = findTestObject('Opportunities/Alert Desk/F and I Manager/Missed Opportunities/F and I Manager - Missed Opportunities - Tab')

elementVisible = WebUI.waitForElementVisible(tab, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if(elementVisible){
	def elementText = WebUI.getText(tab)
	
	WebUI.verifyEqual(elementText, 'Missed Opportunities', FailureHandling.CONTINUE_ON_FAILURE)
	
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(tab)
	
	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify tab is active
def classes = WebUI.getAttribute(tab, "class")

KeywordUtil.logInfo("Tab classes: " + classes as String)
	
WebUI.verifyEqual(classes.contains('ui-tabs-active'), true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify results
def numberOfRowsShown = CustomKeywords.'opportunities.alertdesk.Common.returnNumberOfDataRows'()

KeywordUtil.logInfo("Number of rows showing: " + numberOfRowsShown as String)
	
WebUI.verifyEqual(numberOfRowsShown >= 0, true, FailureHandling.CONTINUE_ON_FAILURE)