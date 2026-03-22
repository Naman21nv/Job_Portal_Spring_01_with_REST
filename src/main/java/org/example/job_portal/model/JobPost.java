package org.example.job_portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 *
 *@Data-> it will only create getter and setter in default , we get this feature  from lombok
 * @NoArgsConstructor  -> create a no args constructor for the class
 * @AllArgsConstructor -> create constructior of having different all the args
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JobPost {
    private int postId;
    private String postProfile;
    private String postDesc;
    private int reqExperience;
    private List<String> postTechStack;

}
