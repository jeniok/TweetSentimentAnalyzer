package com.interview.intuit.twitter.aggregator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class SQLFileReader {

    @Value("${intuit.keyword}")
    private String keyword;

    public String readSqlFile(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        InputStream inputStream = resource.getInputStream();
        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
        return String.format(new String(bdata, StandardCharsets.UTF_8), keyword, keyword, keyword);
    }
}