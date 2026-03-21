package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.entity.Appointment;
import com.atguigu.java.ai.langchain.mapper.AppointmentMapper;
import com.atguigu.java.ai.langchain.service.EnsiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testAppointment {

    @Autowired
    private EnsiService ensiService;
    @Test
    void testOne(){
        Appointment appointment = new Appointment();
        appointment.setDate("2030-12-1");
        appointment.setUsername("Ensi");
        appointment.setIdCard("23423423");
        appointment.setTime("pm");
        appointment.setDoctorName("zhoujie");
        appointment.setDepartment("内科");
        Appointment appointmentdb = ensiService.getOne(appointment);
        System.out.println(appointmentdb);
    }

    @Test
    void makeOne(){
        Appointment appointment = new Appointment();
        appointment.setDate("2030-12-1");
        appointment.setUsername("Ensi");
        appointment.setIdCard("23423423");
        appointment.setTime("pm");
        appointment.setDoctorName("zhoujie");
        appointment.setDepartment("内科");
        ensiService.save(appointment);
    }
}
