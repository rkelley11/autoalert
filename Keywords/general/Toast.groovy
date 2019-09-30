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

import sql.*
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit

import groovy.time.*

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Toast extends WrappedSelenium{

	@Keyword
	def cleared(){

		def logHeader
		
		def disappeared = false
		
		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "
			
			def by = By.cssSelector('.toast')

			// Wait for toast message to appear or timeout
			def appeared = false
			
			def timeExpiredToAppear = false
	
			def timeoutPeriodStart = new TimeDuration(0,0,3,0)
	
			def timeStart = new Date()
			
			KeywordUtil.logInfo(logHeader + "Wait up to " + (timeoutPeriodStart as String) + " for toast message to appear")
	
			while(!appeared && !timeExpiredToAppear){
				
				def toastMessage = findElements(by, 1)
				
				if(toastMessage){
			
					KeywordUtil.logInfo(logHeader + "Toast message APPEARED")
					
					appeared = true					
				}
				else{
	
					timeExpiredToAppear = isTimeExpired(timeStart, timeoutPeriodStart)
					
					if(!timeExpiredToAppear) sleep(1000) // Pause 1 second before polling again			
				}
			}			

			// Wait for toast message to disappear or timeout
			def timeExpiredToDisappear = false
	
			def timeoutPeriodToDisappear = new TimeDuration(0,0,10,0)
	
			timeStart = new Date()
			
			KeywordUtil.logInfo(logHeader + "Wait up to " + (timeoutPeriodToDisappear as String) + " for toast message to disappear")
	
			while(!disappeared && !timeExpiredToDisappear){
				
				def toastMessage = findElements(by, 1)
				
				if(!toastMessage){
			
					KeywordUtil.logInfo(logHeader + "Toast message DISAPPEARED")
					
					disappeared = true					
				}
				else{
	
					timeExpiredToDisappear = isTimeExpired(timeStart, timeoutPeriodToDisappear)
					
					if(!timeExpiredToDisappear) sleep(1000) // Pause 1 second before polling again		
				}
			}			
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}
		
		disappeared
	}
}