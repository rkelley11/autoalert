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
import general.Urls
import test.Assistant.AfterFailure

def testObjectUnderTest

// Login
new Initialize().readySetGo()

// Navigate to page
def url = GlobalVariable.Url_Login + Urls.Opportunities.DataEntry.RATES

WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)

// Verify header
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Rates Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Rates', AfterFailure.CONTINUE)

// Verify tab
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Used Rates/Used Rates - Tab')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Used Rates', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify tab is active
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Used Rates/Used Rates - Tab Content')

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'active', AfterFailure.CONTINUE)

// Verify tab
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Over Allowance/Over Allowance - Tab')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Over Allowance', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify tab is active
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Over Allowance/Over Allowance - Tab Content')

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'active', AfterFailure.CONTINUE)

// Verify tab
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Rate Mark Ups/Rate Mark Ups - Tab')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Rate Mark Ups', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify tab is active
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Rates/Rate Mark Ups/Rate Mark Ups - Tab Content')

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'active', AfterFailure.CONTINUE)