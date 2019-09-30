package opportunities.alertdesk

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

final class TestObjectNames {

	static class Activities{
	}

	static class Conquest{
	}

	static class Dashboard{

		static class QuickLinks{

			static String ACADEMY = 'Quick Links - Academy'

			static String ACTIVITIES = 'Quick Links - Activities'

			static String FIMANAGER = 'Quick Links - FI Manager'

			static String OPPORTUNITIES = 'Quick Links - Opportunities'

			static String PREOWNED_FINDABUYER = 'Quick Links - Pre-Owned - Find a Buyer'

			static String SEARCH = 'Quick Links - Search'

			static String SERVICE_CONQUESTS = 'Quick Links - Service Conquests'

			static String SERVICE_DRIVE = 'Quick Links - Service Drive'

			static String SERVICE_DRIVE_OFFERS = 'Quick Links - Service Drive Offers'
		}

		static class OpportunitiesByAlert{

			static String CONTAINER = 'Opportunities Container'
		}
	}

	static class FIManager{
	}

	static class Opportunities{
	}

	static class Preowned{
	}

	static class QuickSearch{
	}

	static class Search{

		static String CONTAINER = 'Search Container'
	}

	static class ServiceDrive{
	}

	static class ServiceDriveOffers{

		static String PRINT_OFFER_TITLE = 'Print Offer - Header - Title'
	}
}
