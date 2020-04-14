package com.blesk.authorizationserver.Service.Emails;

import com.blesk.authorizationserver.Model.Accounts;

import java.util.Map;

public interface EmailsService {

    void sendMessage(String subject, String text, Accounts accounts);

    void sendHtmlMesseage(String subject, String htmlfile, Map<String, Object> variables, Accounts accounts);
}