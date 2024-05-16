/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author mahpi
 */
public class GeneratePDF {
    
    public void WDReportPDF(JTable table, String filepath, String title, String workDoneData) throws FileNotFoundException{
        PdfWriter pdfWriter = new PdfWriter(filepath);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
        Document document = new Document(pdfDocument);
        
        float tableCol = 190f;
        float titleCol = 285f;
        float titleCol2 = titleCol + 150f;
        float titleWidth[] = {titleCol2, titleCol};
        float fullwidth[] = {tableCol * 5};
        Paragraph gap = new Paragraph("\n");
        
        Table titleTable = new Table(titleWidth);
        titleTable.addCell(new Cell().add(title).setFontSize(20).setBold().setBorder(Border.NO_BORDER));
        Table nestedTable = new Table(new float[]{titleCol/2, titleCol/2});
        nestedTable.addCell(headerTextLeft("Company: "));
        nestedTable.addCell(headerTextRight("YoYo Furniture Sdn bhd."));
        nestedTable.addCell(headerTextLeft("Address: "));
        nestedTable.addCell(headerTextRight("C-B-3,\nJalan CheeseB,\nCheeseBurger Town,\n47300 Kuala Lumpur"));
        
        titleTable.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
        Border border = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(fullwidth);   
        divider.setBorder(border);
        
        Table resultTable = new Table(table.getColumnCount());
        for (int i = 0; i < table.getColumnCount(); i++) {
            // Create a cell with the column name
            Cell headerCell = new Cell().add(table.getColumnName(i));

            // Calculate the width based on the length of the column name
            float width = Math.max(100, table.getColumnName(i).length() * 8); // Adjust the multiplier as needed

            // Set the width of the header cell
            headerCell.setWidth(UnitValue.createPointValue(width));

            // Add the header cell to the table
            resultTable.addHeaderCell(headerCell);
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                resultTable.addCell(table.getValueAt(i, j).toString());
            }
        }
        
        document.add(titleTable);
        document.add(gap);
        document.add(divider);
        document.add(gap);
        document.add(resultTable);
        document.add(gap);
        Paragraph workDoneParagraph = new Paragraph(workDoneData);
        document.add(workDoneParagraph);
        
        document.close();
    }
    
    public void ACReportPDF(JTable table, String filepath, String title, String salesOrderData) throws FileNotFoundException{
        PdfWriter pdfWriter = new PdfWriter(filepath);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
        Document document = new Document(pdfDocument);
        
        float tableCol = 190f;
        float titleCol = 285f;
        float titleCol2 = titleCol + 150f;
        float titleWidth[] = {titleCol2, titleCol};
        float fullwidth[] = {tableCol * 5};
        Paragraph gap = new Paragraph("\n");
        
        Table titleTable = new Table(titleWidth);
        titleTable.addCell(new Cell().add(title).setFontSize(20).setBold().setBorder(Border.NO_BORDER));
        Table nestedTable = new Table(new float[]{titleCol/2, titleCol/2});
        nestedTable.addCell(headerTextLeft("Company: "));
        nestedTable.addCell(headerTextRight("YoYo Furniture Sdn bhd."));
        nestedTable.addCell(headerTextLeft("Address: "));
        nestedTable.addCell(headerTextRight("C-B-3,\nJalan CheeseB,\nCheeseBurger Town,\n47300 Kuala Lumpur"));
        
        titleTable.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
        Border border = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(fullwidth);   
        divider.setBorder(border);
        
        Table resultTable = new Table(table.getColumnCount());
        for (int i = 0; i < table.getColumnCount(); i++) {
            // Create a cell with the column name
            Cell headerCell = new Cell().add(table.getColumnName(i));

            // Calculate the width based on the length of the column name
            float width = Math.max(100, table.getColumnName(i).length() * 8); // Adjust the multiplier as needed

            // Set the width of the header cell
            headerCell.setWidth(UnitValue.createPointValue(width));

            // Add the header cell to the table
            resultTable.addHeaderCell(headerCell);
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                resultTable.addCell(table.getValueAt(i, j).toString());
            }
        }
        
        document.add(titleTable);
        document.add(gap);
        document.add(divider);
        document.add(gap);
        document.add(resultTable);
        document.add(gap);
        Paragraph salesOrderParagraph = new Paragraph(salesOrderData);
        document.add(salesOrderParagraph);
        
        document.close();
    }
    
    public void panelToPDF(JPanel panel, String filepath) throws IOException {
        // Create a BufferedImage to capture the panel
        BufferedImage bufferedImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        
        // Apply rendering hints for quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Render the panel to the BufferedImage
        panel.printAll(g2d);
        g2d.dispose();
        
        // Convert the BufferedImage to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        
        // Use iText to write the image to a PDF
        PdfWriter writer = new PdfWriter(filepath);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        
        ImageData imageData = ImageDataFactory.create(imageBytes);
        Image image = new Image(imageData);
        
        // Add image to document
        document.add(image);
        
        // Close the document
        document.close();
    }
    
    static Cell headerTextLeft(String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    
    static Cell headerTextRight(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    
}
