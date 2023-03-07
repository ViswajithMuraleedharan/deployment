package com.example.Doctor.Controller;


import com.example.Doctor.Service.PatientService;
import com.example.Doctor.dao.DoctorRepository;
import com.example.Doctor.dao.PatientRepository;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.model.Patient;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PatientController {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientService patientService;
    @Autowired
    PatientRepository patientRepository;
    @PostMapping(value = "/patient")
    public ResponseEntity<String> savePatient(@RequestBody String patientresponse){
        JSONObject json=new JSONObject(patientresponse);
        Patient patient=setPatient(json);

        Patient patient1=patientService.savePatient(patient);
        return new ResponseEntity<>("Patient saved successfully",HttpStatus.OK);

    }

    private Patient setPatient(JSONObject json) {
        Patient patient=new Patient();
        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));
        Timestamp currTime=new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);
        String doctorId=json.getString("doctorId");
        Doctor doctor=doctorRepository.findById(Integer.valueOf(doctorId)).get();
        patient.setDoctorId(doctor);
        return patient;
    }
@GetMapping("/patient")
    public List<Patient> getPatient(@Nullable @RequestParam String doctorId, @Nullable @RequestParam String patientId){
        List<Patient> patientList;
       if(patientId==null && doctorId==null){
           return patientRepository.findAll();
       }
       else if(doctorId==null){
           patientList=new ArrayList<>();
           List<Patient> allPatient=patientRepository.findAll();
           for(Patient patient: allPatient){
               if(patient.getPatientId()==Integer.valueOf(patientId)){
                   patientList.add(patient);
               }
           }
       }
       else{
           patientList=new ArrayList<>();
           List<Patient> allPatient=patientRepository.findAll();
           for(Patient patient: allPatient){
               if(patient.getDoctorId().equals(doctorId)){
                   patientList.add(patient);
               }
           }
       }
       return patientList;
    }


}
