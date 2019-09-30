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

public class TestObjectNames {

	static class OppsByAlert{

		static String ALERT = 'Opportunity - Alert'

		static String FLEX = 'Opportunity - Flex'

		static String IN_MARKET = 'Opportunity - In-Market'

		static String ENGAGED = 'Opportunity - Engaged'

		static String SERVICE = 'Opportunity - Service'

		static String PENDING_SERVICE = 'Opportunity - Pending Service'

		static String MILEAGE = 'Opportunity - Mileage'

		static String WARRANTY = 'Opportunity - Warranty'

		static String CONTRACT_END = 'Opportunity - Contract End'
	}

	static class MyAgenda{

		static String TABLE_BODY = 'Opps Dash - My Agenda - Table Body'
	}
}
