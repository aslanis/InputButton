package ru.ncedu.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ncedu.java.domain.NewData;
import ru.ncedu.java.repository.NewDataRepos;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/del/{id}")
    public String delete(@PathVariable(value = "id") long id, Model model) {

        //NewData data = newdatarepos.findById(id).orElseThrow();
        newdatarepos.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id", required = false) long id, Model model) {

        if (!newdatarepos.existsById(id)) { return  "redirect:/";}

        Optional<NewData> data = newdatarepos.findById(id);
        ArrayList<NewData> res = new ArrayList<>();
        data.ifPresent(res::add);
        model.addAttribute("data", res);

        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editsave(@PathVariable(value = "id", required = false) long id, @RequestParam(required = false) String text, Model model) {

        NewData data = newdatarepos.findById(id).orElseThrow();

        data.setText(text);

        newdatarepos.save(data);

        return "redirect:/";
    }

}
