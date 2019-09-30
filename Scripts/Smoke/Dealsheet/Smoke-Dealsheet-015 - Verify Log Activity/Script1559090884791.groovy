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
def tabButton = findTestObject('Dealsheet/Log Activity/Log Activity/Common/Dealsheet - Log Activity - Tab - Button')

elementVisible = WebUI.waitForElementVisible(tabButton, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    def tabOpened = CustomKeywords.'general.WrappedSelenium.click'(tabButton)

    if (tabOpened) {
        def classes = WebUI.getAttribute(tabButton, 'class', FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyEqual(classes.contains('active'), true, FailureHandling.CONTINUE_ON_FAILURE)
    }
}

// Verify Heading
def mainHeading = findTestObject('Dealsheet/Log Activity/Log Activity/Common/Dealsheet - Log Activity - Heading')

elementVisible = WebUI.waitForElementVisible(mainHeading, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    elementText = WebUI.getText(mainHeading)

    WebUI.verifyEqual(elementText, 'Log Activity', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Log Activity icon exists
def logActivityIcon = findTestObject('Dealsheet/Log Activity/Log Activity/Action Menu/Dealsheet - Log Activity - Log Activity - Action Menu - Button')

elementVisible = WebUI.waitForElementVisible(logActivityIcon, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    elementText = WebUI.getText(logActivityIcon)

    WebUI.verifyEqual(elementText, 'Log Activity', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Schedule icon exists
def scheduleIcon = findTestObject('Dealsheet/Log Activity/Schedule/Action Menu/Dealsheet - Log Activity - Schedule - Action Menu - Button')

elementVisible = WebUI.waitForElementVisible(scheduleIcon, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    elementText = WebUI.getText(scheduleIcon)

    WebUI.verifyEqual(elementText, 'Schedule', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify 'Type of Call' heading
def typeOfCallHeading = findTestObject('Dealsheet/Log Activity/Log Activity/Log Call/Type of Call/Dealsheet - Log Activity - Type of Call - Heading')

elementVisible = WebUI.waitForElementVisible(typeOfCallHeading, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    elementText = WebUI.getText(typeOfCallHeading)

    WebUI.verifyEqual(elementText, 'Type of Call', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Notes label
def notesLabel = findTestObject('Dealsheet/Log Activity/Log Activity/Notes/Dealsheet - Log Activity - Notes - Label')

elementVisible = WebUI.waitForElementVisible(notesLabel, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    elementText = WebUI.getText(notesLabel)

    WebUI.verifyEqual(elementText, 'Notes', FailureHandling.CONTINUE_ON_FAILURE)
}

