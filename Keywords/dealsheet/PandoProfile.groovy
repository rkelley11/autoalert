package dealsheet

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

import java.time.format.DateTimeFormatter as DateTimeFormatter
import groovy.time.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class PandoProfile extends WrappedSelenium{
	
	/*
	 * Many areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def driver, timeoutPeriod

	def pandoProfileContainerObjName = TestObjectNames.UpperSection.PandoProfile.CONTAINER

	def pandoProfileCustomerNameObjName = TestObjectNames.UpperSection.PandoProfile.CUSTOMER_NAME

	def pandoIconObjName = TestObjectNames.UpperSection.PandoProfile.TAB

	PandoProfile(){

		this.driver = DriverFactory.getWebDriver()

		this.timeoutPeriod = new TimeDuration(0,0,10,0)
	}

	@Keyword
	def waitForSectionToOpen(){

		def foundMaps, classAttribute, customerName

		def sectionOpen = false

		def timeStart = new Date()

		while (!sectionOpen && (TimeCategory.minus(new Date(), timeStart) < this.timeoutPeriod)){

			customerName = getText(returnTestObject(pandoProfileCustomerNameObjName))

			KeywordUtil.logInfo("Customer name: " + (customerName ? customerName : "None found yet"))

			sectionOpen = customerName ? true : false

			sleep(1000)
		}

		sleep(1000) //having issue with Customer Info/Messages becoming active right away, so sleep to give chance to complete.  Refactor is possible

		KeywordUtil.logInfo("Pando Profile open: " + sectionOpen as String)

		sectionOpen
	}

	def pandoIconStatus(){

		def logHeader

		def status = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def testObject = returnTestObject(pandoIconObjName)

			def classes = getAttribute(testObject, 'class')

			status['active'] = classes.contains('active')

			def image = findElements(By.cssSelector('img'), testObject)

			def src = getAttribute(image[0], 'src')

			def start = src.lastIndexOf(GlobalVariable.GoodSlash) + 1

			KeywordUtil.logInfo(logHeader + "Good slash found at index " + start as String)

			def srcLength = src.length()

			KeywordUtil.logInfo(logHeader + "Attribute 'src' length: " + srcLength)

			def svg = src.substring(start, src.length())

			status['svg'] = svg

			KeywordUtil.logInfo(logHeader + "Pando icon status: " + status as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		status
	}
}
