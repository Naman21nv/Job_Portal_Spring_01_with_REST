package org.example.job_portal.Service;

import org.example.job_portal.model.JobPost;
import org.example.job_portal.repo.JobRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * UNIT TESTING THE SERVICE LAYER
 *
 * CONCEPT:
 * The Service layer contains "Business Logic" (rules of the application).
 * We want to test that the Service handles data correctly and passes it to the Repository.
 * We DO NOT want to connect to a real database, because database calls are slow and require setup.
 * Therefore, we "Mock" (fake) the Repository layer.
 *
 * WHY THIS APPROACH?
 * By isolating the Service from the Repository, we ensure that if a test fails here, 
 * the bug is 100% inside the Service layer, not caused by a database connection error.
 */
class JobServiceTest {

    /**
     * @Mock: Creates a dummy JobRepo. It does not contain the in-memory array list.
     * Any method called on this mock will do nothing and return null by default, 
     * unless we train it using 'when()'.
     */
    @Mock
    private JobRepo jobRepo;

    /**
     * @InjectMocks: Creates a real JobService and injects our dummy jobRepo into it.
     * This is exactly what Spring's @Autowired does in the real application, but here
     * Mockito is doing it for our test environment.
     */
    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        // Initialize the mocks before every test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddJob() {
        // ARRANGE: Create a fake job to add
        JobPost newJob = new JobPost(1, "Test Engineer", "Test Desc", 1, Arrays.asList("Java"));
        
        // ACT: Call the service method
        jobService.addJob(newJob);
        
        // ASSERT & VERIFY: Since addJob returns void, we can't assert a return value.
        // Instead, our "test" is simply verifying that the service correctly handed 
        // the job over to the repository to be saved.
        verify(jobRepo, times(1)).addJob(newJob);
    }

    @Test
    void testGetJobs() {
        // ARRANGE: Prepare fake data that the fake database will return
        List<JobPost> mockJobs = Arrays.asList(
                new JobPost(1, "Job1", "Desc1", 1, Arrays.asList("Java")),
                new JobPost(2, "Job2", "Desc2", 2, Arrays.asList("Python"))
        );
        
        // Train the mock repository
        when(jobRepo.getAllJobs()).thenReturn(mockJobs);

        // ACT
        List<JobPost> result = jobService.getJobs();

        // ASSERT: Check that the service returned exactly what the repository gave it
        assertEquals(2, result.size());
        assertEquals("Job1", result.get(0).getPostProfile());
        
        // VERIFY: Ensure the service actually called the repository to get the data
        verify(jobRepo, times(1)).getAllJobs();
    }

    @Test
    void testDeleteJob() {
        int jobIdToDelete = 1;
        
        // ACT
        jobService.deleteJob(jobIdToDelete);
        
        // VERIFY: The service's only job here is to pass the ID to the repo. Did it do that?
        verify(jobRepo, times(1)).deleteJob(jobIdToDelete);
    }

    @Test
    void testGetJobById() {
        // ARRANGE
        JobPost mockJob = new JobPost(1, "Job1", "Desc1", 1, Arrays.asList("Java"));
        when(jobRepo.getJob(1)).thenReturn(mockJob);
        
        // ACT
        JobPost result = jobService.getJobById(1);
        
        // ASSERT
        assertEquals("Job1", result.getPostProfile());
        verify(jobRepo, times(1)).getJob(1);
    }
}