package com.example.demo.controller;

import com.example.demo.model.CheckBoxModel;
import com.example.demo.model.PersonModel;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping()
public class TestController {
    private final TemplateEngine templateEngine;

    public TestController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/file")
    @ResponseBody
    public ResponseEntity<?> downloadFile() {
        String content = "This is a sample file content.";
        byte[] data = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sample.txt");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/download")
    public void PrintFormRegister(HttpServletResponse response) {
        try {
            byte[] data = readHtmlFile().getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            response.setContentType("application/html");
            response.setHeader("Content-Disposition", "attachment; filename=data.html");
            IOUtils.copy(byteArrayInputStream, response.getOutputStream());
        } catch (Exception e) {
            ResponseEntity.ok().body("Can't get form");
        }
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() throws Exception {
        byte[] pdfBytes = convertHtmlToPdf(readHtmlFile());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    public byte[] convertHtmlToPdf(String htmlContent) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            return outputStream.toByteArray();
        }
    }

    public String readHtmlFile() {
        Context context = new Context();
        context.setVariable("name", "Hello, Thymeleaf!");
        context.setVariable("image", "https://source.unsplash.com/random");

        CheckBoxModel checkBoxModel = new CheckBoxModel();
        checkBoxModel.setValue2(true);
        context.setVariable("checkBoxModel", checkBoxModel);

        PersonModel person = new PersonModel();
        person.setGender("male");
        context.setVariable("person", person);
        return templateEngine.process("index", context);
    }

    @GetMapping("/pdf")
    public void exportPdf(HttpServletResponse response) throws Exception {
        byte[] pdfBytes = exportToPdf(readHtmlFile());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfBytes);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=data.pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }

    public byte[] exportToPdf(String htmlContent) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        renderer.layout();
        renderer.createPDF(pdfOutputStream);
        renderer.finishPDF();
        return pdfOutputStream.toByteArray();
    }
}