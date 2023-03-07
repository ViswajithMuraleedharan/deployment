package com.example.Doctor.Controller;

import com.example.Doctor.Service.DoctorService;
import com.example.Doctor.model.Doctor;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class Doctorcontroller {
    @Autowired
    DoctorService doctorService;
    @PostMapping(value = "/doctor")
    public ResponseEntity saveDoctor(@RequestBody String requestdoctor){
        JSONObject json=new JSONObject(requestdoctor);
        List<String> errorList=validateDoctor(json);
        if(errorList.isEmpty()) {
             Doctor doctor=setDoctor(json);
             doctorService.saveDoctor(doctor);
             return new ResponseEntity<>("Doctor saved successfully", HttpStatus.OK);
        }
        else {
            String[] answer= Arrays.copyOf(errorList.toArray(),errorList.size(),String[].class);
                return new ResponseEntity<>("Please add necessary parameters:- "+Arrays.toString(answer), HttpStatus.BAD_REQUEST);
        }
    }

    private List<String> validateDoctor(JSONObject json) {

        List<String> errorList=new ArrayList<>();
        if(!json.has("doctorId")){
            errorList.add("doctorId");
        }
        if(!json.has("doctorName")){
            errorList.add("doctorName");
        }

        if(!json.has("specializedIn")){
            errorList.add("specializedIn");
        }

        return errorList;
    }

    public Doctor setDoctor(JSONObject json){
        Doctor doctor = new Doctor();
        List<String> errorList=validateDoctor(json);
            int doctorId = (int) (json.get("doctorId"));
            doctor.setDoctorId(doctorId);
            String doctorName = (json.getString("doctorName"));
            doctor.setDoctorName(doctorName);
            String specializedIn = json.getString("specializedIn");
            doctor.setSpecializedIn(specializedIn);
        if (json.has("experience")) {
            String experience = json.getString("experience");
            doctor.setSpecializedIn(experience);
        }
        return doctor;
    }

    @GetMapping(value = "/doctor")
    public List<Doctor> getDoctor(@Nullable @RequestParam String doctorId){
        return doctorService.getDoctor(doctorId);
    }

}
