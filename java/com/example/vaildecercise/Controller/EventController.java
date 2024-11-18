package com.example.vaildecercise.Controller;

import com.example.vaildecercise.ApiRespons.ApiResopnse;
import com.example.vaildecercise.Model.Event;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/lab5/vi/event")
public class EventController {

    private final ArrayList<Event> eventsList = new ArrayList<>();

    // Retrieve all events
    @GetMapping("/get")
    public ArrayList<Event> getEventsList() {
        return eventsList;
    }

    // Add a new event
    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody @Valid Event event, Errors errors) {
        if (errors.hasErrors()) {
            // Retrieve the first validation error message
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        eventsList.add(event);
        return ResponseEntity.status(200).body(new ApiResopnse("Event added successfully"));
    }

    // Update an event by index
    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateEvent(@PathVariable int index, @RequestBody @Valid Event event, Errors errors) {
        if (index < 0 || index >= eventsList.size()) {
            return ResponseEntity.status(404).body("Event not found at index " + index);
        }
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        eventsList.set(index, event);
        return ResponseEntity.status(200).body(new ApiResopnse("Event updated successfully"));
    }

    // Delete an event by index
    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteEvent(@PathVariable int index) {
        if (index < 0 || index >= eventsList.size()) {
            return ResponseEntity.status(404).body("Event not found at index " + index);
        }
        eventsList.remove(index);
        return ResponseEntity.status(200).body(new ApiResopnse("Event deleted successfully"));
    }

    // Change capacity of an event by ID
    @PutMapping("/change/{ID}/{Cap}")
    public ResponseEntity<?> changeCap(@PathVariable String ID, @PathVariable int Cap) {
        for (Event event : eventsList) {
            if (event.getID().equals(ID)) {
                if (Cap <= 25) {
                    return ResponseEntity.status(400).body("Capacity must be greater than 25.");
                }
                event.setCapacity(Cap);
                return ResponseEntity.status(200).body(new ApiResopnse("Event capacity updated successfully"));
            }
        }
        return ResponseEntity.status(404).body("Event with ID " + ID + " not found");
    }

    // Search for an event by ID
    @GetMapping("/search/{ID}")
    public ResponseEntity<?> search(@PathVariable String ID) {
        for (Event event : eventsList) {
            if (event.getID().equalsIgnoreCase(ID)) {
                return ResponseEntity.status(200).body(event);
            }
        }
        return ResponseEntity.status(404).body("Event with ID " + ID + " not found");
    }
}
