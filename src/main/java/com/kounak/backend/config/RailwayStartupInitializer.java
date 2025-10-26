package com.kounak.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Component
@Profile("production")
public class RailwayStartupInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing Railway database asynchronously...");

        // Add delay to ensure database connection is fully established
        Thread.sleep(10000); // Wait 10 seconds for database to be ready

        // Run database initialization asynchronously to not block application startup
        CompletableFuture.runAsync(() -> {
            try {
                // Check if database is already initialized
                boolean isInitialized = checkIfDatabaseInitialized();

                if (!isInitialized) {
                    System.out.println("Database not initialized. Running initialization scripts...");

                    // Execute schema.sql first
                    executeSqlFile("schema.sql");

                    // Execute migration scripts in order
                    executeSqlFile("db/migration/V2__Rename_Current_Price_To_Price.sql");
                    executeSqlFile("db/migration/V3__Fix_Order_Total_Price_Trigger.sql");
                    executeSqlFile("db/migration/V4__Fix_Order_Total_Price_Trigger_Again.sql");
                    executeSqlFile("db/migration/V5__Create_ID_Generator_Table.sql");
                    executeSqlFile("db/migration/V5__Remove_Auto_Increment_From_Orders.sql");
                    executeSqlFile("db/migration/V6__Modify_Order_Details_Table.sql");

                    // Execute data population scripts
                    executeSqlFile("db/scripts/fill_database.sql");

                    System.out.println("Database initialization completed successfully!");
                } else {
                    System.out.println("Database already initialized. Skipping initialization.");
                }

            } catch (Exception e) {
                System.err.println("Error during database initialization: " + e.getMessage());
                e.printStackTrace();
                // Don't throw exception - let the application continue even if initialization fails
            }
        });

        System.out.println("Application startup continues while database initializes in background...");
    }

    private boolean checkIfDatabaseInitialized() {
        try {
            // Check if users table exists and has data
            Integer userCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'users'",
                Integer.class
            );

            if (userCount != null && userCount > 0) {
                // Check if there's actual data in users table
                Integer actualUserCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
                return actualUserCount != null && actualUserCount > 0;
            }
        } catch (Exception e) {
            System.out.println("Database check failed (this is normal for first run): " + e.getMessage());
        }
        return false;
    }

    private void executeSqlFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        if (resource.exists()) {
            String sql = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            String[] statements = sql.split(";");

            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty()) {
                    try {
                        jdbcTemplate.execute(statement);
                    } catch (Exception e) {
                        System.err.println("Error executing statement from " + filePath + ": " + e.getMessage());
                        // Continue with other statements
                    }
                }
            }
            System.out.println("Executed SQL file: " + filePath);
        } else {
            System.out.println("SQL file not found: " + filePath);
        }
    }
}