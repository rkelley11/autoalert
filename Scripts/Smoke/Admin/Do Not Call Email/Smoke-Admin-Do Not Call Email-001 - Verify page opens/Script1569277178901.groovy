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
import test.Assistant.AfterFailure
import general.Urls
import begin.Initialize

// Login
new Initialize().readySetGo('automation1')

def testObjectUnderTest

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.Admin.DO_NOT_CALL_EMAIL

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the Admin/Do Not Call/Email page url', AfterFailure.STOP)

// Verify page header
testObjectUnderTest = findTestObject('Opportunities/Admin/Do Not Call or Email List/Do Not Call or Email List Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Do Not Call / Email', AfterFailure.CONTINUE)