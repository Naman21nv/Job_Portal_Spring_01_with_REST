package org.example.job_portal.repo;

import org.example.job_portal.model.JobPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIT TESTING THE REPOSITORY LAYER
 *
 * CONCEPT:
 * The Repository layer directly interacts with data storage. 
 * Since our JobRepo currently uses a simple in-memory ArrayList as a "fake database", 
 * we DO NOT need Mockito here. We can just create a real instance of JobRepo 
 * and test if the ArrayList adds, removes, and retrieves data correctly.
 *
 * ALTERNATIVE (For real databases):
 * If we were using Spring Data JPA with a real MySQL or PostgreSQL database, 
 * we would use the `@DataJpaTest` annotation. That tells Spring to spin up a 
 * temporary, in-memory SQL database (like H2) specifically for testing, so we don't 
 * accidentally write test data into our actual production database.
 */
class JobRepoTest {

    // The actual object we are testing
    private JobRepo jobRepo;

    /**
     * @BeforeEach ensures that a brand new JobRepo object is created before EVERY test.
     * This is crucial! If Test A deletes a job, we don't want Test B to fail because 
     * it expects that job to still be there. Every test gets a fresh copy of the initial 5 jobs.
     */
    @BeforeEach
    void setUp() {
        jobRepo = new JobRepo(); // Direct instantiation since there are no @Autowired dependencies
    }

    @Test
    void testGetAllJobs_returnsInitialSize() {
        // ACT
        List<JobPost> result = jobRepo.getAllJobs();
        
        // ASSERT
        // We know our JobRepo hardcodes 5 jobs in its constructor, so we verify that size.
        assertEquals(5, result.size(), "Initial mock database should contain exactly 5 jobs");
    }

    @Test
    void testAddJob_increasesSizeByOne() {
        // ARRANGE: Get the size before adding
        int initialSize = jobRepo.getAllJobs().size();
        JobPost newJob = new JobPost(6, "QA Engineer", "Desc", 2, Arrays.asList("Selenium"));
        
        // ACT
        jobRepo.addJob(newJob);
        
        // ASSERT
        List<JobPost> updatedList = jobRepo.getAllJobs();
        assertEquals(initialSize + 1, updatedList.size(), "List size should increase by 1");
        
        // Verify the exact job we added is at the end of the list
        JobPost lastJobInList = updatedList.get(updatedList.size() - 1);
        assertEquals("QA Engineer", lastJobInList.getPostProfile());
    }

    @Test
    void testDeleteJob_decreasesSizeByOne() {
        // ARRANGE
        int initialSize = jobRepo.getAllJobs().size();
        int jobIdToDelete = 1; // "Software Engineer" from the hardcoded list
        
        // ACT
        jobRepo.deleteJob(jobIdToDelete);
        
        // ASSERT
        List<JobPost> updatedList = jobRepo.getAllJobs();
        assertEquals(initialSize - 1, updatedList.size(), "List size should decrease by 1");
        
        // Verify that searching for the deleted job now returns null
        assertNull(jobRepo.getJob(jobIdToDelete), "Job should no longer exist in the repository");
    }

    @Test
    void testGetJob_found() {
        // ARRANGE
        int jobIdToFind = 2; // "Data Scientist"
        
        // ACT
        JobPost result = jobRepo.getJob(jobIdToFind);
        
        // ASSERT
        assertNotNull(result, "Result should not be null if the job exists");
        assertEquals("Data Scientist", result.getPostProfile());
    }

    @Test
    void testGetJob_notFound() {
        // ARRANGE
        int nonExistentJobId = 999;
        
        // ACT
        JobPost result = jobRepo.getJob(nonExistentJobId);
        
        // ASSERT
        assertNull(result, "Result should be null if the job does not exist");
    }
}