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
import org.openqa.selenium.Keys as Keys
import java.util.concurrent.ThreadLocalRandom as ThreadLocalRandom
import java.text.DecimalFormat as DecimalFormat
import java.time.LocalDate as LocalDate
import java.time.format.DateTimeFormatter as DateTimeFormatter
import dealsheet.Mileage as Mileage
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import begin.Initialize

// Login
new Initialize().readySetGo('automation5')

def mileage = new Mileage()

def elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 
    30)

assert elementVisible

WebUI.sendKeys(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), GlobalVariable.Dealsheet_Search + 
    Keys.RETURN)

def successfulClick = CustomKeywords.'opportunities.alertdesk.QuickSearch.openFirstDealsheetFromSearch'()

assert successfulClick

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

// Verify Existing Vehicle Year Exists
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Trim Info/Car Comparison - Existing Vehicle - Year'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

def elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Trim Info/Car Comparison - Existing Vehicle - Year'))

def elementNotEmpty = (elementText != null) && !(elementText.isEmpty())

WebUI.verifyEqual(elementNotEmpty, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Existing Vehicle Make Exists
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Trim Info/Car Comparison - Existing Vehicle - Make'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Trim Info/Car Comparison - Existing Vehicle - Make'))

elementNotEmpty = ((elementText != null) && !(elementText.isEmpty()))

WebUI.verifyEqual(elementNotEmpty, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Existing Vehicle Full Trim Exists
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Trim Info/Car Comparison - Existing Vehicle - Full Trim'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Trim Info/Car Comparison - Existing Vehicle - Full Trim'))

elementNotEmpty = ((elementText != null) && !(elementText.isEmpty()))

WebUI.verifyEqual(elementNotEmpty, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Existing Vehicle Term and Payment Exists
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Contract Info/Car Comparison - Existing Vehicle - Term and Payment'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Contract Info/Car Comparison - Existing Vehicle - Term and Payment'))

elementNotEmpty = ((elementText != null) && !(elementText.isEmpty()))

WebUI.verifyEqual(elementNotEmpty, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Existing Vehicle Sale Type Edit Button is Clickable & Opens Popup
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Financial Info/Financial Info - Edit Icon'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    def clicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Financial Info/Financial Info - Edit Icon'))

    WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)

    elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Financial Info/Common/Financial Info - Common - Header'), 
        10)

    WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

    elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Financial Info/Common/Financial Info - Common - Header'))

    WebUI.verifyEqual(elementText, 'Financial Info', FailureHandling.CONTINUE_ON_FAILURE)

    elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Financial Info/Common/Financial Info - Common - Button - Cancel'), 
        10)

    WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

    clicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Financial Info/Common/Financial Info - Common - Button - Cancel'))

    WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Existing Vehicle Mileage Edit Button is Clickable & Opens Popup
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Car Comparison - Existing Vehicle - Mileage - Edit Icon'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    def clicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Car Comparison - Existing Vehicle - Mileage - Edit Icon'))

    WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)

    elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Title'), 
        10)

    WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

    elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Title'))

    WebUI.verifyEqual(elementText, 'Update Mileage', FailureHandling.CONTINUE_ON_FAILURE)

    elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Button - Cancel'), 
        10)

    WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

    clicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Button - Cancel'))

    WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify Existing Vehicle Payoff Edit Button is Clickable & Opens Popup
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Car Comparison - Existing Vehicle - Payoff - Edit Icon'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
    def clicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Car Comparison - Existing Vehicle - Payoff - Edit Icon'))

    WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)

    elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Modal/Save Adjust Payoff Modal - Title'), 
        10)

    WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

    elementText = WebUI.getText(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Modal/Save Adjust Payoff Modal - Title'))

    WebUI.verifyEqual(elementText, 'Save/Adjust Payoff', FailureHandling.CONTINUE_ON_FAILURE)

    elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Modal/Save Adjust Payoff Modal - Button - Cancel'), 
        10)

    WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

    clicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Modal/Save Adjust Payoff Modal - Button - Cancel'))

    WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
}

