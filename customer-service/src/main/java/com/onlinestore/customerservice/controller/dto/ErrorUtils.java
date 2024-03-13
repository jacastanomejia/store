package com.onlinestore.customerservice.controller.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ErrorUtils {

    public static String formatMessage(BindingResult result){
        List<Map<String, String>> messages = result.getFieldErrors()
                .stream()
                .map(error-> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(error.getField(), error.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());

        ErrorMessage message = ErrorMessage.builder()
                .code("001")
                .message(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = "";
        try {
            jsonMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info(jsonMessage);
        return jsonMessage;
    }

}
