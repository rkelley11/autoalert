package sql

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

final class Queries{

	static class Dealsheet{

		static String URLS = "select distinct top 5000 aac.URL from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[Replacement New Used]='New' and " +
		"aac.[New Used]='Used'"

		static String NOT_PAID_OFF_WITH_OPP = "select distinct top 5000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[Replacement New Used]='New' and " +
		"aac.[New Used]='Used' and " +
		"aac.[Estimated Payoff] > 0 and " +
		"(aac.[_Is Alert]='Yes' or " +
		"aac.[_Is Appointment]='Yes' or " +
		"aac.[_Is Contract End]='Yes' or " +
		"aac.[_Is Conversion Alert]='Yes' or " +
		"aac.[_Is Flex Alert]='Yes' or " +
		"aac.[_Is In-Market]='Yes' or " +
		"aac.[_Is In-Market Engaged]='Yes' or " +
		"aac.[_Is Mileage Alert]='Yes' or " +
		"aac.[_Is Service]='Yes' or " +
		"aac.[_Is Warranty Alert]='Yes')"

		static String MULTIPLE_VEHICLE_OFFERS = 'select top 5000 e.EntityID ' +
		'from autoalert_calculation.dbo.salecalculation_mvo mvo ' +
		'join AutoAlert.dbo.entity e on e.entitykeyid = mvo.SaleID and e.EntityTypeID < 3 ' +
		'where mvo.DealerID='+ GlobalVariable.Dealer_Id

		static String MULTIPLE_VEHICLE_OFFERS_ANY_DEALER = 'select top 5000 e.EntityID ' +
		'from autoalert_calculation.dbo.salecalculation_mvo mvo ' +
		'join AutoAlert.dbo.entity e on e.entitykeyid = mvo.SaleID and e.EntityTypeID < 3 '

		static String MULTIPLE_HOME_PHONES = 'select top 5000 cch.CustomerID ' +
		'from CustomerContactHistory cch ' +
		'where cch.ContactID=2 ' +
		'group by cch.CustomerID ' +
		'having count(*) > 1 '

		static String CONTAINING_EMAIL = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.Email<>''"

		static String CONTAINING_ALL_PHONES = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[Home Phone]<>'' and " +
		"aac.[Work Phone]<>'' and " +
		"aac.[Cell Phone]<>''"

		static String CONTAINING_ALL_PHONES_AND_EMAIL = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[Home Phone]<>'' and " +
		"aac.[Work Phone]<>'' and " +
		"aac.[Cell Phone]<>'' and " +
		"aac.Email<>''"

		static String CONTAINING_ALL_PHONES_AND_EMAIL2 = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[Home Phone]<>'' and " +
		"aac.[Work Phone]<>'' and " +
		"aac.[Cell Phone]<>'' and " +
		"aac.Email<>''"

		// Has opportunities associated with a dealsheet
		static String HAS_ALERT_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is Alert]='Yes'"

		static String HAS_FLEX_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is Flex Alert]='Yes'"

		static String HAS_IN_MARKET_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is In-Market]='Yes'"

		static String HAS_ENGAGED_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is In-Market Engaged]='Yes'"

		// This query should only be used on data where nothing is done to it.  Only for reading.
		static String HAS_SERVICE_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac.[_Is Service]='Yes'"

		// This query should only be used on data where nothing is done to it.  Only for reading.
		static String HAS_PENDING_SERVICE_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac.[_Is Appointment]='Yes'"

		static String HAS_MILEAGE_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is Mileage Alert]='Yes'"

		static String HAS_WARRANTY_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is Warranty Alert]='Yes'"

		static String HAS_CONTRACT_END_OPP = "select distinct top 1000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[_Is Contract End]='Yes'"

		static String NOT_PAID_OFF_WITH_A_AND_I_OPPS = "select distinct top 500 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac.DealerName like '%Demo' and " +
		"aac.[Replacement New Used]='New' and " +
		"aac.[New Used]='Used' and " +
		"aac.[Estimated Payoff] > 0 and " +
		"aac.[_Is Alert]='Yes' and " +
		"aac.[_Is In-Market]='Yes'"

		// Address
		static String COMPLETE_ADDRESS = "select distinct top 5000 * from AutoAlert_Calculation.dbo.vw_Search aac " +
		"where aac._DealerID=" + GlobalVariable.Dealer_Id + " and " +
		"aac.[Replacement New Used]='New' and " +
		"aac.[New Used]='Used' and " +
		"aac.Address<>'' and " +
		"aac.State<>'' and " +
		"aac.City<>'' and " +
		"aac.[Zip Code]<>''"

		// Print Offers
		static String PRINT_OFFERS = "select distinct top 100 * from AutoAlert_Calculation.dbo.vw_Search " +
		"where _DealerID='1294' " +
		"and [Replacement Full Trim Name]<>''"
	}
}
