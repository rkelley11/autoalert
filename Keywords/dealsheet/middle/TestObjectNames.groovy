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

final class TestObjectNames {	
	
	static String TBD = ''

	static class ExistingVehicle{	
	
		static def TERM_AND_PAYMENT = findTestObject('Object Repository/Dealsheet/Car Comparison Section/Existing Vehicle/Contract Info/Car Comparison - Existing Vehicle - Term and Payment')
		
	}

	static class MoreVehicleOffers{

		static String LINK	= 'Dealsheet - Car Comparison - Replacement - More Vehicle Offers - Link'

		static String LINK_TEXT	= 'Dealsheet - Car Comparison - Replacement - More Vehicle Offers - Link Text'

		static String OFFER_CONTAINER	= 'Dealsheet - Car Comparison - Replacement - More Vehicle Offers - Offer Container'
	}

	static class Proposal{

		static String BUTTON = 'Dealsheet - Car Comparison - Replacement - Proposal - Button'
	}
}