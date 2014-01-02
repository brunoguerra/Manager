package com.ajurasz.util.pdf;

import com.ajurasz.model.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Arek Jurasz
 */
public class GeneratePDF {
    private static final String TEMPLATE = "DOKUMENT_DOSTAWY.pdf";
    private static final String FONT_NAME = "arial.ttf";

    private Company company;
    private Order order;
    private PdfReader reader;
    private PdfStamper stamper;
    private AcroFields acroFields;
    private String destination;

    public GeneratePDF(Company company, Order order, String dest) {
        if(!dest.endsWith(File.separator)) {
            dest = dest + File.separator;
        }
        try {

            this.company = company;
            this.order = order;
            FileSystemResource resource = new FileSystemResource(TEMPLATE);
            this.reader = new PdfReader(resource.getPath());
            this.destination = dest + order.getDocumentName() + ".pdf";
            this.stamper = new PdfStamper(this.reader, new FileOutputStream(this.destination));
            setFontForAcroFields(this.stamper);
            this.acroFields = this.stamper.getAcroFields();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFontForAcroFields(PdfStamper stamper) throws DocumentException, IOException {
        BaseFont bfont=BaseFont.createFont(new FileSystemResource(FONT_NAME).getPath(), "ISO-8859-2", BaseFont.EMBEDDED);
        ArrayList<BaseFont> subFonts= new ArrayList(1);
        subFonts.add(bfont);

        stamper.getAcroFields().setSubstitutionFonts(subFonts);
    }

    public String generate() {

        Address address = order.getCustomer().getAddress();
        try {
            //company info
            acroFields.setField("companyName", company.getFullName());
            acroFields.setField("companyAddress", company.getAddress().getAddress());

            //customer info
            acroFields.setField("docNumber", order.getDocNumber());
            acroFields.setField("customerName", order.getCustomer().getLastName() + " " + order.getCustomer().getFirstName());
            acroFields.setField("customerAddress", address.getAddress());

            //date
            acroFields.setField("date", order.getDate().toString("dd/MM/yyyy"));

            //order details
            int counter = 1;
            for(OrderDetails orderDetails : order.getOrderDetails()) {
                String lp = "" + counter;
                String item = "item" + counter;
                String quantity = "quantity" + counter;
                String reason = "reason" + counter;

                //fill row
                Item itemObj = orderDetails.getItem();
                acroFields.setField(lp, "" + counter);
                acroFields.setField(item, itemObj.getName() + " " + itemObj.getCode());
                acroFields.setField(quantity, "" + orderDetails.getQuantity().intValue());
                acroFields.setField(reason, orderDetails.getReason().getDescription());

                counter++;
            }

            stamper.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return destination;
    }
}
