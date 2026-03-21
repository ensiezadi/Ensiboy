package com.atguigu.java.ai.langchain.tools;

import com.atguigu.java.ai.langchain.entity.Appointment;
import com.atguigu.java.ai.langchain.service.EnsiService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xiaoZhiTools")
public class XiaoZhiTools {

    @Autowired
    private EnsiService appointmentService;

    /**
     * 注意：Tool 的 name 必须符合正则 ^[a-zA-Z0-9_-]+$
     * 不能包含中文、空格。
     */
    @Tool(name = "book_appointment", value = "根据参数，先执行工具方法queryDepartment查询是否可预约，"
            +"并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约。" +
            "如果用户没有提供具体的医生姓名，请从向量存储中找到一位医生。")
    public String bookAppointment(
            @P("患者姓名") String username,
            @P("身份证号") String idCard,
            @P("科室名称") String department,
            @P("预约日期，格式如：2026-03-21") String date,
            @P("时间段，可选：上午、下午") String time,
            @P("医生姓名，可选") String doctorName
    ) {
        // 1. 构建与你实体类字段一致的对象
        Appointment appointment = new Appointment();
        appointment.setUsername(username);
        appointment.setIdCard(idCard);
        appointment.setDepartment(department);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setDoctorName(doctorName);

        System.out.println(">>> [XiaoZhiTools] 正在写入 MySQL: " + appointment);

        // 2. 检查逻辑：防止同一个人在同一天同一科室重复挂号
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB == null) {
            if (appointmentService.save(appointment)) {
                return "【系统回执】预约成功！请携带身份证准时就诊。";
            } else {
                return "【系统回执】由于系统繁忙，挂号暂时失败。";
            }
        }
        return "【系统回执】检测到您已存在相同的预约记录，请勿重复操作。";
    }

    @Tool(name = "query_availability", value = "查询指定科室在特定日期的号源情况。")
    public String queryDepartment(
            @P("科室名称") String department,
            @P("日期") String date,
            @P("时间段") String time,
            @P(value = "医生名称", required = false) String doctorName
    ) {
        System.out.println(">>> 正在查询 MySQL 排班数据...");
        // 这里可以写真实的数据库查询，目前先返回成功以引导 AI 继续
        return String.format("【查询结果】%s 在 %s %s 有号，您可以放心预约。", department, date, time);
    }

    @Tool(name = "cancel_appointment", value = "根据身份证号和科室，取消已有的预约。")
    public String cancelAppointment(
            @P("身份证号") String idCard,
            @P("预约科室") String department,
            @P("预约日期") String date
    ) {
        Appointment query = new Appointment();
        query.setIdCard(idCard);
        query.setDepartment(department);
        query.setDate(date);

        Appointment dbRecord = appointmentService.getOne(query);
        if (dbRecord != null) {
            appointmentService.removeById(dbRecord.getId());
            return "【系统回执】取消预约成功。";
        }
        return "【系统回执】未找到对应的预约信息。";
    }
}