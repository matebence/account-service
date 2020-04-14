package com.blesk.accountservice.Component.Mailer;

import java.util.Map;

public interface HtmlMailer {

    String generateMailHtml(String htmlfile, Map<String, Object> variables);
}