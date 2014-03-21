/*
 * Copyright 2014 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kfs.sys.ConfigureContext;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.krad.datadictionary.AttributeDefinition;
import org.kuali.rice.krad.datadictionary.BusinessObjectEntry;
import org.kuali.rice.krad.service.PersistenceStructureService;

@ConfigureContext
public class DictionaryLookupAttributesTest extends KualiTestBase {
    public void testPrimaryKeysHaveDDAttributes() {
        final DataDictionaryService ddService = SpringContext.getBean(DataDictionaryService.class);
        final PersistenceStructureService persistenceStructureService = SpringContext.getBean(PersistenceStructureService.class);
        final ConfigurationService configService = SpringContext.getBean(ConfigurationService.class);

        List<String> missingPkFields = new ArrayList<String>();
        final String appName = configService.getPropertyValueAsString(CoreConstants.Config.APPLICATION_ID).toLowerCase();

        for (BusinessObjectEntry boEntry : ddService.getDataDictionary().getBusinessObjectEntries().values()) {
            final Class<?> boClazz = boEntry.getBusinessObjectClass();
            if (boClazz.getName().contains(appName) && ((org.kuali.rice.kns.datadictionary.BusinessObjectEntry)boEntry).getLookupDefinition() != null && persistenceStructureService.isPersistable(boClazz)) {
                List pkFieldNames = persistenceStructureService.getPrimaryKeys(boClazz);
                for (Object pkFieldNameAsObject : pkFieldNames) {
                    final String pkFieldName = (String)pkFieldNameAsObject;

                    final AttributeDefinition attrDefn = boEntry.getAttributeDefinition(pkFieldName);
                    if (attrDefn == null) {
                        missingPkFields.add(boClazz.getName()+" "+pkFieldName);
                    }
                }
            }
        }
        if (!missingPkFields.isEmpty()) {
            System.err.println(StringUtils.join(missingPkFields, "\n"));
        }
        assertTrue("Some business object are missing pk fields as attributes in the data dictionary", missingPkFields.isEmpty());
    }

    class FieldConversionCheck {
        private Class<?> boClazz;
        private String attributeName;
        private String jspFileName;
        public FieldConversionCheck(Class<?> boClazz, String attributeName, String jspFileName) {
            super();
            this.boClazz = boClazz;
            this.attributeName = attributeName;
            this.jspFileName = jspFileName;
        }
        public Class<?> getBoClazz() {
            return boClazz;
        }
        public String getAttributeName() {
            return attributeName;
        }

        public String getErrorMessage() {
            return "\""+boClazz.getName()+"\",\""+attributeName+"\",\""+jspFileName+"\"";
        }
    }

    protected List<FieldConversionCheck> getFieldConversionChecks() {
        List<FieldConversionCheck> checks = new ArrayList<FieldConversionCheck>();
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.CreditCardType.class,"financialDocumentCreditCardTypeCode","\\fp\\creditCardReceipts.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.CreditCardVendor.class,"financialDocumentCreditCardTypeCode","\\fp\\creditCardReceipts.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.CreditCardVendor.class,"financialDocumentCreditCardVendorNumber","\\fp\\creditCardReceipts.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.CreditCardType.class,"financialDocumentCreditCardTypeCode","\\fp\\creditCardReceipts.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.CreditCardVendor.class,"financialDocumentCreditCardTypeCode","\\fp\\creditCardReceipts.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.CreditCardVendor.class,"financialDocumentCreditCardVendorNumber","\\fp\\creditCardReceipts.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"name","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"code","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"name","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"code","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"name","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"code","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"name","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"code","\\fp\\dvNonEmployeeTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TaxIncomeClassCode.class,"code","\\fp\\dvNRATax.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.NonResidentAlienTaxPercent.class,"incomeTaxPercent","\\fp\\dvNRATax.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.NonResidentAlienTaxPercent.class,"incomeTaxPercent","\\fp\\dvNRATax.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.DisbursementPayee.class,"payeeIdNumber","\\fp\\dvPayment.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.DisbursementPayee.class,"payeeTypeCode","\\fp\\dvPayment.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.DisbursementPayee.class,"paymentReasonCode","\\fp\\dvPayment.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.PaymentDocumentationLocation.class,"paymentDocumentationLocationCode","\\fp\\dvPayment.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.Customer.class,"customerNumber","\\module\\ar\\cashControlDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.CustomerInvoiceLookup.class,"invoiceNumber","\\module\\ar\\customerCreditMemoInit.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.CustomerAddress.class,"customerAddressIdentifier","\\module\\ar\\customerInvoiceAddresses.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.CustomerAddress.class,"customerAddressIdentifier","\\module\\ar\\customerInvoiceAddresses.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"financialObjectCode","\\module\\ar\\customerInvoiceDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialSubObjectCode","\\module\\ar\\customerInvoiceDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Chart.class,"chartOfAccountsCode","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"accountNumber","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"chartOfAccountsCode","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"subAccountNumber","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"chartOfAccountsCode","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"accountNumber","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"financialObjectCode","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialSubObjectCode","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"chartOfAccountsCode","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"accountNumber","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ProjectCode.class,"code","\\module\\ar\\customerInvoiceDetailAccountingInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.Customer.class,"customerNumber","\\module\\ar\\customerInvoiceGeneral.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Organization.class,"organizationCode","\\module\\ar\\customerInvoiceOrganization.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"accountNumber","\\module\\ar\\customerInvoiceReceivableAccountingLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"subAccountNumber","\\module\\ar\\customerInvoiceReceivableAccountingLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"financialObjectCode","\\module\\ar\\customerInvoiceReceivableAccountingLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialSubObjectCode","\\module\\ar\\customerInvoiceReceivableAccountingLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ProjectCode.class,"code","\\module\\ar\\customerInvoiceReceivableAccountingLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.CustomerInvoiceLookup.class,"invoiceNumber","\\module\\ar\\customerInvoiceWriteoffInit.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.Customer.class,"customerNumber","\\module\\ar\\paymentApplicationApplyToInvoiceDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"chartOfAccountsCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"accountNumber","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"chartOfAccountsCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"accountNumber","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"subAccountNumber","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"financialObjectCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"chartOfAccountsCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialSubObjectCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"chartOfAccountsCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialObjectCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"accountNumber","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ProjectCode.class,"chartOfAccountsCode","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ProjectCode.class,"code","\\module\\ar\\paymentApplicationNonAr.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ar.businessobject.Customer.class,"customerNumber","\\module\\ar\\paymentApplicationUnappliedTab.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingCode","\\module\\cams\\barcodeInventoryErrorDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"campusCode","\\module\\cams\\barcodeInventoryErrorDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingCode","\\module\\cams\\barcodeInventoryErrorDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"campusCode","\\module\\cams\\barcodeInventoryErrorDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingRoomNumber","\\module\\cams\\barcodeInventoryErrorDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.cam.businessobject.AssetCondition.class,"assetConditionCode","\\module\\cams\\barcodeInventoryErrorDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"campusCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"campusCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"campusCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingRoomNumber","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"campusCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingRoomNumber","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.cam.businessobject.AssetCondition.class,"assetConditionCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.cam.businessobject.AssetCondition.class,"assetConditionCode","\\module\\cams\\barcodeInventoryErrorDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.SystemOptions.class,"universityFiscalYear","\\module\\ec\\detailLineImport.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ec.businessobject.EffortCertificationReportDefinition.class,"universityFiscalYear","\\module\\ec\\detailLineImport.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.ec.businessobject.EffortCertificationReportDefinition.class,"effortCertificationReportNumber","\\module\\ec\\detailLineImport.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Chart.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"accountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"subAccountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"accountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"financialObjectCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialSubObjectCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"accountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialObjectCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ProjectCode.class,"code","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Chart.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"accountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Account.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"subAccountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"accountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubAccount.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"financialObjectCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ObjectCode.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialSubObjectCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"chartOfAccountsCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"accountNumber","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.SubObjectCode.class,"financialObjectCode","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.ProjectCode.class,"code","\\module\\endow\\endowmentAccountingLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.Security.class,"id","\\module\\endow\\endowmentHoldingHistoryValueAdjustmentDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.Security.class,"id","\\module\\endow\\endowmentSecurityTransactionDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.RegistrationCode.class,"code","\\module\\endow\\endowmentSecurityTransactionDetails.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.KEMID.class,"kemid","\\module\\endow\\endowmentTransactionalLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.EndowmentTransactionCode.class,"code","\\module\\endow\\endowmentTransactionalLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.KEMID.class,"kemid","\\module\\endow\\endowmentTransactionalLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.endow.businessobject.EndowmentTransactionCode.class,"code","\\module\\endow\\endowmentTransactionalLines.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CommodityCode.class,"purchasingCommodityCode","\\module\\purap\\accountdistribution.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CommodityCode.class,"commodityDescription","\\module\\purap\\accountdistribution.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CampusParameter.class,"campusCode","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingCode","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingName","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"campusCode","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingStreetAddress","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressCityName","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressStateCode","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressZipCode","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressCountryCode","\\module\\purap\\bulkReceivingDelivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorNumber","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorHeaderGeneratedIdentifier","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorDetailAssignedIdentifier","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressLine1","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressLine2","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressCity","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressPostalCode","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressStateCode","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressInternationalProvince","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressCountryCode","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorAddressGeneratedIdentifier","\\module\\purap\\bulkReceivingVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.integration.cam.CapitalAssetManagementAsset.class,"capitalAssetNumber","\\module\\purap\\camsDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.integration.cam.CapitalAssetManagementAssetType.class,"capitalAssetTypeCode","\\module\\purap\\camsDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CampusParameter.class,"campusCode","\\module\\purap\\camsLocation.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingCode","\\module\\purap\\camsLocation.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"campusCode","\\module\\purap\\camsLocation.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingRoomNumber","\\module\\purap\\camsLocation.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.ContractManager.class,"contractManagerCode","\\module\\purap\\contractManagerAssignment.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CampusParameter.class,"campusCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingName","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"campusCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingStreetAddress","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressCityName","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressStateCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressZipCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Building.class,"buildingAddressCountryCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Room.class,"buildingRoomNumber","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingName","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingCityName","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingLine1Address","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingLine2Address","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingCityName","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingStateCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingPostalCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"receivingCountryCode","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.ReceivingAddress.class,"useReceivingIndicator","\\module\\purap\\delivery.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.NonResidentAlienTaxPercent.class,"incomeTaxPercent","\\module\\purap\\paymentRequestTaxInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.NonResidentAlienTaxPercent.class,"incomeTaxPercent","\\module\\purap\\paymentRequestTaxInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Organization.class,"organizationCode","\\module\\purap\\purapDocumentDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.coa.businessobject.Organization.class,"chartOfAccountsCode","\\module\\purap\\purapDocumentDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.ContractManager.class,"contractManagerName","\\module\\purap\\purapDocumentDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.ContractManager.class,"contractManagerCode","\\module\\purap\\purapDocumentDetail.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.UnitOfMeasure.class,"itemUnitOfMeasureCode","\\module\\purap\\puritems.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CommodityCode.class,"purchasingCommodityCode","\\module\\purap\\puritems.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.UnitOfMeasure.class,"itemUnitOfMeasureCode","\\module\\purap\\puritems.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.CommodityCode.class,"purchasingCommodityCode","\\module\\purap\\puritems.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorName","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorHeaderGeneratedIdentifier","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorDetailAssignedIdentifier","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressLine1","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressLine2","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressCity","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressPostalCode","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressStateCode","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorAddressGeneratedIdentifier","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorLine1Address","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorLine2Address","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorCityName","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorStateCode","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorZipCode","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorCountryCode","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorFaxNumber","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorAttentionName","\\module\\purap\\quotes.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorAddressGeneratedIdentifier","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorLine1Address","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorLine2Address","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorCityName","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorStateCode","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorCountryCode","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorFaxNumber","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorAttentionName","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorZipCode","\\module\\purap\\quoteVendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.UnitOfMeasure.class,"itemUnitOfMeasureCode","\\module\\purap\\receivingLineItems.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.UnitOfMeasure.class,"itemUnitOfMeasureCode","\\module\\purap\\receivingLineItems.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.purap.businessobject.VendorStipulation.class,"vendorStipulationDescription","\\module\\purap\\stipulationsAndInfo.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorHeaderGeneratedIdentifier","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorDetailAssignedIdentifier","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressLine1","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressLine2","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressCity","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressPostalCode","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressStateCode","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressInternationalProvince","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"defaultAddressCountryCode","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorAddress.class,"vendorAddressGeneratedIdentifier","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorCustomerNumber.class,"vendorCustomerNumber","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorContract.class,"vendorContractGeneratedIdentifier","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorHeaderGeneratedIdentifier","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.vnd.businessobject.VendorDetail.class,"vendorDetailAssignedIdentifier","\\module\\purap\\vendor.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfile.class,"firstName","\\module\\tem\\ent\\attendees.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfile.class,"middleName","\\module\\tem\\ent\\attendees.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfile.class,"lastName","\\module\\tem\\ent\\attendees.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"streetAddressLine1","\\module\\tem\\ent\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"streetAddressLine2","\\module\\tem\\ent\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"zipCode","\\module\\tem\\ent\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"countryCode","\\module\\tem\\ent\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"stateCode","\\module\\tem\\ent\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"cityName","\\module\\tem\\ent\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"name","\\module\\tem\\expenses\\actualExpenseLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"code","\\module\\tem\\expenses\\actualExpenseLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"name","\\module\\tem\\expenses\\importedExpenseLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.fp.businessobject.TravelCompanyCode.class,"code","\\module\\tem\\expenses\\importedExpenseLine.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.GroupTravelerForLookup.class,"groupTravelerId","\\module\\tem\\groupTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.GroupTravelerForLookup.class,"name","\\module\\tem\\groupTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.GroupTravelerForLookup.class,"groupTravelerTypeCode.code","\\module\\tem\\groupTravel.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.PrimaryDestination.class,"id","\\module\\tem\\perDiemExpenses.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.MileageRate.class,"expenseTypeCode","\\module\\tem\\perDiemExpenses.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"streetAddressLine1","\\module\\tem\\relo\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"streetAddressLine2","\\module\\tem\\relo\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"zipCode","\\module\\tem\\relo\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"countryCode","\\module\\tem\\relo\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"stateCode","\\module\\tem\\relo\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"cityName","\\module\\tem\\relo\\requester.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"streetAddressLine1","\\module\\tem\\traveler.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"streetAddressLine2","\\module\\tem\\traveler.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"zipCode","\\module\\tem\\traveler.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"countryCode","\\module\\tem\\traveler.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"stateCode","\\module\\tem\\traveler.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.TemProfileAddress.class,"cityName","\\module\\tem\\traveler.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.module.tem.businessobject.PrimaryDestination.class,"id","\\module\\tem\\tripInformation.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Bank.class,"bankCode","\\sys\\bankControl.tag"));
        checks.add(new FieldConversionCheck(org.kuali.kfs.sys.businessobject.Bank.class,"bankName","\\sys\\bankControl.tag"));
        return checks;
    }

    public void testFieldConversionFieldsHaveDDAttributes() {
        final DataDictionaryService ddService = SpringContext.getBean(DataDictionaryService.class);
        final ConfigurationService configService = SpringContext.getBean(ConfigurationService.class);

        List<String> missingFieldConversionFields = new ArrayList<String>();
        final String appName = configService.getPropertyValueAsString(CoreConstants.Config.APPLICATION_ID).toLowerCase();

        for (FieldConversionCheck check : getFieldConversionChecks()) {
            final Class<?> boClazz = check.getBoClazz();
            if (boClazz.getName().contains(appName)) {
                final BusinessObjectEntry boEntry = ddService.getDataDictionary().getBusinessObjectEntry(check.getBoClazz().getName());

                if (boEntry == null) {
                    System.err.println("Could not find BO Entry for: "+boClazz.getName());
                } else {
                    final AttributeDefinition attrDefn = boEntry.getAttributeDefinition(check.getAttributeName());
                    if (attrDefn == null) {
                        missingFieldConversionFields.add(check.getErrorMessage());
                    }
                }
            }
        }
        if (!missingFieldConversionFields.isEmpty()) {
            System.err.println(StringUtils.join(missingFieldConversionFields, "\n"));
        }
        assertTrue("Some business object are missing field conversion fields as attributes in the data dictionary", missingFieldConversionFields.isEmpty());
    }
}
