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
import dealsheet.top.Icons
import com.kms.katalon.core.util.KeywordUtil

import begin.Initialize

// Login
new Initialize().readySetGo()

CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Sub Menu - Alert Desk'), 30)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Alert Desk/Sub Menu - Alert Desk'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Hover Over Dropdown List/Hover Over Dashboard Link'), 10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Alert Desk/Hover Over Dropdown List/Hover Over Dashboard Link'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities Dashboard Link'), 
    10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities Dashboard Link'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Top Lease Opportunities/Top Lease Opportunities Refresh Button'), 
    10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Top Lease Opportunities/Top Lease Opportunities Refresh Button'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Top Lease Opportunities/Top Lease Opportunities Table Body'), 
    10)

assert elementVisible

elementClicked = CustomKeywords.'opportunities.alertdesk.Dashboard.clickWidgetRow'(Widgets.TOP_LEASE_OPPORTUNITIES, null, null)

assert elementClicked

dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Dealsheet Container'), 10)

assert elementVisible

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Top Section/Customer Profile/Customer Profile Name'), 10)

assert elementVisible

dealsheetInfo = [:]

(dealsheetInfo['customerName']) = WebUI.getText(findTestObject('Dealsheet/Top Section/Customer Profile/Customer Profile Name'))

(dealsheetInfo['newWindowLink']) = WebUI.getAttribute(findTestObject('Dealsheet/Top Section/Icons/Upper/Open Dealsheet in New Window'), 
    'href')

KeywordUtil.logInfo(dealsheetInfo as String)

newWindowOpened = CustomKeywords.'dealsheet.UpperIcons.verifyOpenInNewWindow'(dealsheetInfo)

assert newWindowOpened

