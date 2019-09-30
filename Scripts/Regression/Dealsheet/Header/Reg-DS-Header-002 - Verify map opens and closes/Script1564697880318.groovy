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

import begin.Initialize

// Login
new Initialize().readySetGo()

CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

assert WebUI.waitForElementVisible(findTestObject('Opportunities/Customer Search/Customer Search Textbox'),
	30)

WebUI.sendKeys(findTestObject('Opportunities/Customer Search/Customer Search Textbox'),
	GlobalVariable.Dealsheet_Search + Keys.RETURN)

successfulClick = CustomKeywords.'opportunities.alertdesk.QuickSearch.openFirstDealsheetFromSearch'()

assert successfulClick

dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

elementIsVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Top Section/Google Map/Google Map Icon'), 
    10)

assert elementIsVisible

mapFunctionedProperly = CustomKeywords.'dealsheet.GoogleMap.mapOpensAndClosesProperly'()

assert mapFunctionedProperly

