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
import dealsheet.Modals as Modals
import dealsheet.Modals.Titles as Titles
import dealsheet.Common as Common
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

import begin.Initialize

// Login
new Initialize().readySetGo()

def dealerSelectSuccessful = CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

assert dealerSelectSuccessful

def elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 30)

assert elementVisible

WebUI.sendKeys(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 
    GlobalVariable.Dealsheet_Search + Keys.RETURN)

def successfulClick = CustomKeywords.'opportunities.alertdesk.QuickSearch.openFirstDealsheetFromSearch'()

assert successfulClick

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

def clickedTab = CustomKeywords.'dealsheet.Common.selectBottomSectionTab'(Common.BottomSectionTabs.EXISTING_VEHICLE_DETAILS)

assert clickedTab

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Information'), 10)

assert elementVisible

def originalMileage = WebUI.getText(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Information'))

def dateLastMileageUpdate = new Mileage().getLastMileageUpdateReadOnDate(originalMileage)

def mileageUpdateDate = LocalDate.now().plusDays(7)

KeywordUtil.logInfo((('(Test script) mileageUpdateDate: ' + mileageUpdateDate) as String))

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Edit Icon'), 10)

assert elementVisible

successfulClick = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Edit Icon'))

assert successfulClick

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Input - Mileage'), 10)

assert elementVisible

def mileageInfo = CustomKeywords.'dealsheet.Mileage.enterUpdatedMileage'(null, mileageUpdateDate, Mileage.InputMethod.TYPE)

def submitButtonDisabled = new Modals().submitButtonDisabledStatus(Titles.UPDATE_MILEAGE)

assert submitButtonDisabled // Previous defect - WEBUI-7512

