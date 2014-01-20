package com.ajurasz.util.pdf;

import com.ajurasz.model.*;
import com.ajurasz.util.forms.InvoiceForm;
import com.ajurasz.util.helper.NumberSpeaker;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.joda.time.DateTime;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Arek Jurasz
 */
public class GeneratePDF {
    private static final String TEMPLATE = "DOKUMENT_DOSTAWY.pdf";
    private static final String INVOICE = "INVOICE.pdf";
    private static final String FONT_NAME = "arial.ttf";

    private Company company;
    private String destination;

    //itext objects
    private PdfReader reader;
    private PdfStamper stamper;
    private AcroFields acroFields;

    //jasperreports objects
    private JasperDesign jasperDesign;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;
    private JRDataSource jrDataSource;



    public GeneratePDF(Company company, String dest) {
        if(!dest.endsWith(File.separator)) {
            dest = dest + File.separator;
        }

        this.company = company;
        this.destination = dest;
    }

    private void setFontForAcroFields(PdfStamper stamper) throws DocumentException, IOException {
        BaseFont bfont=BaseFont.createFont(new FileSystemResource(FONT_NAME).getPath(), "ISO-8859-2", BaseFont.EMBEDDED);
        ArrayList<BaseFont> subFonts= new ArrayList(1);
        subFonts.add(bfont);

        stamper.getAcroFields().setSubstitutionFonts(subFonts);
    }

    /**
     * Use this method to generate Documents
     */
    public void generate(Order order, boolean invoice) {

        try {

            FileSystemResource resource = new FileSystemResource(TEMPLATE);
            this.reader = new PdfReader(resource.getPath());
            this.destination = this.destination + order.getDocumentName() + ".pdf";
            this.stamper = new PdfStamper(this.reader, new FileOutputStream(this.destination));
            setFontForAcroFields(this.stamper);
            this.acroFields = this.stamper.getAcroFields();

            //company info
            acroFields.setField("companyName", company.getFullName());
            acroFields.setField("companyAddress", company.getAddress().getAddress());

            //customer info
            acroFields.setField("docNumber", order.getDocNumber());

            if(!invoice) {
                CustomerRegular customer = (CustomerRegular) order.getCustomer();
                Address address = customer.getAddress();
                acroFields.setField("customerName", customer.getLastName() + " " + customer.getFirstName());
                acroFields.setField("customerAddress", address.getAddress());
            } else {
                CustomerVat customer = (CustomerVat) order.getCustomer();
                Address address = customer.getAddress();
                acroFields.setField("customerName", customer.getName());
                acroFields.setField("customerAddress", address.getAddress());
            }

            //date
            acroFields.setField("date", order.getOrderDate().toString("dd.MM.yyyy") + " " +
                    company.getOwnerFirstName() + " " + company.getOwnerLastName());

            //set reason statically
            String r = "zużycie przez gospodarstwo domowe";
            acroFields.setField("reason1", r);
            acroFields.setField("reason2", r);
            acroFields.setField("reason3", r);
            acroFields.setField("reason4", r);
            acroFields.setField("reason5", r);

            //order details
            int counter = 1;
            for(OrderDetails orderDetails : order.getOrderDetails()) {
                String lp = "lp" + counter;
                String item = "item" + counter;
                String quantity = "quantity" + counter;
                String reasonA = "reason" + counter + "a";
                String cb = "cb" + counter;
                String cbA = "cb" + counter + "a";

                //fill row
                Item itemObj = orderDetails.getItem();
                acroFields.setField(lp, "" + counter);
                acroFields.setField(item, itemObj.getName() + "   " + itemObj.getCode());
                acroFields.setField(quantity, "" + orderDetails.getQuantity().intValue());
                if(orderDetails.getReason().isHomeUse()) {
                    acroFields.setField(cb, "Yes");
                } else {
                    acroFields.setField(cbA, "Yes");
                    acroFields.setField(reasonA, orderDetails.getReason().getDescription());
                }

                counter++;
            }

            stamper.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Use this method to generate report
     * @param orders list of Orders for specyfic company and time frame
     */
    public void generate(Report report, List<Order> orders) {

        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("CompanyName", this.company.getFullName());
        parametersMap.put("CompanyAddress", this.company.getAddress().getAddress());
        parametersMap.put("CompanyNip", this.company.getNip());
        parametersMap.put("CompanyCity", this.company.getAddress().getCity());
        parametersMap.put("Quoter", report.getQuoter());
        parametersMap.put("Year", "2014");

        try {

            JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1250");
            this.destination = this.destination + report.getReportName() + ".pdf";

            jasperReport = (JasperReport) JRLoader.loadObject(new FileSystemResource("report1.jasper").getFile());
            jrDataSource = new JRBeanCollectionDataSource(orders);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, jrDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, this.destination);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Use this method to generate Invoices
     */
    public void generate(InvoiceForm invoiceForm) {

        try {

            FileSystemResource resource = new FileSystemResource(INVOICE);
            this.reader = new PdfReader(resource.getPath());
            this.destination = this.destination + invoiceForm.getOrder().getDocumentInvoiceName() + ".pdf";
            this.stamper = new PdfStamper(this.reader, new FileOutputStream(this.destination));
            setFontForAcroFields(this.stamper);
            this.acroFields = this.stamper.getAcroFields();

            //date and place
            acroFields.setField("Data", invoiceForm.getOrder().getOrderDate().toString("dd.MM.yyyy"));
            acroFields.setField("DataMiejsce", invoiceForm.getOrder().getOrderDate().toString("dd-MM-yyyy") + " " + company.getAddress().getCity());
            acroFields.setField("NrFaktury", invoiceForm.getOrder().getInvoiceNumber());


            //company info
            acroFields.setField("Sprzedawca", company.getFullName());
            acroFields.setField("AdresSprzedawcy", company.getAddress().getAddress());
            acroFields.setField("NipSprzedawcy", company.getNip());
            acroFields.setField("Bank", company.getBank());
            acroFields.setField("Konto", company.getBankNumber());
            acroFields.setField("Telefon", company.getPhoneNumber());
            acroFields.setField("E-mail", company.getUsername());

            //customer info
            CustomerVat customer = (CustomerVat) invoiceForm.getOrder().getCustomer();
            acroFields.setField("Nabywca", customer.getName());
            acroFields.setField("AdresNabywcy", customer.getAddress().getAddress());
            acroFields.setField("NipNabywcy", customer.getNip());

            //payments
            acroFields.setField("FormaPlatnosci", invoiceForm.getPayment().getDescription());
            if(invoiceForm.getPaymentDate() == null)
                invoiceForm.setPaymentDate(DateTime.now());
            acroFields.setField("TerminPlatnosci", invoiceForm.getPaymentDate().toString("dd/MM/yyyy"));

            //order details
            int counter = 1;
            BigDecimal valueNetTotal = new BigDecimal(0);
            BigDecimal valueGrossTotal = new BigDecimal(0);
            BigDecimal valueVatTotal = new BigDecimal(0);
            BigDecimal quantityTotal = new BigDecimal(0);
            for(OrderDetails orderDetails : invoiceForm.getOrder().getOrderDetails()) {
                String lp = "Lp" + counter;
                String item = "Nazwa" + counter;
                String quantity = "Ilosc" + counter;
                String jm = "Jm" + counter;
                String vat = "StawkaVat" + counter;

                String priceNet = "CenaNetto" + counter;
                String valueNet = "WartoscNetto" + counter;
                String valueVat = "KwotaVat" + counter;
                String valueGross = "WartoscBrutto" + counter;

                //fill row
                Item itemObj = orderDetails.getItem();
                acroFields.setField(lp, "" + counter);
                acroFields.setField(item, itemObj.getName());
                acroFields.setField(quantity, "" + orderDetails.getQuantity().divide(new BigDecimal(1000)));
                acroFields.setField(jm, "tona");
                acroFields.setField(vat, "23%");

                BigDecimal priceNetResult = invoiceForm.isExcise()? itemObj.getPriceNetExcise() : itemObj.getPriceNet();
                BigDecimal valueNetResult = priceNetResult.multiply(orderDetails.getQuantity().divide(new BigDecimal(1000))).setScale(2, RoundingMode.HALF_UP);
                BigDecimal valueVatResult = valueNetResult.multiply(new BigDecimal(23)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
                BigDecimal valueGrossResult = valueNetResult.add(valueVatResult).setScale(2, RoundingMode.HALF_UP);

                acroFields.setField(priceNet, priceNetResult.toString().replace('.', ','));
                acroFields.setField(valueNet, valueNetResult.toString().replace('.', ','));
                acroFields.setField(valueVat, valueVatResult.toString().replace('.', ','));
                acroFields.setField(valueGross, valueGrossResult.toString().replace('.', ','));

                valueNetTotal = valueNetTotal.add(valueNetResult);
                valueVatTotal = valueVatTotal.add(valueVatResult);
                valueGrossTotal = valueGrossTotal.add(valueGrossResult);
                quantityTotal = quantityTotal.add(orderDetails.getQuantity().divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
                counter++;
            }

            //Summary
            acroFields.setField("WartoscNettoRazem", valueNetTotal.toString().replace('.', ','));
            acroFields.setField("WartoscNettoWtym", valueNetTotal.toString().replace('.', ','));
            acroFields.setField("StawkaVatWtym", "23%");
            acroFields.setField("KwotaVatRazem", valueVatTotal.toString().replace('.', ','));
            acroFields.setField("KwotaVatWtym", valueVatTotal.toString().replace('.', ','));
            acroFields.setField("WartoscBruttoRazem", valueGrossTotal.toString().replace('.', ','));
            acroFields.setField("WartoscBruttoWtym", valueGrossTotal.toString().replace('.', ','));

            acroFields.setField("DoZaplaty", valueGrossTotal.toString().replace('.', ',') + " zł");
            String[] numberInString = valueGrossTotal.toString().replace('.',',').split(",");
            acroFields.setField("KwotaSlownie", NumberSpeaker.speakNumber(valueGrossTotal.intValue()) + " złotych " +
                    numberInString[1] + "/100");


            acroFields.setField("Autor", company.getOwnerFirstName() + " " + company.getOwnerLastName());
            if(invoiceForm.isExcise())
                acroFields.setField("Uwaga", "Uwagi:\nFaktura zawiera naliczoną akcyzę w kwocie " +
                        quantityTotal.multiply(new BigDecimal(1.28)).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(23.8)).setScale(2, RoundingMode.HALF_UP) );
            stamper.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }
}
