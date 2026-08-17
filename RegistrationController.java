package com.college.eventapp.controller;

import com.college.eventapp.model.Registration;
import com.college.eventapp.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    // Student Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Registration registration) {
        if (!registration.getEmail().toLowerCase().endsWith("@francisxavier.ac.in")) {
            return ResponseEntity.badRequest().body("Only @francisxavier.ac.in email addresses are allowed!");
        }
        Registration saved = registrationRepository.save(registration);
        return ResponseEntity.ok(saved);
    }

    // Get Registrations by Event Endpoint
    @GetMapping("/register/event/{eventId}")
    public List<Registration> getRegistrationsForEvent(@PathVariable Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    // QR Code Scan Verification Endpoint
    @PostMapping("/scan")
    public ResponseEntity<?> verifyAttendance(@RequestParam Long regId) {
        Optional<Registration> regOpt = registrationRepository.findById(regId);
        if (regOpt.isPresent()) {
            Registration reg = regOpt.get();
            if (reg.isAttended()) {
                return ResponseEntity.badRequest().body("⚠️ ALREADY MARKED! Student already scanned.");
            }
            reg.setAttended(true);
            registrationRepository.save(reg);
            return ResponseEntity.ok("✅ VALID TICKET! Entry Allowed for: " + reg.getStudentName() + " (" + reg.getRollNo() + ")");
        }
        return ResponseEntity.badRequest().body("❌ INVALID TICKET! Record not found.");
    }
}