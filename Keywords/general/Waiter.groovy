package general

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

import java.util.logging.*
import java.time.format.DateTimeFormatter as DateTimeFormatter
import groovy.time.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

import general.Utilities
import general.WrappedSelenium

public class Waiter {

	/*def log, utils, driver, wrappedSelenium

	Waiter(){

		this.log = Logger.getLogger(this.class.getName())

		this.utils = new Utilities()

		this.driver = DriverFactory.getWebDriver()

		this.wrappedSelenium = new WrappedSelenium()
	}
	
	//Temporary place holders using WebUI framework

	@Keyword
	def waitForElementVisible(testObject, timeout){
		
		waitForElementVisible(testObject, timeout)
	}

	def waitForElementVisible(TestObject testObject){

		WebUI.waitForElementVisible(testObject, 10)
	}

	def waitForElementVisible(TestObject testObject, int timeout){

		WebUI.waitForElementVisible(testObject, timeout)
	}

	@Keyword
	def waitForElementToNotBeVisible(TestObject testObject){

		WebUI.waitForElementNotVisible(testObject, 10)
	}*/
}
