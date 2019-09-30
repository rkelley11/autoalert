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
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets as Widgets
import begin.Initialize
import general.Urls
import test.Assistant.AfterFailure

// Login
new Initialize().readySetGo('automation5')

// Navigate to opportunities dashboard
def expectedUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.OPPORTUNITIES_DASHBOARD

if(WebUI.getUrl() != expectedUrl){
	
	WebUI.navigateToUrl(expectedUrl, FailureHandling.STOP_ON_FAILURE)
	
	CustomKeywords.'test.Assistant.verifyUrl'(expectedUrl, AfterFailure.STOP)
}

// Click first row
CustomKeywords.'opportunities.alertdesk.Dashboard.clickWidgetRow'(Widgets.TOP_LEASE_OPPORTUNITIES, null, null)

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

CustomKeywords.'test.Assistant.verifyTrue'(dealsheetLoaded, 'Dealsheet successfully loaded', AfterFailure.STOP)

// Verify dealsheet container is visible
def testObjectUnderTest = findTestObject('Dealsheet/Dealsheet Container')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)
