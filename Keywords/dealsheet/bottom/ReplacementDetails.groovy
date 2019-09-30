package dealsheet.bottom

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

import general.*
import general.Enums.Alerts

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class ReplacementDetails extends WrappedSelenium{

	def paymentGridTableBodyObjName = TestObjectNames.ReplacementDetails.PaymentGrid.TABLE_BODY

	@Keyword
	def getPaymentGrid(){

		def logHeader

		def paymentGridRowDetails = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def paymentGridTestObject = returnTestObject(paymentGridTableBodyObjName)

			def by = By.cssSelector('tr')

			def paymentGridRows = findElements(by, paymentGridTestObject)

			if(paymentGridRows){

				for(int i = 0 ; i < paymentGridRows.size() ; i++){

					def rowDetails = getPaymentGridRowDetails(paymentGridRows[i])

					paymentGridRowDetails.add(rowDetails)
				}

				KeywordUtil.logInfo(logHeader + "Payment grid of " + (paymentGridRowDetails.size() as String) + " rows returned: " + paymentGridRowDetails as String)
			}
			else{

				throw new Exception("No payment grid rows found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		paymentGridRowDetails
	}

	def getPaymentGridRowDetails(WebElement element){

		def logHeader

		def paymentGridDetails = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def termBy = By.cssSelector('td[ng-bind="contract.term"]')

			def rateBy = By.cssSelector('td[ng-bind*="contract.apr"]')

			def paymentBy = By.cssSelector('td[ng-bind*="contract.payment "]')

			def diffBy = By.cssSelector('td[ng-bind*="contract.paymentDifference"]')

			// Term
			def term = findElements(termBy, element)

			if(term){

				paymentGridDetails['term'] = getText(term[0])
			}
			else{

				KeywordUtil.markWarning(logHeader + "Term information not found")
			}

			// Rate
			def rate = findElements(rateBy, element)

			if(rate){

				paymentGridDetails['rate'] = getText(rate[0])
			}
			else{

				KeywordUtil.markWarning(logHeader + "Rate information not found")
			}

			// Payment
			def payment = findElements(paymentBy, element)

			if(rate){

				paymentGridDetails['payment'] = getText(payment[0])
			}
			else{

				KeywordUtil.markWarning(logHeader + "Payment information not found")
			}

			// Difference
			def diff = findElements(diffBy, element)

			if(rate){

				paymentGridDetails['diff'] = getText(diff[0])
			}
			else{

				KeywordUtil.markWarning(logHeader + "Payment Difference information not found")
			}

			KeywordUtil.logInfo(logHeader + "Payment grid row details: " + paymentGridDetails as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		paymentGridDetails
	}
}
