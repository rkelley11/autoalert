package opportunities.preowned

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

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

import groovy.time.*

public class Common {

	def driver

	Common(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def clickLinkInWidget(PreownedDashboardWidgets widget, LinkedHashMap column){

		def successful

		switch (widget){

			case PreownedDashboardWidgets.TOP_USED_CAR_SALES_OPPORTUNITIES:

				successful = (new TopUsedCarSalesOpportunities()).clickLink(column)

				break

			case PreownedDashboardWidgets.USED_CAR_INVENTORY_AND_OPPORTUNITIES:

				successful = (new UsedCarInventoryAndOpportunities()).clickLink(column)

				break

			case PreownedDashboardWidgets.FASTEST_SELLING_INVENTORY:

				successful = (new FastestSellingInventory()).clickLink(column)

				break

			case PreownedDashboardWidgets.ONE_ON_ONE:

				successful = (new OneOnOne()).clickLink(column)

				break

			default:

				KeywordUtil.markError("Widget " + (widget as String) + " was not found.")

				throw new Exception()
		}

		successful
	}

	enum PreownedDashboardWidgets{

		TOP_USED_CAR_SALES_OPPORTUNITIES,
		USED_CAR_INVENTORY_AND_OPPORTUNITIES,
		FASTEST_SELLING_INVENTORY,
		ONE_ON_ONE
	}
}
