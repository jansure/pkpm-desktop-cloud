/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pkpm.pdfconverterutil.test;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pkpm.pdfconverterutil.Consts;
import com.pkpm.pdfconverterutil.exceptions.NotSupportedDocumentException;
import com.pkpm.pdfconverterutil.office.DocConverter;
import com.pkpm.pdfconverterutil.office.DocxConverter;
import com.pkpm.pdfconverterutil.office.OdtConverter;
import com.pkpm.pdfconverterutil.office.OfficeDocConverter;
import com.pkpm.pdfconverterutil.office.OfficeDocConverterFactory;
import com.pkpm.pdfconverterutil.office.TxtConverter;
import com.pkpm.pdfconverterutil.services.OpenOfficeService;

import junit.framework.Assert;

/**
 *
 * @author obs
 */
public class OfficeDocConverterTest {
   List<File> docOfficeFiles;
   List<File> docxOfficeFiles;
   List<File> odtOfficeFiles;
   List<File> txtOfficeFiles;
   File tempDir;
    
    @BeforeClass
    public static void setUpClass() {
    	
    	try {
    		
    		List<Long> listenInPorts = new ArrayList<>(); 
            listenInPorts.add(8100L);
            OpenOfficeService.initialize(listenInPorts);
            OpenOfficeService.start();
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        OpenOfficeService.stop();
    }   
   
   
    @Before
    public void setUp() throws URISyntaxException {
        this.tempDir = com.google.common.io.Files.createTempDir();
        
        URL docFilesUrl = this.getClass().getResource("/office-files/doc");
        this.docOfficeFiles = Arrays.asList(new File(docFilesUrl.toURI()).listFiles());
        
        URL docxFilesUrl = this.getClass().getResource("/office-files/docx");
        this.docxOfficeFiles = Arrays.asList(new File(docxFilesUrl.toURI()).listFiles());
        
        URL odtFilesUrl = this.getClass().getResource("/office-files/odt");
        this.odtOfficeFiles = Arrays.asList(new File(odtFilesUrl.toURI()).listFiles());
        
        URL txtFilesUrl = this.getClass().getResource("/office-files/txt");
        this.txtOfficeFiles = Arrays.asList(new File(txtFilesUrl.toURI()).listFiles());   
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testOdtToPdf() throws Exception {
        processFiles(odtOfficeFiles, OdtConverter.class);
    }
    
    @Test
    public void testTxtToPdf() throws Exception {
        processFiles(txtOfficeFiles, TxtConverter.class);
    }
    
    @Test
    public void testDocToPdf() throws Exception {
        processFiles(docOfficeFiles, DocConverter.class);
    }
    
    @Test
    public void testDocxToPdf() throws Exception {
        processFiles(docxOfficeFiles, DocxConverter.class);
    }
    
    private void processFiles(List<File> fileList, Class converterClazz) throws 
            NotSupportedDocumentException, FileNotFoundException, IOException {
        OfficeDocConverter officeDocumentConverter;
        
        for (File f : fileList) {
           officeDocumentConverter = OfficeDocConverterFactory.getConverter(f.getAbsolutePath());
           assertThat(officeDocumentConverter, instanceOf(converterClazz));
           String dst = tempDir.getAbsolutePath() + f.getName() + ".pdf";
           Path dstPath = FileSystems.getDefault().getPath(dst);
           officeDocumentConverter.toPdf(dst);
           Assert.assertTrue(new File(dst).exists());
           String mime = Files.probeContentType(dstPath);
           // Will return null on OS X.
           // See: http://stackoverflow.com/questions/12407479/why-does-files-probecontenttype-return-null
//           Assert.assertEquals(Consts.PDFMetadata.MIME_TYPE, mime);
        }
    }
}