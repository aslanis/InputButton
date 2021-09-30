package ru.ncedu.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ncedu.java.domain.NewData;
import ru.ncedu.java.repository.NewDataRepos;

@Controller
public class GreetingController {
    @Autowired
    private NewDataRepos newdatarepos;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World")
                                       String name, Model model) {

        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Model model) {

        Iterable<NewData> data = newdatarepos.findAll();

        model.addAttribute("data", data);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, Model model) {

        NewData newdata = new NewData(text);
        newdatarepos.save(newdata);

        Iterable<NewData> data = newdatarepos.findAll();
        model.addAttribute("data", data);

        return "main";
    }
}
