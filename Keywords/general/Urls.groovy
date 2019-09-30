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

final class Urls{

	static String TBD = ''

	static String SAUCE_LABS = 'https://saucelabs.com/rest/v1/autoalert/jobs/'

	static class Header{

		static String USER_PROFILE = '/user/profile'
	}

	static class Opportunities{

		static class AlertDesk{

			static String OPPORTUNITIES = '/alert-desk/opportunities'

			static String OPPORTUNITIES_DASHBOARD = '/alert-desk/dashboard'

			static String MANAGERS_DASHBOARD = '/alert-desk/dashboard/manager'

			static String ACTIVITIES = '/alert-desk/activities'

			static String SEARCH = '/alert-desk/search'

			static String CONQUEST = '/alert-desk/conquests'

			static String INVENTORY = '/alert-desk/pre-owned-manager'

			static String SERVICE_DRIVE = '/alert-desk/service-manager'

			static String SERVICE_DRIVE_OFFERS = '/alert-desk/service-drive/offers'

			static String FI_MANAGER = '/alert-desk/finance-insurance-manager'

			static String DEALSHEET = '/alert-desk/deal-sheet'

			static String MANAGE_LEADS_PROCESSES = '/alert-desk/search/manage-presets'
		}

		static String INVENTORY = '/inventory-dashboard'

		static class DataEntry{

			static String PRICES = '/data-entry/prices'

			static String RATES = '/data-entry/rates'

			static String DEALER_REBATES = '/data-entry/rebates'

			static String TRADE_INS = '/data-entry/trade-ins'

			static String REPLACEMENT_OFFERS = '/data-entry/replacements'

			static String ALERT_TOLERANCES = '/data-entry/alerts'
		}

		static class Admin{

			static String SETTINGS = '/admin/dealer-settings'

			static String USERS = '/admin/users'

			static String ROLES = '/admin/roles'

			static String NOTIFICATIONS = '/admin/notifications'

			static String REPORT_SUBSCRIPTIONS = '/adminReportSubscriptions'

			static String PANDO_INTEGRATION = '/admin/pando-integration'

			static String DO_NOT_CALL_EMAIL = '/admin/do-not-call-email-list'

			static String TEMPLATES = '/admin/outreach-templates'

			static String CREDITCONVERT_ESTIMATOR = '/admin/creditconvert'
		}

		static class Academy{

			static String ACADEMY = '/academy'

			static String CERTIFICATION = '/academy/certification'

			static String WEBINARS = '/academy/webinars'

			static String CALENDAR = '/academy/calendar'
		}

		static class Help{

			static String CONTACT_US = '/about/contact-us'

			static String ABOUT = '/about'
		}
	}

	static class Reports{

		static String MONTHLY_PERFORMANCE = '/reports/performance'

		static String DASHBOARD = '/reports/dashboard'

		static class Opportunities{

			static String CERTIFICATION_AND_TRAINING = '/reports/certification'

			static String USER_ACTIVITY = '/reports/user-activity'

			static String SOLD_UNITS = '/reports/sold-units'

			static String SALES_GROSS = '/reports/sales-gross'

			static String SERVICE_DRIVE_SALES_ACTIVITY = '/reports/service-drive-sales-performance'

			static String CREDIT_CONVERT = '/reports/credit-convert'

			static String DATA_CLEANSE = '/reports/dataCleanse'

			static String INVENTORY = '/reports/inventory'

			static String INTELLIGENT_MARKETING = '/reports/legacy/intelligentmarketing'
		}

		static class OneToOne{

			static String SOLD_UNITS = '/reports/one-to-one-sold-units'
		}

		static class Pando{

			static String ACTIVITY = '/reports/pando-activity'

			static String SURVEYS = '/reports/pando-surveys'
		}
	}
}
