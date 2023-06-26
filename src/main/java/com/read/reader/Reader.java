package com.read.reader;

import java.io.*;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.copy;


public class Reader  {

    private static String FBFile;
    public static String  Title, Body,Binary;
    private static File TempDir, TmpFile;


    public static void Read(String Path)
            throws  IOException
    {
        FBFile = Path;
        createTempFolder();
        String Proverka =  readText(TmpFile);

    }
    // Копируем файл во временную папку в формате txt
    private static void copyFile(File FB, File txt)
            throws IOException
    {
        copy(FB.toPath(),txt.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    //Создать временную папку
    private static void createTempFolder() throws  IOException{
        String filePath = new File("").getAbsolutePath();
        TempDir = new File(filePath + "\\TempDir");
        TempDir.mkdir();
        TmpFile = new File(TempDir+"\\temp.txt");
        copyFile(new File(FBFile), TmpFile);
    }

    // Удалить временную папку
    private static void deleteDir(File delDir){
        File[] tempF = delDir.listFiles();
        if(tempF != null)
        {
            for(File f: tempF){
                deleteDir(f);
            }
        }
        delDir.delete();
    }
    private static String readText(File filePath){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

