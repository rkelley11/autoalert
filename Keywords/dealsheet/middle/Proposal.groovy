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

import groovy.time.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*
import dealsheet.*

public class Proposal extends WrappedSelenium{
	
	def driver
	
	def proposalButtonObjName = TestObjectNames.Proposal.BUTTON
	
	Proposal(){

		this.driver = DriverFactory.getWebDriver()		
	}
	
	def waitForSpinnerToFinish(){
		
		def spinnerFinished = false
		
		def logHeader
		
		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "
		
			def by = By.cssSelector('print-upgrade-proposal .cg-busy-animation')

			def timeExpired = false
	
			def timeToWait = 45
	
			def timeoutPeriod = new TimeDuration(0, 0, timeToWait, 0)
	
			def timeStart = new Date()
			
			while(!spinnerFinished && !timeExpired){
				
				def spinner = findElements(by, 1)
				
				if(spinner){
				
					def classes = getAttribute(spinner[0], 'class')
					
					spinnerFinished = classes.contains('ng-hide')	
			
					KeywordUtil.logInfo(logHeader + "Spinner HAS" + (spinnerFinished ? " " : " NOT ") + "finished" + (spinnerFinished ? "" : " yet"))			
				}
				
				if(!spinnerFinished){
					
					timeExpired = isTimeExpired(timeStart, timeoutPeriod)
					
					if(!timeExpired) sleep(1000) // Wait 1 second before polling again
				}
			}
			
			KeywordUtil.logInfo(logHeader + "Spinner DID" + (spinnerFinished ? " " : " NOT ") + "finish before time expired")
			
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		spinnerFinished
	}
	
	def waitForSpinnerToStart(){
		
		def spinnerStarted = false
		
		def logHeader
		
		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "
			
			KeywordUtil.logInfo(logHeader + "Checking for spinner to start")
		
			def by = By.cssSelector('print-upgrade-proposal .cg-busy-animation')

			def timeExpired = false
	
			def timeToWait = 5
	
			def timeoutPeriod = new TimeDuration(0, 0, timeToWait, 0)
	
			def timeStart = new Date()
			
			while(!spinnerStarted && !timeExpired){
				
				def spinner = findElements(by, 1)
				
				if(spinner){
				
					def classes = getAttribute(spinner[0], 'class')
					
					spinnerStarted = !classes.contains('ng-hide')
			
					KeywordUtil.logInfo(logHeader + "Spinner HAS" + (spinnerStarted ? " " : " NOT ") + "started" + (spinnerStarted ? "" : " yet"))			
				}
				
				if(!spinnerStarted){
					
					timeExpired = isTimeExpired(timeStart, timeoutPeriod)
					
					if(!timeExpired) sleep(1000) // Wait 1 second before polling again
				}
			}
			
			KeywordUtil.logInfo(logHeader + "Spinner DID" + (spinnerStarted ? " " : " NOT ") + "start before time expired")
			
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		spinnerStarted
	}

	@Keyword
	def verifyOpenInNewWindow(){
		
		def logHeader
		
		def windowOpened = false
		
		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def windowHandles, numberOfWindowsBeforeClicking, numberOfWindowsAfterClicking, currentUrl, correctUrl
	
			windowHandles = this.driver.getWindowHandles()
	
			numberOfWindowsBeforeClicking = windowHandles.size()
	
			KeywordUtil.logInfo(logHeader + "Number of window handles found BEFORE clicking Open in New Window: " + numberOfWindowsBeforeClicking as String)
	
			click(returnTestObject(proposalButtonObjName))
			
			waitForSpinnerToStart()
			
			def spinnerFinished = waitForSpinnerToFinish()
			
			if(spinnerFinished){
		
				windowHandles = this.driver.getWindowHandles()
		
				numberOfWindowsAfterClicking = windowHandles.size()
		
				KeywordUtil.logInfo(logHeader + "Number of window handles found AFTER clicking Open in New Window: " + numberOfWindowsAfterClicking as String)
		
				this.driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 1])
		
				currentUrl = this.driver.getCurrentUrl()
		
				correctUrl = currentUrl.contains(GlobalVariable.Url_Login)
		
				KeywordUtil.logInfo("Current URL : " + currentUrl)
		
				KeywordUtil.logInfo('Current URL DOES' + (correctUrl ? ' ' : ' NOT ') + 'contain the login URL (' + GlobalVariable.Url_Login + ')')
		
				this.driver.close()
		
				this.driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 2])
				
				windowOpened = true				
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		windowOpened
	}
}
