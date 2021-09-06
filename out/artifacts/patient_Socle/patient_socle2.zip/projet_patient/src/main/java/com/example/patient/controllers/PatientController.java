package com.example.patient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "<h1> Bonjour <h1>";

    }
    @RequestMapping("/addFirstTest")
    public String addPatientFirstTest(Model model){
        model.addAttribute("name","yousra");
        return "add_patient";
    }
    @RequestMapping("/add")
    public String addPatient(Model model){
        model.addAttribute("titre","ajouter un patient");
        return "patient/add_edit";
    }
    @RequestMapping("/edit/{id}")
    public String editPatient(@PathVariable int id, Model model){
        model.addAttribute("titre","modifier le patient");
        return "patient/add_edit";
    }
    @RequestMapping("/all")
    public String getAllPatients(Model model){
        model.addAttribute("titre","Liste des patients ");
        return "patient/list";
    }
}
