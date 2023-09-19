package com.example.demo.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping()
public class TestController {
    private final TemplateEngine templateEngine;

    public TestController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/pdf")
    public void exportFormRegisterFaceFinger(HttpServletResponse response) {
        Context context = getContext();
        String htmlContent = templateEngine.process("test", context);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] bytes = htmlContent.getBytes(StandardCharsets.UTF_8);
            String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
            ITextRenderer renderer = new ITextRenderer();
            Path path2 = Paths.get(new ClassPathResource("/fonts/SVN-Times New Roman.ttf").getPath());
            renderer.getFontResolver().addFont(path2.toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocumentFromString(utf8EncodedString);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
            renderer.finishPDF();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=data.pdf");
            IOUtils.copy(byteArrayInputStream, response.getOutputStream());
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Context getContext() {
        Context context = new Context();
        context.setVariable("name", "Đỗ Tiến Dũng");
        context.setVariable("birth", "06/08/2001");
        context.setVariable("nationality", "Việt Nam");
        context.setVariable("idCard", "001201016382");
        context.setVariable("date", "09/01/2022");
        context.setVariable("provider", "Cục trưởng Cục cảnh sát quản lý hành chính về trật tự xã hội");
        context.setVariable("origin", "Giao Tác, Liên Hà, Đông Anh, Hà Nội");
        context.setVariable("phone", "0344536552");
        return context;
    }
}