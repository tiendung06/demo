package com.example.demo.controller;

import com.example.demo.model.CheckBoxModel;
import com.example.demo.model.PersonModel;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class HomeController {
    @GetMapping()
    public String hello(Model model) {
        model.addAttribute("name", "Text");

        model.addAttribute("image", "https://source.unsplash.com/random");

        CheckBoxModel checkBoxModel = new CheckBoxModel();
        checkBoxModel.setValue2(true);
        model.addAttribute("checkBoxModel", checkBoxModel);

        PersonModel person = new PersonModel();
        person.setGender("male");
        model.addAttribute("person", person);

        return "index";
    }

    @GetMapping("/print")
    public void PrintFormRegister(HttpServletResponse response) {
        try {
            String content = "This is a sample file content.";
            byte[] data = content.getBytes(StandardCharsets.UTF_8);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=data.pdf");
            IOUtils.copy(byteArrayInputStream, response.getOutputStream());
        } catch (Exception e) {
            ResponseEntity.ok().body("Can't get form");
        }
    }
}
