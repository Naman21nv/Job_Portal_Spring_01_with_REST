package org.example.job_portal.model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JobController {

    /**
     * RequestMapping() is by default uses GET method
     * @return
     */


    @RequestMapping({"/","home"})
    public String home(){
        return "home";
    }

    @GetMapping("/addjob")
    public String addJob(){
        return "addjob";
    }

    @RequestMapping("/viewalljobs")
    public String viewAllJobs(){
        return "viewalljobs";
    }

    @PostMapping("handleForm" )
    public String handleForm(JobPost jobpost, Model model){
        model.addAttribute("jobPost", jobpost);
        return "sucess";
    }

}
