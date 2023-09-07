package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class HtmlReaderService {

    private final TemplateEngine templateEngine;

    @Autowired
    public HtmlReaderService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String readHtmlFile() {
        Context context = new Context();
        context.setVariable("exampleVariable", "Hello, Thymeleaf!");

        String htmlContent = templateEngine.process("index", context);

        return htmlContent;
    }
}

