package com.atguigu.java.ai.langchain.service.implement;

import com.atguigu.java.ai.langchain.entity.Appointment;
import com.atguigu.java.ai.langchain.mapper.AppointmentMapper;
import com.atguigu.java.ai.langchain.service.EnsiService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EnsiServiceImplement extends ServiceImpl<AppointmentMapper, Appointment> implements EnsiService {
    @Override
    public Appointment getOne(Appointment appointment) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());

        return baseMapper.selectOne(queryWrapper);
    }
}
