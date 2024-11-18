package com.example.valdecercise.Controller;

import com.example.valdecercise.ApiRespons.ApiResopnse;
import com.example.valdecercise.Model.Project;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@RestController
@RequestMapping("/api/v1/project")
public class trackerController {

    private final ArrayList<Project> projects = new ArrayList<>();

    // Add a new project
    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody @Valid Project project, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        projects.add(project);
        return ResponseEntity.status(200).body(new ApiResopnse("Project added successfully"));
    }

    // Retrieve all projects
    @GetMapping("/get")
    public ArrayList<Project> getProjects() {
        return projects;
    }

    // Update a project by index
    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateProject(@PathVariable int index, @RequestBody @Valid Project project, Errors errors) {
        if (index < 0 || index >= projects.size()) {
            return ResponseEntity.status(404).body(new ApiResopnse("Project not found at index " + index));
        }
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        projects.set(index, project);
        return ResponseEntity.status(200).body(new ApiResopnse("Project updated successfully"));
    }

    // Delete a project by index
    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteProject(@PathVariable int index) {
        if (index < 0 || index >= projects.size()) {
            return ResponseEntity.status(404).body(new ApiResopnse("Project not found at index " + index));
        }
        projects.remove(index);
        return ResponseEntity.status(200).body(new ApiResopnse("Project deleted successfully"));
    }

    // Mark a project as done
    @PutMapping("/mark/{index}")
    public ResponseEntity<?> markAsDone(@PathVariable int index) {
        if (index < 0 || index >= projects.size()) {
            return ResponseEntity.status(404).body(new ApiResopnse("Project not found at index " + index));
        }
        Project project = projects.get(index);
        if (project.getStatus().equalsIgnoreCase("not done")) {
            project.setStatus("done");
            return ResponseEntity.status(200).body(new ApiResopnse("Project marked as done successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResopnse("Project is already marked as done"));
        }
    }

    // Search for a project by title
    @GetMapping("/search/{title}")
    public ResponseEntity<?> search(@PathVariable String title) {
        ArrayList<Project> filteredProjects = projects.stream()
                .filter(project -> project.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filteredProjects.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResopnse("No projects found with title: " + title));
        }
        return ResponseEntity.status(200).body(filteredProjects);
    }

    // Retrieve all projects for a specific company
    @GetMapping("/all/{companyName}")
    public ResponseEntity<?> allProjectsByCompany(@PathVariable String companyName) {
        ArrayList<Project> filteredProjects = projects.stream()
                .filter(project -> project.getCompanyName().equalsIgnoreCase(companyName))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filteredProjects.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResopnse("No projects found for company: " + companyName));
        }
        return ResponseEntity.status(200).body(filteredProjects);
    }
}
