package opportunities.alertdesk.dashboard

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

import com.kms.katalon.core.util.KeywordUtil

public class ManagerDashboard {

	enum Widgets{

		SERVICE("Service"),
		EMAIL_AND_TEXT("Email & Text"),
		WEB("Web"),
		ONE_TO_ONE("One-to-One"),
		ONE_TO_ONE_INTELLIGENT_MARKETING("One-to-One / Intelligent Marketing"),
		UPCOMING_APPOINTMENTS("Upcoming Appointments"),
		CUSTOMER_MESSAGES("Customer Messages"),
		GEO_ALERT("GeoAlert")

		def String stringValue

		Widgets(String stringValue){

			this.stringValue = stringValue
		}

		def getValue(){

			return stringValue
		}
	}

	enum ServiceColumns{

		HOT(0),
		DATE(1),
		OWNER(2),
		CLIENT_NAME(3),
		YEAR(4),
		MODEL(5),
		SCORE(6)

		def int intValue

		ServiceColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	enum EmailTextColumns{

		HOT(0),
		TYPE(1),
		DATE(2),
		OWNER(3),
		CLIENT_NAME(4),
		YEAR(5),
		MODEL(6),
		SCORE(7)

		def int intValue

		EmailTextColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	enum WebColumns{

		HOT(0),
		DATE(1),
		OWNER(2),
		CLIENT_NAME(3),
		YEAR(4),
		MODEL(5),
		SCORE(6)

		def int intValue

		WebColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}

	enum OneToOneColumns{

		HOT(0),
		DATE(1),
		OWNER(2),
		CLIENT_NAME(3),
		YEAR(4),
		MODEL(5),
		SCORE(6)

		def int intValue

		OneToOneColumns(int intValue){

			this.intValue = intValue
		}

		def getValue(){

			return intValue
		}
	}
}
