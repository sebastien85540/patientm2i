package com.example.patient.controllers;

import com.example.patient.entities.PatientEntity;
import com.example.patient.entities.VilleEntity;
import com.example.patient.repositories.PatientRepository;
import com.example.patient.repositories.VilleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;
    private final VilleRepository villeRepository;

    public PatientController(PatientRepository patientRepository, VilleRepository villeRepository) {
        this.patientRepository = patientRepository;
        this.villeRepository = villeRepository;
    }

    //    partie add patient
    @GetMapping("/add")
    public String addPatientGet(Model model){
        model.addAttribute("titre","ajouter un patient");
        List<VilleEntity> villes = (List<VilleEntity>) villeRepository.findAll();
        model.addAttribute("villes" , villes);
        PatientEntity patient = new PatientEntity();
//        patient.setId(0);
//        patient.setNom("");
//        patient.setPrenom("");
        VilleEntity v = new VilleEntity();
//        v.setId(0);
        patient.setVille(v);
        model.addAttribute("patient", patient);
//        model.addAttribute("patient", new PatientEntity());
        return "patient/add_edit";
    }

    @PostMapping("/add")
    public String addPatientPost(HttpServletRequest request){
        try {
            PatientEntity p = new PatientEntity();
            p.setNom((String) request.getParameter("nom"));
            p.setPrenom((String) request.getParameter("prenom"));
            p.setTelephone((String) request.getParameter("telephone"));
            p.setEmail((String) request.getParameter("email"));
            p.setPhoto((String) request.getParameter("photo"));

            VilleEntity ville = new VilleEntity();
            ville.setId(Integer.parseInt(request.getParameter("ville_depuis_template")));
            p.setVille(ville);
            patientRepository.save(p);
        }catch (Exception e){
            e.printStackTrace();
        }
        // ici redirect vers lien du controller
        return "redirect:/patient/all";
    }

//    partie edit patient
    @GetMapping("/edit/{id}")
    public String editPatientGet(@PathVariable String id, Model model){
        model.addAttribute("titre","modifier le patient");
        try{
            List<VilleEntity> villes = (List<VilleEntity>) villeRepository.findAll();
            model.addAttribute("villes" , villes);
            PatientEntity patient = patientRepository.findById(Integer.parseInt(id)).get();
            model.addAttribute("patient", patient);
        }catch(Exception e){
            e.printStackTrace();
        }

        return "patient/add_edit";
    }
    @PostMapping("/edit/{id}")
    public String editPatientPost(@PathVariable String id, HttpServletRequest request){
        try{
//            List<VilleEntity> villes = (List<VilleEntity>) villeRepository.findAll();

            PatientEntity p = patientRepository.findById(Integer.parseInt(id)).get();
//            PatientEntity p = new PatientEntity();
//            p.setId(Integer.parseInt(id));
            p.setNom((String) request.getParameter("nom"));
            p.setPrenom((String) request.getParameter("prenom"));
            p.setTelephone((String) request.getParameter("telephone"));
            p.setEmail((String) request.getParameter("email"));
            p.setPhoto((String) request.getParameter("photo"));

//            VilleEntity ville = new VilleEntity();
            VilleEntity ville = villeRepository.findById(Integer.parseInt(request.getParameter("ville_depuis_template"))).get();
//            ville.setId(Integer.parseInt(request.getParameter("ville")));
            p.setVille(ville);
            System.out.println("test ");
            patientRepository.save(p);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/patient/all";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id){

        try {
            patientRepository.deleteById(Integer.parseInt(id));

        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/patient/all";
    }

    @RequestMapping("/all")
    public String getAllPatients(Model model){
        model.addAttribute("titre","Liste des patients ");
//        List<VilleEntity> villes = (List<VilleEntity>) villeRepository.findAll();
//        model.addAttribute("villes" , villes);
        List<PatientEntity> patients = (List<PatientEntity>) patientRepository.findAll();
        model.addAttribute("patients" , patients);
        return "patient/list";
    }



    // premiere partie de test
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
}
