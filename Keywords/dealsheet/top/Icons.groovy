package dealsheet.top

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import general.*

public class Icons extends WrappedSelenium{

	@Keyword
	def click(Names icon){

		def successful = false

		def by = icon.getValue()

		KeywordUtil.logInfo("Element locator found: " + by as String)

		def iconList = findElements(by)

		if(iconList){

			successful = click(iconList[0])
		}

		KeywordUtil.logInfo("Successful at clicking " + (icon as String) + " icon: " + successful as String)

		successful
	}

	enum Names{
		FLAG(By.cssSelector('[title*=\"lag Deal Sheet\"]')),
		PRINT(By.cssSelector('[title=\"Print Deal Sheet\"]')),
		OPEN_IN_NEW_WINDOW(By.cssSelector('open-new-window a')),
		CLOSE(By.cssSelector('[title=Close Deal Sheet\"]')),
		MESSAGE(By.cssSelector('pando-customer-connect-message')),
		PROCESS(By.cssSelector('pando-customer-connect-process')),
		PRE_SCREEN(By.cssSelector('credit-prescreen a')),
		CRM(By.cssSelector('crm-action ng-transclude')),
		OFFER(By.cssSelector('print-offer ng-transclude')),
		CUSTOMER_EDIT(By.cssSelector('.ds-user-name customer-edit-action ng-transclude'))

		def By getValue

		Names(By getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}
