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
import dealsheet.Common as Common
import com.kms.katalon.core.util.KeywordUtil

import begin.Initialize

// Login
new Initialize().readySetGo()

def mileage = new Mileage()

dealerSelectSuccessful = CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

assert dealerSelectSuccessful

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 
    30)

assert elementVisible

WebUI.sendKeys(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 
    GlobalVariable.Dealsheet_Search + Keys.RETURN)

successfulClick = CustomKeywords.'opportunities.alertdesk.QuickSearch.openFirstDealsheetFromSearch'()

assert successfulClick

dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

clickedTab = CustomKeywords.'dealsheet.Common.selectBottomSectionTab'(Common.BottomSectionTabs.EXISTING_VEHICLE_DETAILS)

assert clickedTab

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Information'), 
    10)

assert elementVisible

originalMileage = WebUI.getText(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Information'))

def dateLastMileageUpdate = mileage.getLastMileageUpdateReadOnDate(originalMileage)

def mileageUpdateDate = LocalDate.now()

KeywordUtil.logInfo('mileageUpdateDate: ' + mileageUpdateDate as String)
	
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Edit Icon'), 
    10)

assert elementVisible

successfulClick = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Edit Icon'))

assert successfulClick

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Input - Mileage'), 
    10)

assert elementVisible

def mileageInfo = CustomKeywords.'dealsheet.Mileage.enterUpdatedMileage'(null, mileageUpdateDate, Mileage.InputMethod.CALENDAR)

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Button - Submit'), 
    10)

assert elementVisible

successfulClick = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Car Comparison Section/Existing Vehicle/Mileage/Modal/Update Mileage Modal - Button - Submit'))

assert successfulClick

dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Information'), 
    10)

assert elementVisible

def updatedMileage = WebUI.getText(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Mileage/Bottom Section - Mileage - Information'))

def expectedMileage = mileage.determineExpectedMileageInfo(mileageInfo)

KeywordUtil.logInfo('Original Reading:\t' + originalMileage)

KeywordUtil.logInfo('Updated Reading:\t' + updatedMileage)

KeywordUtil.logInfo('Attempted Input:\t' + mileageInfo['mileage'] + ' on ' + mileageInfo['calendar']['date'])

KeywordUtil.logInfo('Expected mileage:\t' + expectedMileage)

assert updatedMileage.contains(expectedMileage) // Omitting date due to production using future date (Richard ok'd not a defect)

WebUI.delay(5)