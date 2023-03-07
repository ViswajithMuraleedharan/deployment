package com.example.Doctor.Service;

import com.example.Doctor.dao.DoctorRepository;
import com.example.Doctor.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    public Doctor saveDoctor(Doctor doctor){
        String doctorName=doctor.getDoctorName();
        doctorName="Dr. "+doctorName;
        doctor.setDoctorName(doctorName);
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getDoctor(String doctorId) {
        List<Doctor> doctorList;
        if(null!=doctorId){
            doctorList=new ArrayList<>();
            doctorList.add(doctorRepository.findById(Integer.valueOf(doctorId)).get());
            return doctorList;
        } else {
            doctorList=doctorRepository.findAll();
        }
        return doctorList;
    }

}
