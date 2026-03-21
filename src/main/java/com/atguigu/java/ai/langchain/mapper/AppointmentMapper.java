package com.atguigu.java.ai.langchain.mapper;

import com.atguigu.java.ai.langchain.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
    Appointment getOne(Appointment appointment);
}
