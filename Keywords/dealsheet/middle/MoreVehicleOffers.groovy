package dealsheet.middle

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
import dealsheet.*

public class MoreVehicleOffers extends WrappedSelenium{

	def offerContainerObjName = TestObjectNames.MoreVehicleOffers.OFFER_CONTAINER

	@Keyword
	def getOffers(){

		def offers = []

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def offerContainer = returnTestObject(offerContainerObjName)

			def by = By.cssSelector('li')

			offers = findElements(by, offerContainer)

			def numberOfOffers = offers ? offers.size() : 0

			KeywordUtil.logInfo(logHeader + "Found " + (numberOfOffers as String) + " offer" + (numberOfOffers != 1 ? 's' : ''))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		offers
	}

	@Keyword
	def isOfferGood(WebElement offer){

		def goodOffer = false

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "
			
			def by = By.cssSelector('li [class*="payment-"]')
			
			def payment = new ExistingVehicle().getPayment()
			
			def offerPaymentDifference = findElements(by, offer)
			
			if(offerPaymentDifference){
				
				def paymentDifference = offerPaymentDifference[0].getText()
				
				goodOffer = !paymentDifference.contains(payment) // If existing payment is in the offer payment difference then it appears there is no term/payment available
			}
			else{
				
				throw new Exception('No payment difference found in offer')
			}
			
			KeywordUtil.logInfo(logHeader + '')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		goodOffer
	}
}
