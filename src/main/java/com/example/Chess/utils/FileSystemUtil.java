package com.example.Chess.utils;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemUtil {
    public final String storageServicePath = ".\\src\\main\\resources\\games";

    public void createFile(String fileId, String content){
        String filePath = storageServicePath + "\\" + fileId;
        Path fileFullPath = Paths.get(filePath);
        try{
            if (!Files.exists(fileFullPath)) {
                Files.createFile(fileFullPath);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not create the file " + fileFullPath);
        }
        writeOnFile(fileFullPath, content);
    }

    public void writeOnFile(Path fileFullPath, String content){
        try{
            Files.write(fileFullPath, content.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not write on the file " + fileFullPath);
        }
    }

    public File[] getSubFiles(String folderId){
        String folderPath = storageServicePath + "\\" + folderId;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            return new File[]{};
        }
        return folder.listFiles();
    }

    public void deleteFile(String fileId){
        String filePath = storageServicePath + "\\" + fileId;
        Path fileFullPath = Paths.get(filePath);
        if(!Files.exists(fileFullPath)){
            throw new IllegalArgumentException("No such file " + fileFullPath);
        }
        try{
            Files.delete(fileFullPath);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not delete the file " + fileFullPath);
        }
    }

    public void deleteFolder(String folderId){
        String folderPath = storageServicePath + "\\" + folderId;
        File folder = new File(folderPath);

        if(folder.exists() && folder.isDirectory()){
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFolder(file.getAbsolutePath());
                    }
                    else if (!file.delete()) {
                        throw new IllegalStateException("Something Went Wrong While Deleting The File " + file.getAbsolutePath());
                    }
                }
            }

            if (!folder.delete()) {
                throw new IllegalStateException("Something Went Wrong While Deleting The Folder " + folder.getAbsolutePath());
            }
        }
    }

    public String readFileContents(String fileId) {
        String filePath = storageServicePath + "\\" + fileId;

        Path path = Path.of(filePath);
        try {
            return Files.readString(path);
        } catch (Exception e){
            throw new IllegalArgumentException("Could not read the file " + path);
        }
    }

    public void writeObjectOnFile(Object object, String fileId) {
        String filePath = storageServicePath + "\\" + fileId;
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // Create directories if they don't exist
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(object);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not create the file " + filePath, e);
        }
    }

    public Object readObjectFromFile(String fileId) {
        String filePath = storageServicePath + "\\" + fileId;
        if (!Files.exists(Paths.get(filePath))){
            writeObjectOnFile(null, fileId);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error deserializing the Object from path " + filePath);
        }
    }

    public boolean fileExists(String fileId) {
        String filePath = storageServicePath + "\\" + fileId;
        return Files.exists(Paths.get(filePath));
    }

    public void createFolderIfNotExists(String folderId){
        String path = storageServicePath + "\\" + folderId;
        File file = new File(path);
        if (!file.exists()){
            if (!file.mkdir()){
                throw new IllegalStateException("Failed to create folder " + path);
            } else {
                System.out.println("Folder " + path + " created!");
            }
        }
    }
}
