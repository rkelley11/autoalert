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

import begin.Initialize

// Login
new Initialize().readySetGo()

def dealerSelect = CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

assert dealerSelect

// Open Dealsheet
def alert = findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities by Alert/Opportunity - Alert')

elementVisible = WebUI.waitForElementVisible(alert, 10)

CustomKeywords.'general.WrappedSelenium.click'(alert)

CustomKeywords.'opportunities.alertdesk.Opportunities.clickFirstRow'()

/*def dealsheetUrl = CustomKeywords.'dealsheet.Common.getRandomDealsheetURL'()

WebUI.navigateToUrl(dealsheetUrl, FailureHandling.STOP_ON_FAILURE)*/

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Dealsheet - Tabs - Existing Vehicle Details - Tab'), 10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Dealsheet - Tabs - Existing Vehicle Details - Tab'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Payoff/Bottom Section - Payoff - Edit Icon'), 10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Payoff/Bottom Section - Payoff - Edit Icon'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Modal/Save Adjust Payoff Modal - Button - Calendar'), 10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Payoff/Modal/Save Adjust Payoff Modal - Button - Calendar'))

assert elementClicked

def pastDate = LocalDate.now().minusDays(2)

def dateInfo = CustomKeywords.'general.Calendar.clickDate'(pastDate)

assert dateInfo["available"] == false

