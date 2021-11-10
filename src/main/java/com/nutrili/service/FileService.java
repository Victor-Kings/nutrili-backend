package com.nutrili.service;

import com.nutrili.config.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileService {
    @Autowired
    Properties properties;

    public String updateFile(MultipartFile profilePic){

        String uniqueID = UUID.randomUUID().toString();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName= uniqueID+profilePic.getOriginalFilename();
        File newFile = new File("./user-photos/" + fileName);

        try {
            inputStream = profilePic.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getFilePath()+fileName;
    }
}
