package org.example.job_portal;

import org.example.job_portal.Service.JobService;
import org.example.job_portal.model.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * JobRestController is a Spring MVC controller that handles RESTful requests related to job posts.
 * It exposes endpoints to interact with the job portal's data.
 */

//wt we return it will be treated as a body
@RestController
// allow frontend requests from the origin where your frontend runs
@CrossOrigin(origins = {"http://localhost:3000"})
public class JobRestController {

    /**
     * The JobService is automatically injected by Spring's dependency injection.
     * This service contains the business logic for handling job-related operations.
     */
    // prefer constructor injection over field injection
    private final JobService services;

    @Autowired
    public JobRestController(JobService services) {
        this.services = services;
    }


    /**
     * This method handles GET requests to the "/posts" endpoint.
     * It retrieves a list of all job posts from the JobService.
     *
     * @return A list of JobPost objects.
     */
    @GetMapping("posts")
    public List<JobPost> getAllJobs(){
        // This is a departure from traditional Spring MVC where you might return a view name (like a JSP page).
        // In a REST API, you return the data itself, which will be consumed by a client (e.g., a React or Angular application).
        return services.getJobs();
    }

    /**
     * This method handles DELETE requests to the "/posts/{postId}" endpoint.
     * It deletes a specific job post by its ID.
     *
     * @param postId The ID of the JobPost to delete.
     * @return A success message.
     */
    @DeleteMapping("posts/{postId}")
    public String deleteJob(@PathVariable("postId") int postId) {
        services.deleteJob(postId);
        return "Deleted successfully";
    }


    public JobPost getJob(int postId){
        return services.getJobs();
    }

}