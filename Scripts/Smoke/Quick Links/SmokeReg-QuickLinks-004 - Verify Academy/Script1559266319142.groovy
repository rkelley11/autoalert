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

// Verify Quick Link
def quickLink = findTestObject('Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - Academy')

def elementVisible = WebUI.waitForElementVisible(quickLink, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if(elementVisible){
	def elementText = WebUI.getText(quickLink)
	
	WebUI.verifyEqual(elementText, 'Academy', FailureHandling.CONTINUE_ON_FAILURE)
	
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(quickLink)
	
	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify Heading
def heading = findTestObject('Opportunities/Academy/AutoAlert Academy Title')

elementVisible = WebUI.waitForElementVisible(heading, 10)

if(elementVisible){
	def elementText = WebUI.getText(heading)
	
	WebUI.verifyEqual(elementText, 'AutoAlert Academy', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Certification link exists
def certificationLink = findTestObject('Opportunities/Academy/Certification/Certification Link')

elementVisible = WebUI.waitForElementVisible(certificationLink, 10)

if(elementVisible){
	def elementText = WebUI.getText(certificationLink)
	
	WebUI.verifyEqual(elementText, 'Certification', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Transcript link exists
def transcriptLink = findTestObject('Opportunities/Academy/Transcript/Transcript Link')

elementVisible = WebUI.waitForElementVisible(transcriptLink, 10)

if(elementVisible){
	def elementText = WebUI.getText(transcriptLink)
	
	WebUI.verifyEqual(elementText, 'Transcript', FailureHandling.CONTINUE_ON_FAILURE)
}