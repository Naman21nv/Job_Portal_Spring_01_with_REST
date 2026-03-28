package org.example.job_portal.Service;

import org.example.job_portal.model.JobPost;
import org.example.job_portal.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    /**
     * @Autowired -> Dependency Injection (DI)
     * Spring Boot looks inside its "Application Context" (a container of all objects it manages)
     * for an object of type `JobRepo`. Since we annotated JobRepo with @Repository earlier, 
     * Spring created one.
     * 
     * FLOW: Instead of you typing `JobRepo repo = new JobRepo();`, Spring automatically 
     * hands you the ready-to-use `repo` object. This makes your code loosely coupled.
     */
    @Autowired
    private JobRepo repo;

    /**
     * Takes a newly created job from the Controller and passes it to the Repository.
     * (If we needed to check if the job title was valid or if fields were empty, we would do it here).
     * 
     * @param jobpost The new job object to be saved.
     */
    public void addJob(JobPost jobpost) {
        // Business logic could go here (e.g. validating input)
        repo.addJob(jobpost);
    }

    /**
     * Asks the Repository to fetch all available jobs.
     * 
     * @return A list of JobPost objects fetched from our "database".
     */
    public List<JobPost> getJobs() {
        return repo.getAllJobs();
    }

    /**
     * Deletes a job from the database via the Repository.
     *
     * @param postId The ID of the JobPost to delete.
     */
    public void deleteJob(int postId) {
        repo.deleteJob(postId);
    }

    public JobPost getJobById(int postId) {
        // This method is not implemented in the repository, but you could add it there.
        // For now, we can just fetch all jobs and find the one with the matching ID.
        return repo.getAllJobs().stream()
                .filter(job -> job.getPostId() == postId)
                .findFirst()
                .orElse(null); // Returns null if no job is found with the given ID
    }
}