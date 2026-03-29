package org.example.job_portal;

import org.example.job_portal.Service.JobService;
import org.example.job_portal.model.JobPost;
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
 * UNIT TESTING THE CONTROLLER LAYER
 *
 * CONCEPT:
 * In a unit test, we want to test ONLY the logic inside this specific class (JobRestController).
 * We DO NOT want to test the layers below it (JobService or JobRepo) or start the entire web server.
 * If the Service layer has a bug, this Controller test should still pass, because we "Mock" (fake) the Service.
 *
 * ALTERNATIVE:
 * Instead of pure Mockito, we could use Spring's `@WebMvcTest(JobRestController.class)`.
 * That approach starts a miniature Spring web server to test actual HTTP requests/responses (JSON, Status Codes).
 * We use Mockito here because it is much faster and focuses purely on Java method execution.
 */
class JobRestControllerTest {

    /**
     * @Mock: Creates a "fake" version of JobService.
     * It looks like a JobService, but it doesn't actually run any of the real code inside JobService.java.
     * We have to tell this mock exactly how to behave using `when(...).thenReturn(...)`.
     */
    @Mock
    private JobService jobService;

    /**
     * @InjectMocks: Creates a REAL instance of JobRestController, but automatically
     * takes the "fake" @Mock jobService from above and injects it into the controller.
     */
    @InjectMocks
    private JobRestController jobRestController;

    /**
     * @BeforeEach: A JUnit annotation. Any method with this annotation runs BEFORE EVERY SINGLE @Test.
     * It ensures we have a fresh, clean slate for each test so they don't interfere with each other.
     */
    @BeforeEach
    void setUp() {
        // Initializes all fields annotated with @Mock and @InjectMocks in this test class.
        MockitoAnnotations.openMocks(this);
    }

    /**
     * @Test: Tells JUnit that this is a test method to be executed.
     */
    @Test
    void testGetAllJobs() {
        // 1. ARRANGE (Set up the test data and mock behavior)
        List<JobPost> mockJobs = Arrays.asList(
                new JobPost(1, "Java Dev", "Desc", 3, Arrays.asList("Java", "Spring"))
        );
        // We tell our fake service: "When someone calls getJobs(), return this mockJobs list."
        when(jobService.getJobs()).thenReturn(mockJobs);

        // 2. ACT (Call the actual method we want to test)
        List<JobPost> result = jobRestController.getAllJobs();

        // 3. ASSERT (Verify the results are what we expect)
        // Check if the list size is exactly 1
        assertEquals(1, result.size());
        // Check if the first item's profile matches
        assertEquals("Java Dev", result.get(0).getPostProfile());
        
        // VERIFY: Check if the fake service's getJobs() method was called exactly 1 time.
        // This ensures our controller isn't just returning hardcoded data, but actually asking the service.
        verify(jobService, times(1)).getJobs();
    }

    @Test
    void testDeleteJob() {
        // 1. ARRANGE
        int jobId = 1;
        String expectedMessage = "Deleted successfully";
        
        // Note: We don't need 'when().thenReturn()' here because deleteJob in the service returns void (nothing).
        // The mock will simply do nothing when called, which is perfect.

        // 2. ACT
        String result = jobRestController.deleteJob(jobId);

        // 3. ASSERT
        assertEquals(expectedMessage, result);
        
        // Ensure the controller actually passed the correct ID down to the service layer.
        verify(jobService, times(1)).deleteJob(jobId);
    }

    @Test
    void testGetJob() {
        // 1. ARRANGE
        int jobId = 1;
        JobPost mockJob = new JobPost(1, "React Dev", "Desc", 2, Arrays.asList("React", "JS"));
        
        // Train the mock to return our specific job when asked for ID 1.
        when(jobService.getJobById(jobId)).thenReturn(mockJob);

        // 2. ACT
        JobPost result = jobRestController.getJob(jobId);

        // 3. ASSERT
        assertEquals(mockJob.getPostProfile(), result.getPostProfile());
        verify(jobService, times(1)).getJobById(jobId);
    }
}