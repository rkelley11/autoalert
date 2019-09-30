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
import dealsheet.bottom.Common.BottomSectionTabs2 as BottomSectionTabs2
import begin.Initialize

// Login
new Initialize().readySetGo('automation5')

def elementVisible

def elementText

def alert = findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities by Alert/Opportunity - Alert')

elementVisible = WebUI.waitForElementVisible(alert, 10)

CustomKeywords.'general.WrappedSelenium.click'(alert)

CustomKeywords.'opportunities.alertdesk.Opportunities.clickFirstRow'()

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

// Opening Tab
def tabOpened = CustomKeywords.'dealsheet.bottom.Common.openTab'(BottomSectionTabs2.SERVICE_HISTORY)

WebUI.verifyEqual(tabOpened, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Heading
def mainHeading = findTestObject('Dealsheet/Tabs/Service History/Dealsheet - Tabs - Service History - Heading')

elementVisible = WebUI.waitForElementVisible(mainHeading, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    elementText = WebUI.getText(mainHeading)

    WebUI.verifyEqual(elementText, 'Service History', FailureHandling.CONTINUE_ON_FAILURE)
}