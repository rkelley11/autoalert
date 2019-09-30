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

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Enums extends WrappedSelenium {

	enum Widgets{

		MY_AGENDA("My Agenda"),
		NEW_OPPORTUNITY_CHANGES("New Opportunity Changes"),
		OPPORTUNITIES_BY_ALERT("Opportunities by Alert"),
		OPPORTUNITY_SEARCHES("Opportunity Searches"),
		RECENTLY_ASSIGNED("Recently Assigned"),
		TAGGED_OPPORTUNITIES("Tagged Opportunities"),
		TOP_LEASE_OPPORTUNITIES("Top Lease Opportunities"),
		TOP_RETAIL_OPPORTUNITIES("Top Retail Opportunities")

		def String stringValue

		Widgets(String stringValue){

			this.stringValue = stringValue
		}

		def getValue(){

			return stringValue
		}
	}

	enum TopLeaseOpportunitiesColumns{

		CLIENT_NAME(0),
		APPT_LAST_RO(1),
		CATEGORIES(2),
		YEAR(3),
		MODEL(4),
		SCORE(5)

		def int intValue

		TopLeaseOpportunitiesColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	enum TopRetailOpportunitiesColumns{

		CLIENT_NAME(0),
		APPT_LAST_RO(1),
		CATEGORIES(2),
		YEAR(3),
		MODEL(4),
		SCORE(5)

		def int intValue

		TopRetailOpportunitiesColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	enum NewOpportunityChangesColumns{

		CLIENT_NAME(0),
		ASSIGNED_TO(1),
		CATEGORIES(2),
		YEAR(3),
		MODEL(4),
		SCORE(5)

		def int intValue

		NewOpportunityChangesColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	enum OpportunitySearchesColumns{

		DESCRIPTION(0),
		TOTAL(1),
		WORKED(2),
		NOT_WORKED(3)

		def int intValue

		OpportunitySearchesColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	static class OpportunitiesByAlert{

		enum AlertBlocks{
			ALERT(['testObject': TestObjectNames.OppsByAlert.ALERT]),
			FLEX(['testObject': TestObjectNames.OppsByAlert.FLEX]),
			IN_MARKET(['testObject': TestObjectNames.OppsByAlert.IN_MARKET]),
			ENGAGED(['testObject': TestObjectNames.OppsByAlert.ENGAGED]),
			SERVICE(['testObject': TestObjectNames.OppsByAlert.SERVICE]),
			PENDING_SERVICE(['testObject': TestObjectNames.OppsByAlert.PENDING_SERVICE]),
			MILEAGE(['testObject': TestObjectNames.OppsByAlert.MILEAGE]),
			WARRANTY(['testObject': TestObjectNames.OppsByAlert.WARRANTY]),
			CONTRACT_END(['testObject': TestObjectNames.OppsByAlert.CONTRACT_END])

			def getValue

			AlertBlocks(LinkedHashMap getValue){

				this.getValue = getValue
			}

			def getValue(){

				return this.getValue
			}
		}
	}
}