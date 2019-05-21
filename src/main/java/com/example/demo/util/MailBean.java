package com.example.demo.util;

import lombok.Data;
import java.io.Serializable;

@Data
public class MailBean implements Serializable {
    private static final long serialVersionUID = 9017755323919036747L;
    private String recipient; // 邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容

}