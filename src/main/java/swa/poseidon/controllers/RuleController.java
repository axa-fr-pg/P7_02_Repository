package swa.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import swa.poseidon.model.Rule;

import javax.validation.Valid;

@Controller
public class RuleController {
    // TODO: Inject Rule service

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all Rule, add to model
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Rule bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid Rule ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rule list
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rule by Id and to model then show to the form
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rule and return Rule list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rule by Id and delete the Rule, return to Rule list
        return "redirect:/ruleName/list";
    }
}
