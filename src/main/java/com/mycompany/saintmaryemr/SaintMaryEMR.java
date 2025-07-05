/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.saintmaryemr;

/**
 *
 * @author ADLAN
 */
/**
 * St. Mary's Hospital Lacor - Electronic Medical Records (EMR) System
 * Medical Log File Reader with Comprehensive Exception Handling
 * 
 * This program reads medical log files and displays their content on screen
 * with proper exception handling for FileNotFoundException and IOException
 */

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SaintMaryEMR {
    
    // Constants for the EMR system
    private static final String HOSPITAL_NAME = "St. Mary's Hospital Lacor";
    private static final String LOCATION = "Gulu, Uganda";
    private static final String SYSTEM_NAME = "EMR File Reader System";
    
    public static void main(String[] args) {
        // Display system header
        displaySystemHeader();
        
        // Create sample medical log file for demonstration
        File medicalLogFile = createSampleMedicalLogFile();
        
        // Read and display the medical log file
        readMedicalLogFile(medicalLogFile);
        
        // Demonstrate error handling with non-existent file
        demonstrateFileNotFoundHandling();
        
        // Interactive file reading option
        offerInteractiveFileReading();
    }
    
    /**
     * Main method to read medical log file with proper exception handling
     * Handles FileNotFoundException and IOException as required (10 marks)
     * 
     * @param medicalLogFile The File object containing medical records
     */
    public static void readMedicalLogFile(File medicalLogFile) {
        System.out.println("=".repeat(70));
        System.out.println("📋 READING MEDICAL LOG FILE: " + medicalLogFile.getName());
        System.out.println("=".repeat(70));
        
        // Method 1: Using BufferedReader with try-with-resources
        System.out.println("🔍 Reading file using BufferedReader...\n");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(medicalLogFile))) {
            
            String line;
            int lineNumber = 1;
            
            System.out.println("📄 MEDICAL LOG CONTENT:");
            System.out.println("-".repeat(70));
            
            while ((line = reader.readLine()) != null) {
                // Display line with line numbers for better readability
                System.out.printf("%3d | %s%n", lineNumber, line);
                lineNumber++;
            }
            
            System.out.println("-".repeat(70));
            System.out.printf("✅ Successfully read %d lines from %s%n", 
                            lineNumber - 1, medicalLogFile.getName());
            
        } catch (FileNotFoundException e) {
            System.err.println("❌ FILE NOT FOUND ERROR:");
            System.err.println("   The medical log file could not be found: " + medicalLogFile.getPath());
            System.err.println("   Please ensure the file exists and the path is correct.");
            System.err.println("   Error details: " + e.getMessage());
            
            // Log the error for hospital IT department
            logError("FileNotFoundException", e.getMessage(), medicalLogFile.getPath());
            
        } catch (IOException e) {
            System.err.println("❌ INPUT/OUTPUT ERROR:");
            System.err.println("   An error occurred while reading the medical log file.");
            System.err.println("   This could be due to:");
            System.err.println("   - File permissions issues");
            System.err.println("   - Network connectivity problems (if file is on network drive)");
            System.err.println("   - Disk read errors");
            System.err.println("   - File corruption");
            System.err.println("   Error details: " + e.getMessage());
            
            // Log the error for hospital IT department
            logError("IOException", e.getMessage(), medicalLogFile.getPath());
            
        } catch (SecurityException e) {
            System.err.println("❌ SECURITY ERROR:");
            System.err.println("   Access denied to medical log file: " + medicalLogFile.getPath());
            System.err.println("   Please check file permissions and user access rights.");
            System.err.println("   Error details: " + e.getMessage());
            
            logError("SecurityException", e.getMessage(), medicalLogFile.getPath());
        }
        
        System.out.println(); // Add spacing
    }
    
    /**
     * Alternative method using Scanner for file reading
     * Demonstrates different approach with same exception handling
     * @param medicalLogFile
     */
    public static void readMedicalLogFileWithScanner(File medicalLogFile) {
        System.out.println("📖 Alternative reading method using Scanner...\n");
        
        try (Scanner scanner = new Scanner(medicalLogFile)) {
            
            int recordCount = 0;
            System.out.println("📊 MEDICAL RECORDS SUMMARY:");
            System.out.println("-".repeat(50));
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                recordCount++;
                
                // Process medical record line
                if (line.contains("Patient ID:") || line.contains("PATIENT")) {
                    System.out.println("👤 " + line);
                } else if (line.contains("Date:") || line.contains("TIME")) {
                    System.out.println("📅 " + line);
                } else if (line.contains("Diagnosis:") || line.contains("DIAGNOSIS")) {
                    System.out.println("🩺 " + line);
                } else if (!line.trim().isEmpty()) {
                    System.out.println("📝 " + line);
                }
            }
            
            System.out.println("-".repeat(50));
            System.out.printf("✅ Processed %d records from %s%n", recordCount, medicalLogFile.getName());
            
        } catch (FileNotFoundException e) {
            System.err.println("❌ MEDICAL LOG FILE NOT FOUND:");
            System.err.println("   Cannot locate: " + medicalLogFile.getAbsolutePath());
            System.err.println("   Please verify the file path and try again.");
            
            logError("FileNotFoundException (Scanner)", e.getMessage(), medicalLogFile.getPath());
            
        } 
    }
    
    /**
     * Method using Files.readAllLines for complete file reading
     * Java 8+ approach with exception handling
     * @param medicalLogFile
     */
    public static void readMedicalLogFileWithFiles(File medicalLogFile) {
        System.out.println("📂 Reading complete file using Files.readAllLines...\n");
        
        try {
            // Read all lines at once
            java.util.List<String> lines = Files.readAllLines(medicalLogFile.toPath());
            
            System.out.println("📋 COMPLETE MEDICAL LOG:");
            System.out.println("=".repeat(60));
            
            for (int i = 0; i < lines.size(); i++) {
                System.out.printf("%4d: %s%n", i + 1, lines.get(i));
            }
            
            System.out.println("=".repeat(60));
            System.out.printf("📊 File Statistics:%n");
            System.out.printf("   Total Lines: %d%n", lines.size());
            System.out.printf("   File Size: %d bytes%n", medicalLogFile.length());
            System.out.printf("   Last Modified: %s%n", 
                            new java.util.Date(medicalLogFile.lastModified()));
            
        } catch (NoSuchFileException e) {
            System.err.println("❌ MEDICAL LOG FILE DOES NOT EXIST:");
            System.err.println("   File path: " + medicalLogFile.getPath());
            System.err.println("   Please check if the file was moved or deleted.");
            
            logError("NoSuchFileException", e.getMessage(), medicalLogFile.getPath());
            
        } catch (IOException e) {
            System.err.println("❌ ERROR READING MEDICAL LOG FILE:");
            System.err.println("   Could not read file: " + medicalLogFile.getPath());
            System.err.println("   Reason: " + e.getMessage());
            
            logError("IOException (Files)", e.getMessage(), medicalLogFile.getPath());
        }
    }
    
    /**
     * Creates a sample medical log file for demonstration purposes
     */
    private static File createSampleMedicalLogFile() {
        String fileName = "medical_log_sample.txt";
        File medicalLogFile = new File(fileName);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(medicalLogFile))) {
            writer.println("ST. MARY'S HOSPITAL LACOR - MEDICAL LOG FILE");
            writer.println("===========================================");
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("Location: Gulu, Uganda");
            writer.println();
            writer.println("PATIENT RECORDS:");
            writer.println("================");
            writer.println();
            writer.println("Patient ID: LCR-2024-001");
            writer.println("Name: John Doe");
            writer.println("Age: 35");
            writer.println("Date: 2024-01-15");
            writer.println("Time: 10:30 AM");
            writer.println("Diagnosis: Malaria");
            writer.println("Treatment: Artemether-Lumefantrine");
            writer.println("Status: Treated and Discharged");
            writer.println();
            writer.println("Patient ID: LCR-2024-002");
            writer.println("Name: Jane Smith");
            writer.println("Age: 28");
            writer.println("Date: 2024-01-15");
            writer.println("Time: 11:45 AM");
            writer.println("Diagnosis: Hypertension");
            writer.println("Treatment: Amlodipine 5mg");
            writer.println("Status: Ongoing Treatment");
            writer.println();
            writer.println("Patient ID: LCR-2024-003");
            writer.println("Name: Peter Okello");
            writer.println("Age: 42");
            writer.println("Date: 2024-01-15");
            writer.println("Time: 02:15 PM");
            writer.println("Diagnosis: Diabetes Type 2");
            writer.println("Treatment: Metformin 500mg");
            writer.println("Status: Follow-up Required");
            writer.println();
            writer.println("END OF MEDICAL LOG");
            writer.println("===================");
            
            System.out.println(" Sample medical log file created: " + fileName);
            
        } catch (IOException e) {
            System.err.println(" Could not create sample medical log file: " + e.getMessage());
        }
        
        return medicalLogFile;
    }
    
    /**
     * Demonstrates FileNotFoundException handling with non-existent file
     */
    private static void demonstrateFileNotFoundHandling() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEMONSTRATING ERROR HANDLING WITH NON-EXISTENT FILE");
        System.out.println("=".repeat(70));
        
        File nonExistentFile = new File("non_existent_medical_log.txt");
        readMedicalLogFile(nonExistentFile);
    }
    
    /**
     * Offers interactive file reading option
     */
    private static void offerInteractiveFileReading() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println(" INTERACTIVE FILE READING");
        System.out.println("=".repeat(70));
        
        System.out.print("Enter medical log file path (or press Enter to skip): ");
        String filePath = scanner.nextLine().trim();
        
        if (!filePath.isEmpty()) {
            File userFile = new File(filePath);
            readMedicalLogFile(userFile);
            
            // Demonstrate different reading methods
            if (userFile.exists()) {
                readMedicalLogFileWithScanner(userFile);
                readMedicalLogFileWithFiles(userFile);
            }
        }
    }
    
    /**
     * Logs errors to system for IT department tracking
     */
    private static void logError(String errorType, String message, String filePath) {
        try {
            String logFileName = "emr_error_log.txt";
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            try (PrintWriter logger = new PrintWriter(new FileWriter(logFileName, true))) {
                logger.println(timestamp + " | " + errorType + " | " + filePath + " | " + message);
            }
            
            System.out.println(" Error logged to: " + logFileName);
            
        } catch (IOException e) {
            System.err.println("️  Could not log error: " + e.getMessage());
        }
    }
    
    /**
     * Displays system header information
     */
    private static void displaySystemHeader() {
        System.out.println("=".repeat(80));
        System.out.println("    " + HOSPITAL_NAME.toUpperCase());
        System.out.println("    " + LOCATION);
        System.out.println("    " + SYSTEM_NAME);
        System.out.println("=".repeat(80));
        System.out.println("🏥 Electronic Medical Records (EMR) System");
        System.out.println("📅 Session Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("🌐 Website: https://www.lacorhospital.org/");
        System.out.println("=".repeat(80));
        System.out.println();
    }
}