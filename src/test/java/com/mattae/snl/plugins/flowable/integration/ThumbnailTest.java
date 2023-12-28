/*
package com.mattae.snl.plugins.flowable.integration;

import co.elastic.thumbnails4j.core.ThumbnailingException;
import com.aspose.cells.*;
import com.itextpdf.licensing.base.LicenseKey;
import com.itextpdf.pdfoffice.OfficeConverter;
import com.itextpdf.pdfoffice.OfficeDocumentConverterProperties;
import com.itextpdf.pdfoffice.OfficePageRange;
import io.github.makbn.thumbnailer.exception.ThumbnailerException;
import io.github.makbn.thumbnailer.exception.ThumbnailerRuntimeException;
import io.github.makbn.thumbnailer.util.ResizeImage;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ThumbnailTest {
    public ThumbnailTest() throws ThumbnailingException {
    }

    @Test
    public void testNullKey() {
        Map<String, Object> m = new HashMap<>();
        m.put(null, "The value");
        m.put(null, "The value1");
        assertEquals("The value1", m.get(null));

        Set<String> values = new HashSet<>();
        values.add(null);
        values.add(null);
        assertEquals(1, values.size());
        assertNull(values.iterator().next());
    }

    @Test
    public void generate() throws Exception {
       */
/* LocaleOptions.setLocale(new Locale("en", "US"));
        Document doc = new Document("/home/mattae/Downloads/CISSP All in one Exam Guide ( PDFDrive ).pdf");

        int pageIndex = 1;
        Page page = doc.getPages().get_Item(pageIndex);

        FileOutputStream imageStream = new FileOutputStream("Thumbnails_" + pageIndex + ".jpg");
        Resolution resolution = new Resolution(300);
        JpegDevice jpegDevice = new JpegDevice(55, 72, resolution, 600);
        jpegDevice.process(page, imageStream);
        try {
            imageStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        *//*


        Workbook book = new Workbook("/home/mattae/Documents/AnyDesk/Matthew Edor_TER_Feb_23.xlsm");

// Define ImageOrPrintOptions
        ImageOrPrintOptions imgOptions = new ImageOrPrintOptions();
// Set the vertical and horizontal resolution
        imgOptions.setVerticalResolution(200);
        imgOptions.setHorizontalResolution(200);
// Set the image's format
        imgOptions.setImageType(ImageType.JPEG);
// One page per sheet is enabled
        imgOptions.setOnePagePerSheet(true);

// Get the first worksheet
        Worksheet sheet = book.getWorksheets().get(0);
// Render the sheet with respect to specified image/print options
        SheetRender sr = new SheetRender(sheet, imgOptions);
// Render the image for the sheet
        sr.toImage(0,  "mythumb.jpg");

// Creating Thumbnail
        java.awt.Image img = ImageIO.read(new File("mythumb.jpg")).getScaledInstance(300, 400, BufferedImage.SCALE_SMOOTH);
        BufferedImage img1 = new BufferedImage(300, 400, BufferedImage.TYPE_INT_RGB);
        img1.createGraphics().drawImage(
            ImageIO.read(new File("mythumb.jpg")).getScaledInstance(300, 400, img.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(img1, "jpg", new File( "GTOfWorksheet_out.jpg"));

        File input = new File("/home/mattae/Documents/2023 Goal Setting Form.docx");
        File pdfOutFile = new File("out_office.pdf");
        File demoFile = new File("input.docx");
        // converts pages between 1 to 8
        LicenseKey.loadLicenseFile(new File("/home/mattae/Downloads/3eee1fb5b50e5fdc1222b3eaf8e21166882247d53e4019f1106bb78e7f661dd1.json"));
        OfficeConverter.convertOfficeDocumentToPdf(input, pdfOutFile, new OfficeDocumentConverterProperties()
            .setPageRange(new OfficePageRange().addPageSequence(1, 1)));
       */
/* Thumbnailer thumbnailer = new XLSXThumbnailer();
        List<Dimensions> outputDimensions = Collections.singletonList(new Dimensions(300, 400));
        BufferedImage output = thumbnailer.getThumbnails(input, outputDimensions).get(0);
        ImageIO.write(output, "jpg", new File("GTOfWorksheet_out1.jpg"));*//*

       */
/* Thumbnailer thumbnailer = new PDFThumbnailer();
        List<Dimensions> outputDimensions = Collections.singletonList(new Dimensions(100, 100));
        BufferedImage output = thumbnailer.getThumbnails(input, outputDimensions).get(0);
        ImageIO.write(output, "jpg", new File("GTOfWorksheet_out1.jpg"));

        String[] args = new String[]{};*//*

//generateThumbnail1(input, new File("thumbnail.jpg"));
       */
/* JThumbnailer jThumbnailer = JThumbnailerStarter.init(args);

        File in = new File("/home/mattae/Downloads/Apress - Pro JPA 2 Mastering the Java Persistence API  ( PDFDrive ).pdf");

        ThumbnailCandidate candidate = new ThumbnailCandidate(in, "unique_code");

        jThumbnailer.run(candidate, new ThumbnailListener() {
            @Override
            public void onThumbnailReady(String hash, File thumbnail) {
                try {
                    Files.copy(thumbnail.toPath(), Path.of(thumbnail.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                }
            }

            @Override
            public void onThumbnailFailed(String hash, String message, int code) {
                // handle the situation
            }
        });
        jThumbnailer.close();*//*

    }

   */
/* public void generateThumbnail1(File input, File output) throws ThumbnailerException {
        try {
            Workbook workbook = new Workbook();
            workbook.loadFromFile(input.getAbsolutePath());
            workbook.getConverterSetting().setSheetFitToPage(true);
            File outputTmp = File.createTempFile("jthumbnailer", ".pdf");
            workbook.saveToFile(outputTmp.getAbsolutePath(), FileFormat.PDF);
            this.generateThumbnail(outputTmp, output);
            outputTmp.deleteOnExit();
        } catch (Exception var5) {
            throw new ThumbnailerException(var5);
        }
    }*//*



    private PDDocument getDocument(File input) throws IOException {
        return PDDocument.load(input);
    }

    public void generateThumbnail(File input, File output) throws ThumbnailerException, ThumbnailerRuntimeException {
        if (!Files.exists(input.toPath())) {
            throw new ThumbnailerException("input file does not exist");
        } else if (input.length() == 0L) {
            throw new ThumbnailerException("File is empty");
        } else {
            FileUtils.deleteQuietly(output);

            try {
                PDDocument document = this.getDocument(input);

                try {
                    BufferedImage tmpImage = this.writeImageFirstPage(document);
                    if (tmpImage.getWidth() == this.thumbWidth) {
                        ImageIO.write(tmpImage, "PNG", output);
                    } else {
                        ResizeImage resizer = new ResizeImage(this.thumbWidth, this.thumbHeight);
                        resizer.setResizeMethod(2);
                        resizer.setInputImage(tmpImage);
                        resizer.writeOutput(output);
                    }
                } catch (Throwable var7) {
                    if (document != null) {
                        try {
                            document.close();
                        } catch (Throwable var6) {
                            var7.addSuppressed(var6);
                        }
                    }

                    throw var7;
                }

                if (document != null) {
                    document.close();
                }

            } catch (IllegalArgumentException var8) {
                throw new ThumbnailerRuntimeException(var8.getMessage());
            } catch (IOException var9) {
                throw new ThumbnailerException(var9);
            }
        }
    }

    private BufferedImage writeImageFirstPage(PDDocument document) throws IOException {
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        return  null;//return pdfRenderer.renderImageWithDPI(0, 72.0F, ImageType.RGB);
    }

    protected int thumbHeight = 400;
    protected int thumbWidth = 300;
}
*/
