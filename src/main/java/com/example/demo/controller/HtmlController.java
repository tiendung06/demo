package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class HtmlController {
    private final TemplateEngine templateEngine;

    @Autowired
    public HtmlController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/read-html")
    public String readHtml() {
        return readHtmlFile();
    }

    public String readHtmlFile() {
        Context context = new Context();
        context.setVariable("exampleVariable", "Hello, Thymeleaf!");

        return templateEngine.process("hello", context);
    }
}