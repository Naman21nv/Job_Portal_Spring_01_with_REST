package org.example.job_portal.repo;

import org.example.job_portal.model.JobPost;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class JobRepo {

    /**
     * FAKE DATABASE (In-Memory List)
     * We don't have a real SQL database yet, so we use an ArrayList to simulate one.
     * When the application starts, this list is initialized with 5 fake job posts.
     * Note: If you restart the server, any new jobs added by the user will be lost 
     * because this list is re-created every time.
     */
    List<JobPost> jobs = new ArrayList<>(Arrays.asList(
            new JobPost(1, "Software Engineer", "Responsible for developing and maintaining software applications.", 2, Arrays.asList("Java", "Spring Boot", "SQL")),
            new JobPost(2, "Data Scientist", "Analyze and interpret complex data to help companies make informed decisions.", 3, Arrays.asList("Python", "Machine Learning", "Data Visualization")),
            new JobPost(3, "Frontend Developer", "Design and implement user interfaces for web applications.", 2, Arrays.asList("HTML", "CSS", "JavaScript")),
            new JobPost(4, "Backend Developer", "Develop and maintain server-side logic and databases.", 3, Arrays.asList("Node.js", "Express", "MongoDB")),
            new JobPost(5, "DevOps Engineer", "Manage and automate the deployment and operation of software applications.", 4, Arrays.asList("Docker", "Kubernetes", "AWS"))
    ));


    /**
     * Retrieves all jobs from our "database".
     * Called by: JobService.getJobs()
     *
     * @return List of all JobPost objects currently stored in memory.
     */
    public List<JobPost> getAllJobs() {
        return jobs;
    }

    /**
     * Adds a new job to our "database".
     * Called by: JobService.addJob(JobPost)
     *
     * @param job The new JobPost object created from the user's form submission.
     */
    public void addJob(JobPost job) {
        jobs.add(job);
        System.out.println("Job added: " + job); // Prints to console for debugging
    }

    /**
     * Deletes a job from our "database" by its ID.
     * Called by: JobService.deleteJob(int)
     *
     * @param postId The ID of the JobPost to delete.
     */
    public void deleteJob(int postId) {
        jobs.removeIf(job -> job.getPostId() == postId);
        System.out.println("Job deleted with ID: " + postId);
    }

    public JobPost getJob(int postId) {
        for(JobPost job : jobs){
            if(job.getPostId() == postId) {
                return job;
            }
        }
        return null;
    }
}