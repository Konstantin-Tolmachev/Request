package com.practikum.naumen.configs;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;


import java.util.Map;

/*----------- Перенаправление на страницу в случае не существующей страницы -----------*/

@Component
public class WebConfig extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("customs", "ошибка");
        errorAttributes.remove("error");
        return errorAttributes;
    }
}