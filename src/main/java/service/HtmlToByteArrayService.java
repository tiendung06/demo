package service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class HtmlToByteArrayService {
    private final TemplateEngine templateEngine;

    public HtmlToByteArrayService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] getHtmlAsByteArray() throws IOException {
        // Tạo một Context để truyền dữ liệu vào template
        Context context = new Context();
        context.setVariable("message", "Hello, Thymeleaf!");

        // Đọc và chuyển đổi template HTML thành một mảng byte
        String htmlContent = templateEngine.process("templates/index.html", context);
        return htmlContent.getBytes();
    }
}

