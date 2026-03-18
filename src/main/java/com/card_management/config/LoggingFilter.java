package com.card_management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper((HttpServletRequest) request, 1024 * 1024);
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) response);

        long startTime = System.currentTimeMillis();

        chain.doFilter(requestWrapper, responseWrapper);

        String requestBody = indentBody(new String(
                requestWrapper.getContentAsByteArray(),
                StandardCharsets.UTF_8
        ));
        String responseBody = indentBody(new String(
                responseWrapper.getContentAsByteArray(),
                StandardCharsets.UTF_8
        ));

        long duration = System.currentTimeMillis() - startTime;
        log.info("""
                Request: {} {}
                Request Body: {}
                Response Status: {}
                Response Body: {}
                Time Taken: {} ms
                """,
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                requestBody,
                responseWrapper.getStatus(),
                responseBody,
                duration
        );
        responseWrapper.copyBodyToResponse();
    }

    private String indentBody(String body) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String prettyBody = body;
        try {
            Object json = mapper.readValue(body, Object.class);
            prettyBody = mapper.writeValueAsString(json);
        } catch (Exception ignored) {
            return body;
        }
        return prettyBody;
    }

}