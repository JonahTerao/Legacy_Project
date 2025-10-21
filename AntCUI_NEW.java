import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class AntCUI extends UserInterface {
    private boolean darkMode = false;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BG_BLACK = "\u001B[40m";
    private static final String ANSI_BG_WHITE = "\u001B[47m";
    
    @Override
    public void showIntroduction() {
        clearScreen();
        showNotification("Welcome to Enhanced Decision Support System", "INFO");
        System.out.println(getThemeColor("TITLE") + INTRODUCTION + ANSI_RESET);
        showStatusIndicator("Application initialized successfully");
        System.out.println(getThemeColor("PROMPT") + "\nType 'help' for commands or 'theme' to toggle dark mode" + ANSI_RESET);
    }

    @Override
    public List<Alternative> getAlternatives() {
        List<Alternative> alternativeList = new ArrayList<Alternative>();
        Scanner sc = new Scanner(System.in);
        
        showStatusIndicator("Alternative input started");
        System.out.println(getThemeColor("HEADER") + "\n=== ALTERNATIVES INPUT ===" + ANSI_RESET);
        System.out.println(getThemeColor("PROMPT") + FIRST_ALT_PROMPT + ": " + ANSI_RESET);
        
        String alternative = sc.nextLine().trim();
        
        // Handle theme toggle command
        if ("theme".equalsIgnoreCase(alternative)) {
            toggleDarkMode();
            showNotification("Theme toggled to " + (darkMode ? "Dark Mode" : "Light Mode"), "SUCCESS");
            System.out.println(getThemeColor("PROMPT") + FIRST_ALT_PROMPT + ": " + ANSI_RESET);
            alternative = sc.nextLine().trim();
        }
        
        // Handle help command
        if ("help".equalsIgnoreCase(alternative)) {
            showHelp();
            System.out.println(getThemeColor("PROMPT") + FIRST_ALT_PROMPT + ": " + ANSI_RESET);
            alternative = sc.nextLine().trim();
        }
        
        int altCount = 0;
        while (alternative != null && !("".equals(alternative))) {
            alternativeList.add(new Alternative(alternative));
            altCount++;
            showNotification("Alternative '" + alternative + "' added successfully", "SUCCESS");
            
            System.out.println(getThemeColor("PROMPT") + ADDITIONAL_ALT_PROMT + " (or press Enter to finish): " + ANSI_RESET);
            alternative = sc.nextLine().trim();
            
            // Handle theme toggle during input
            if ("theme".equalsIgnoreCase(alternative)) {
                toggleDarkMode();
                showNotification("Theme toggled to " + (darkMode ? "Dark Mode" : "Light Mode"), "SUCCESS");
                System.out.println(getThemeColor("PROMPT") + ADDITIONAL_ALT_PROMT + " (or press Enter to finish): " + ANSI_RESET);
                alternative = sc.nextLine().trim();
            }
        }
        
        showStatusIndicator(altCount + " alternatives added successfully");
        return alternativeList;
    }

    @Override
    public List<Factor> getFactors() {
        List<Factor> factorList = new ArrayList<Factor>();
        Scanner sc = new Scanner(System.in);
        
        showStatusIndicator("Factor input started");
        System.out.println(getThemeColor("HEADER") + "\n=== FACTORS INPUT ===" + ANSI_RESET);
        System.out.println(getThemeColor("PROMPT") + FIRST_FACTOR_PROMPT + ": " + ANSI_RESET);
        
        String factor = sc.nextLine().trim();
        
        // Handle theme toggle command
        if ("theme".equalsIgnoreCase(factor)) {
            toggleDarkMode();
            showNotification("Theme toggled to " + (darkMode ? "Dark Mode" : "Light Mode"), "SUCCESS");
            System.out.println(getThemeColor("PROMPT") + FIRST_FACTOR_PROMPT + ": " + ANSI_RESET);
            factor = sc.nextLine().trim();
        }
        
        int factorCount = 0;
        while (factor != null && !("".equals(factor))) {
            factorList.add(new Factor(factor));
            factorCount++;
            showNotification("Factor '" + factor + "' added successfully", "SUCCESS");
            
            System.out.println(getThemeColor("PROMPT") + ADDITIONAL_FACTOR_PROMPT + " (or press Enter to finish): " + ANSI_RESET);
            factor = sc.nextLine().trim();
            
            // Handle theme toggle during input
            if ("theme".equalsIgnoreCase(factor)) {
                toggleDarkMode();
                showNotification("Theme toggled to " + (darkMode ? "Dark Mode" : "Light Mode"), "SUCCESS");
                System.out.println(getThemeColor("PROMPT") + ADDITIONAL_FACTOR_PROMT + " (or press Enter to finish): " + ANSI_RESET);
                factor = sc.nextLine().trim();
            }
        }
        
        showStatusIndicator(factorCount + " factors added successfully");
        return factorList;
    }

    @Override
    public void getFactorRankings(final List<Factor> factors,
                                  final int standard) {
        Scanner sc = new Scanner(System.in);
        int firstAttribute = 0;
        factors.get(firstAttribute).setRank(standard);
        
        showStatusIndicator("Factor ranking process started");
        System.out.println(getThemeColor("HEADER") + "\n=== FACTOR RANKING ===" + ANSI_RESET);
        System.out.println(getThemeColor("INFO") + "\nAssume that "
            + factors.get(firstAttribute).getName()
            + " has an importance of " + standard + ",\n"
            + "    and that higher values are more important.\n" + ANSI_RESET);
            
        for (int i = firstAttribute + 1; i < factors.size(); i++) {
             System.out.println(getThemeColor("PROMPT") + "        How important is "
                              + factors.get(i).getName() + "? " + ANSI_RESET);
             String importance = sc.nextLine().trim();
             
             // Handle theme toggle during ranking
             if ("theme".equalsIgnoreCase(importance)) {
                 toggleDarkMode();
                 showNotification("Theme toggled to " + (darkMode ? "Dark Mode" : "Light Mode"), "SUCCESS");
                 System.out.println(getThemeColor("PROMPT") + "        How important is "
                                  + factors.get(i).getName() + "? " + ANSI_RESET);
                 importance = sc.nextLine().trim();
             }
             
             if (importance == null || "".equals(importance)) {
                 factors.get(i).setRank(standard);
                 showNotification("Using default importance " + standard + " for " + factors.get(i).getName(), "INFO");
             } else {
                 try {
                     factors.get(i).setRank(Integer.valueOf(importance));
                     showNotification("Importance " + importance + " set for " + factors.get(i).getName(), "SUCCESS");
                 } catch (NumberFormatException e) {
                     showNotification("Invalid number format. Using default importance " + standard, "WARNING");
                     factors.get(i).setRank(standard);
                 }
             }
        }
        
        showStatusIndicator("All factors ranked successfully");
    }

    @Override
    public double[][] getCrossRankings(final List<Alternative> alternatives,
                                       final List<Factor> factors,
                                       final int standard) {
        Scanner sc = new Scanner(System.in);
        double[][] crossRankings =
                    new double[alternatives.size()][factors.size()];
        
        showStatusIndicator("Cross-ranking process started");
        System.out.println(getThemeColor("HEADER") + "\n=== CROSS-RANKING ===" + ANSI_RESET);
        
        for (int i = 0; i < factors.size(); i++) {
            int firstAlternative = 0;
            crossRankings[firstAlternative][i] = standard;
            
            System.out.println(getThemeColor("SECTION") + "\nConsidering " + factors.get(i).getName()
                + " only..." + ANSI_RESET);
            System.out.println(getThemeColor("INFO") + "    if "
                    + alternatives.get(firstAlternative).getDescriptor()
                    + " has a value of " + standard + "... " + ANSI_RESET);
                    
            for (int j = firstAlternative + 1; j < alternatives.size(); j++) {
                System.out.println(getThemeColor("PROMPT") + "        ...what value would you associate"
                    + " with " + alternatives.get(j).getDescriptor() + ": " + ANSI_RESET);
                String rank = sc.nextLine().trim();
                
                // Handle theme toggle during cross-ranking
                if ("theme".equalsIgnoreCase(rank)) {
                    toggleDarkMode();
                    showNotification("Theme toggled to " + (darkMode ? "Dark Mode" : "Light Mode"), "SUCCESS");
                    System.out.println(getThemeColor("PROMPT") + "        ...what value would you associate"
                        + " with " + alternatives.get(j).getDescriptor() + ": " + ANSI_RESET);
                    rank = sc.nextLine().trim();
                }
                
                if (rank == null || "".equals(rank)) {
                    crossRankings[j][i] = standard;
                    showNotification("Using default value " + standard + " for " + alternatives.get(j).getDescriptor(), "INFO");
                } else {
                    try {
                        crossRankings[j][i] = Integer.valueOf(rank);
                        showNotification("Value " + rank + " set for " + alternatives.get(j).getDescriptor(), "SUCCESS");
                    } catch (NumberFormatException e) {
                        showNotification("Invalid number format. Using default value " + standard, "WARNING");
                        crossRankings[j][i] = standard;
                    }
                }
            }
            
            showStatusIndicator("Completed ranking for factor: " + factors.get(i).getName());
        }
        
        showStatusIndicator("Cross-ranking completed for all factors");
        return crossRankings;
    }

    @Override
    public void showResults(final List<Alternative> alternatives) {
        clearScreen();
        Alternative preferredAlternative = alternatives.get(0);
        
        showNotification("Analysis complete! Displaying results...", "SUCCESS");
        System.out.println(getThemeColor("HEADER") + "\n================\nPREFERRED CHOICE" + ANSI_RESET);
        System.out.println(getThemeColor("SUCCESS") + preferredAlternative.getDescriptor() + ANSI_RESET);
        System.out.println(getThemeColor("SECTION") + "-----" + ANSI_RESET);
        
        for (Alternative a : alternatives) {
            if (a.equals(preferredAlternative)) {
                System.out.println(getThemeColor("SUCCESS") + "★ " + a + " ★" + ANSI_RESET);
            } else {
                System.out.println(getThemeColor("TEXT") + "  " + a + ANSI_RESET);
            }
        }
        
        showStatusIndicator("Results displayed successfully");
        System.out.println(getThemeColor("PROMPT") + "\nPress Enter to exit..." + ANSI_RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore exceptions on exit
        }
    }

    /**
     * Shows a toast-style notification with different colors based on type
     * @param message The notification message
     * @param type The type of notification (SUCCESS, WARNING, ERROR, INFO)
     */
    private void showNotification(String message, String type) {
        String color;
        String symbol;
        
        switch (type.toUpperCase()) {
            case "SUCCESS":
                color = ANSI_GREEN;
                symbol = "✓";
                break;
            case "WARNING":
                color = ANSI_YELLOW;
                symbol = "⚠";
                break;
            case "ERROR":
                color = ANSI_RED;
                symbol = "✗";
                break;
            case "INFO":
            default:
                color = ANSI_CYAN;
                symbol = "ℹ";
                break;
        }
        
        System.out.println(color + "[" + symbol + "] " + message + ANSI_RESET);
    }

    /**
     * Shows a status indicator with themed formatting
     * @param status The status message to display
     */
    private void showStatusIndicator(String status) {
        System.out.println(getThemeColor("STATUS") + "↳ " + status + ANSI_RESET);
    }

    /**
     * Toggles between dark and light mode
     */
    private void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            System.out.print(ANSI_BG_BLACK + ANSI_WHITE);
        } else {
            System.out.print(ANSI_BG_WHITE + ANSI_BLACK);
        }
    }

    /**
     * Gets the appropriate color code based on current theme and element type
     * @param elementType The type of UI element (TITLE, HEADER, PROMPT, etc.)
     * @return ANSI color code string
     */
    private String getThemeColor(String elementType) {
        if (darkMode) {
            switch (elementType) {
                case "TITLE":
                    return ANSI_CYAN;
                case "HEADER":
                    return ANSI_PURPLE;
                case "SECTION":
                    return ANSI_BLUE;
                case "PROMPT":
                    return ANSI_YELLOW;
                case "SUCCESS":
                    return ANSI_GREEN;
                case "INFO":
                    return ANSI_CYAN;
                case "TEXT":
                    return ANSI_WHITE;
                case "STATUS":
                    return ANSI_BLUE;
                default:
                    return ANSI_WHITE;
            }
        } else {
            switch (elementType) {
                case "TITLE":
                    return ANSI_BLUE;
                case "HEADER":
                    return ANSI_PURPLE;
                case "SECTION":
                    return ANSI_CYAN;
                case "PROMPT":
                    return ANSI_BLACK;
                case "SUCCESS":
                    return ANSI_GREEN;
                case "INFO":
                    return ANSI_BLUE;
                case "TEXT":
                    return ANSI_BLACK;
                case "STATUS":
                    return ANSI_CYAN;
                default:
                    return ANSI_BLACK;
            }
        }
    }

    /**
     * Clears the console screen
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Shows help information
     */
    private void showHelp() {
        System.out.println(getThemeColor("HEADER") + "\n=== HELP ===" + ANSI_RESET);
        System.out.println(getThemeColor("INFO") + "Available commands:" + ANSI_RESET);
        System.out.println(getThemeColor("TEXT") + "  • theme - Toggle between dark and light mode" + ANSI_RESET);
        System.out.println(getThemeColor("TEXT") + "  • help  - Show this help message" + ANSI_RESET);
        System.out.println(getThemeColor("TEXT") + "  • [Enter] - Finish current input section" + ANSI_RESET);
    }
}
