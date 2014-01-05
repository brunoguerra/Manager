package com.ajurasz.util.pdf;

import com.ajurasz.model.*;
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
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
            acroFields.setField("date", order.getOrderDate().toString("dd/MM/yyyy"));

            //order details
            int counter = 1;
            for(OrderDetails orderDetails : order.getOrderDetails()) {
                String lp = "" + counter;
                String item = "item" + counter;
                String quantity = "quantity" + counter;
                String reason = "reason" + counter;
                String asterix = "asterix" + counter;

                //fill row
                Item itemObj = orderDetails.getItem();
                acroFields.setField(lp, "" + counter);
                acroFields.setField(item, itemObj.getName() + "   " + itemObj.getCode());
                acroFields.setField(quantity, "" + orderDetails.getQuantity().intValue());
                acroFields.setField(reason, orderDetails.getReason().getDescription());
                if(!orderDetails.getReason().isHomeUse()) {
                    acroFields.setField(asterix, "*");
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
}
