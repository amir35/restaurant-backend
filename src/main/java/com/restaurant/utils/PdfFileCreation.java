package com.restaurant.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.amir35.logutil.TimingLog;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class PdfFileCreation {

    @TimingLog
    public byte[] generatePdfContent(Map<String, Object> requestMap) {
        try {
            String data = "Name: "+requestMap.get("name") + "\n" +
                    "Contact Number: "+requestMap.get("contactNumber") + "\n" +
                    "Email: "+requestMap.get("email") + "\n" +
                    "Payment Method: "+requestMap.get("paymentMethod");

            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            document.open();
            setRectangleInPdf(document);

            Paragraph para = new Paragraph("Bismillah Cafe", getFont("Header"));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);

            Paragraph para2 = new Paragraph(data + "\n \n", getFont("Data"));
            document.add(para2);

            PdfPTable productTable = new PdfPTable(5);
            productTable.setWidthPercentage(100);
            addTableHeader(productTable);

            JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
            for (int i = 0; i < jsonArray.length(); i++) {
                addRows(productTable, CafeUtils.getMapFromJson(jsonArray.getString(i)));
            }
            document.add(productTable);

            Paragraph para3 = new Paragraph("Total: " + requestMap.get("totalAmount") + "\n"
                    + "Thank you for Visiting. Please Visit Again!!", getFont("Data"));
            document.add(para3);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setRectangleInPdf(Document document) throws DocumentException {

        Rectangle rect = new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private void addTableHeader(PdfPTable productTable) {
        //log.info("Inside addTableHeader");
        Stream.of("Name","Category", "Quantity", "Price", "Sub Total")
                .forEach(column -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(column));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    productTable.addCell(header);
                });
    }

    private void addRows(PdfPTable productTable, Map<String, Object> mapFromJson) {
        //log.info("Inside addRows");
        productTable.addCell((String) mapFromJson.get("name"));
        productTable.addCell((String) mapFromJson.get("category"));
        //productTable.addCell((String) mapFromJson.get("quantity"));

        productTable.addCell(String.valueOf(mapFromJson.get("quantity")));

        // Convert Double to String
        Double price = (Double) mapFromJson.get("price");
        String priceString = String.valueOf(price);
        productTable.addCell(priceString);

        Double total = (Double) mapFromJson.get("total");
        String totalString = String.valueOf(total);
        productTable.addCell(totalString);

        //productTable.addCell(Double.toString( (Double) mapFromJson.get("price")));
        //productTable.addCell(Double.toString( (Double) mapFromJson.get("total")));
    }

    private Font getFont(String type) {
        //log.info("Inside getFont");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }
}
