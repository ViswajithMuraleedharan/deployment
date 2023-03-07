package com.example.Doctor.Service;

import com.example.Doctor.dao.PatientRepository;
import com.example.Doctor.model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;
    public Patient savePatient(Patient patient){

        Patient patient1=patientRepository.save(patient);
        return patient1;
    }

    public JSONArray findAll() {
        List<Patient> allpatients= patientRepository.findAll();
        JSONArray jsonArray=new JSONArray();
        for(Patient patient:allpatients){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("patientId",patient.getPatientId());
            jsonObject.put("patient_name",patient.getPatientName());
            jsonObject.put("age",patient.getAge());
            jsonObject.put("phone_number",patient.getPhoneNumber());
            jsonObject.put("disease_type",patient.getDiseaseType());
            jsonObject.put("gender",patient.getGender());
            jsonObject.put("admit_date",patient.getAdmitDate());
            jsonObject.put("doctor_id",patient.getDoctorId().getDoctorId());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
