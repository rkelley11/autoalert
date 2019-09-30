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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import java.time.LocalDateTime as LocalDateTime
import test.Assistant.AfterFailure
import begin.Initialize

// Login
new Initialize().readySetGo()

def testObjectUnderTest
def opensProperly

// Should the Live Support link even be available at this time?
def withinHours = CustomKeywords.'opportunities.LiveSupport.shouldBeAvailable'()

// Make sure page loads
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - Opportunities')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

// Determine is Live Support link is visible
testObjectUnderTest = findTestObject('Header/Live Support/Live Support Link')

def linkVisible = WebUI.waitForElementVisible(testObjectUnderTest, 5, FailureHandling.CONTINUE_ON_FAILURE)

// If the link is visible does it open the support window properly?
if(linkVisible){
	
	testObjectUnderTest = findTestObject('Header/Live Support/Live Support Link')
	
	opensProperly = CustomKeywords.'opportunities.LiveSupport.doesLiveSupportOpenProperly'(testObjectUnderTest)
}

// If support should be available then the window should open or if it supposed to be unavailable then the link should not appear
if(withinHours){	
			
	assert withinHours && linkVisible && opensProperly
}
else{
	
	def afterHours = !withinHours
	
	def linkNotVisible = !linkVisible
		
	assert afterHours && linkNotVisible
}