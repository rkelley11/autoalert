package opportunities.alertdesk.dashboard.opportunities

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
import dealsheet.Common
import dealsheet.bottom.*
import dealsheet.bottom.Common as BottomCommon
import dealsheet.bottom.Common.BottomSectionTabs2 as BottomSectionTabs2
import opportunities.alertdesk.Dashboard
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets as OpportunitiesDashboardWidgets

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class TaggedOpportunities extends WrappedSelenium{

	def returnWrappers(){

		def logHeader

		def wrappers
		
		def numberOfWrappers = 0

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "
			
			def by = By.cssSelector('tagged-customers .tag-wrapper')
			
			wrappers = findElements(by)

			if(wrappers){
				
				numberOfWrappers = wrappers.size()								
			}
				
			KeywordUtil.logInfo(logHeader + 'Number of found tagged opportunities: ' + numberOfWrappers as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		wrappers
	}

	@Keyword
	def returnList(){

		def logHeader

		def taggedOpportunities = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "
			
			def wrappers = returnWrappers()
			
			if(wrappers){
			
				def by = By.cssSelector('.tag-description')
			
				for(int i = 0 ; i < wrappers.size() ; i++){
					
					def tagDescriptions = findElements(by, wrappers[i])
					
					if(tagDescriptions){
						
						def tagDescription = getText(tagDescriptions[0])
						
						taggedOpportunities.add(tagDescription)
					}
				}
			}
			else{
				
				throw new Exception('No tagged opportunities found')
			}

			KeywordUtil.logInfo(logHeader + 'Tagged opportunities found: ' + taggedOpportunities as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		taggedOpportunities
	}
}
