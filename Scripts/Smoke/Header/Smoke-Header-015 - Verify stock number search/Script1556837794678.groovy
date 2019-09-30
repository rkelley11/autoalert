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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import begin.Initialize
import general.Urls

// Login
new Initialize().readySetGo()

CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

def elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Stock Number Search/Stock Number Search Textbox'), 30)

assert elementVisible

WebUI.sendKeys(findTestObject('Opportunities/Stock Number Search/Stock Number Search Textbox'), 
    GlobalVariable.StockNumber)

def resultsDisplayedProperly = CustomKeywords.'opportunities.StockSearch.resultsDisplayProperly'()

assert resultsDisplayedProperly

def selectedStockNumber = CustomKeywords.'opportunities.StockSearch.selectFirstResult'()

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Inventory/Inventory Header'), 30)

assert elementVisible

def currentUrl = WebUI.getUrl()

assert currentUrl.contains(GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.INVENTORY)

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Inventory/Inventory Selected Vehicle'), 10)

assert elementVisible

def selectedVehicle = WebUI.getText(findTestObject('Opportunities/Alert Desk/Inventory/Inventory Selected Vehicle'))

KeywordUtil.logInfo("Selected stock number: " + selectedStockNumber)

KeywordUtil.logInfo(selectedVehicle)

assert selectedVehicle == ('Selected vehicle: ' + selectedStockNumber)

