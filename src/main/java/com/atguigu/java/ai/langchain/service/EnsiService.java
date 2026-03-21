package com.atguigu.java.ai.langchain.service;

import com.atguigu.java.ai.langchain.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface EnsiService extends IService<Appointment> {
    public Appointment getOne(Appointment appointment);
}
