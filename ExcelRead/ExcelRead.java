package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelRead {
    private String excelFilePath;
    private File exceFile;
    private Workbook excelWorkBook;
    private String excelSheetName;
    private Sheet excelSheet;
    
    public ExcelRead() {
        super();
        excelFilePath = "C:\\Users\\eclipse-workspace\\DemoDay1\\final.xls";
    }
    public static void main(String[] args) {
        ExcelRead jxlReader = new ExcelRead();
        jxlReader.getContentsFromExcel();
    }
    
    public void getContentsFromExcel() {
        exceFile = new File(excelFilePath);
        if (!exceFile.exists()) {
            throw new RuntimeException("Unable to find the file : " + excelFilePath);
        }
        try {
            excelWorkBook = Workbook.getWorkbook(exceFile);
            if (excelSheetName == null) {
                excelSheet = excelWorkBook.getSheet(0);
            } else {
                excelSheet = excelWorkBook.getSheet(excelSheetName);
            }
        } catch (BiffException biffe) {
            biffe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        int columns = excelSheet.getColumns();
        int rows = excelSheet.getRows();
        System.out.println("Excel Sheet Name -> " + excelSheet.getName());
        System.out.println();
        System.out.println("Excel Sheet Contents ");
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("two.pdf"));
        } catch (FileNotFoundException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        PdfPTable table = new PdfPTable(8);
        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                String attributeName = excelSheet.getCell(col, r).getContents().trim();
                if (!attributeName.equalsIgnoreCase("")) {
                    PdfPCell header = new PdfPCell();
                    Phrase phr = new Phrase();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    phr.add(attributeName);
                    header.setPhrase(phr);
                    table.addCell(header);
                    System.out.print(attributeName + " ");
                }
            }
            System.out.println();
        }
        try {
            document.add(table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.close();
    }
}