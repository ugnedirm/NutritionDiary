package view;

import controller.ResultController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

public class ResultView {

    public StackPane build(ResultController ctrl) {
        User user = ctrl.getUser();

        double bmi = Calculator.calculateBMI(user.getCurrentWeight(), user.getHeight());
        String bmiCat = Calculator.getBMICategory(bmi);
        double bmr = Calculator.calculateBMR(user.getCurrentWeight(), user.getHeight(), user.getAge(), user.getGender());
        double tdee = Calculator.calculateTDEE(bmr, user.getActivityLevel());
        String tdeeCat = Calculator.getTDEECategory(tdee, user.getGoalWeight(), user.getCurrentWeight());
        String water = Calculator.calculateWater(MealRepository.getWater(), user.getCurrentWeight());

        double totalCal = getTotalCalories();
        double totalProtein = getTotalProtein();
        double totalCarbs = getTotalCarbs();
        double totalFats = getTotalFats();
        double calDiff = totalCal - tdee;
        boolean hasMacros = totalProtein > 0;
        String macroCat = hasMacros
                ? Calculator.getMacroCategory(tdee, user.getGoalWeight(), user.getCurrentWeight())
                : null;

        Label title = new Label("Results for " + user.getUsername());
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        VBox summaryCard = createSummaryCard(
                totalCal, calDiff, tdee,
                totalProtein, totalCarbs, totalFats, hasMacros,
                MealRepository.getWater(), water
        );

        HBox infoRow = new HBox(20);
        infoRow.setAlignment(Pos.CENTER);

        VBox bmiCard = createCard("BMI", String.format("%.1f", bmi), bmiCat, bmiColor(bmiCat));
        VBox tdeeCardSmall = createCard("TDEE", String.format("%.0f kcal", tdee), "Daily target", "#60a5fa");

        HBox.setHgrow(bmiCard, Priority.ALWAYS);
        HBox.setHgrow(tdeeCardSmall, Priority.ALWAYS);
        bmiCard.setMaxWidth(Double.MAX_VALUE);
        tdeeCardSmall.setMaxWidth(Double.MAX_VALUE);

        if (hasMacros) {
            VBox macroCard = createMacroCard(tdee);
            HBox.setHgrow(macroCard, Priority.ALWAYS);
            macroCard.setMaxWidth(Double.MAX_VALUE);
            infoRow.getChildren().addAll(bmiCard, tdeeCardSmall, macroCard);
        } else {
            infoRow.getChildren().addAll(bmiCard, tdeeCardSmall);
        }

        VBox tdeeCard = createWideCard("Recommendation", tdeeCat, "#4CAF50");
        VBox workoutCard = createWorkoutCard();

        Button graphBtn = new Button("📈 View Graph");
        graphBtn.setStyle("-fx-background-color: #A9A9A9; -fx-text-fill: black; " +
                "-fx-font-size: 14px; -fx-background-radius: 8; " +
                "-fx-padding: 10 24; -fx-cursor: hand;");
        graphBtn.setOnAction(e -> ctrl.handleGraph());

        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(30, 30, 80, 30));

        if (hasMacros) {
            VBox macroRecCard = createWideCard("Macro Recommendation", macroCat, "#a855f7");
            content.getChildren().addAll(title, summaryCard, infoRow, tdeeCard, macroRecCard, workoutCard, graphBtn);
        } else {
            content.getChildren().addAll(title, summaryCard, infoRow, tdeeCard, workoutCard, graphBtn);
        }

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        Button againBtn = new Button("↩ Enter Data Again");
        againBtn.setStyle("-fx-background-color: #A9A9A9; -fx-text-fill: black; " +
                "-fx-font-size: 13px; -fx-background-radius: 8; " +
                "-fx-padding: 8 18; -fx-cursor: hand;");
        againBtn.setOnAction(e -> ctrl.handleEnterAgain());
        StackPane.setAlignment(againBtn, Pos.BOTTOM_LEFT);
        StackPane.setMargin(againBtn, new Insets(0, 0, 15, 15));

        Button pdfBtn = new Button("⬇ Download PDF");
        pdfBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: black; " +
                "-fx-font-size: 13px; -fx-background-radius: 8; " +
                "-fx-padding: 8 18; -fx-cursor: hand;");
        pdfBtn.setOnAction(e -> {
            String path = PdfGenerator.generate(user);
            if (path != null) {
                pdfBtn.setText("✓ Saved: " + path);
                pdfBtn.setStyle("-fx-background-color: #34d399; -fx-text-fill: black; " +
                        "-fx-font-size: 13px; -fx-background-radius: 8; " +
                        "-fx-padding: 8 18; -fx-cursor: hand;");
            } else {
                pdfBtn.setText("✗ Failed");
                pdfBtn.setStyle("-fx-background-color: #f87171; -fx-text-fill: black; " +
                        "-fx-font-size: 13px; -fx-background-radius: 8; " +
                        "-fx-padding: 8 18; -fx-cursor: hand;");
            }
        });
        StackPane.setAlignment(pdfBtn, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(pdfBtn, new Insets(0, 15, 15, 0));

        StackPane root = new StackPane(scroll, againBtn, pdfBtn);
        root.setStyle("-fx-background-image: url('/images/background.jpg'); " +
                "-fx-background-size: cover; -fx-background-position: center;");
        return root;
    }

    private double getTotalCalories() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                total += d.getCalories();
        return total;
    }

    private double getTotalProtein() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                if (d.hasNutritionalValues()) total += d.getProtein();
        return total;
    }

    private double getTotalCarbs() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                if (d.hasNutritionalValues()) total += d.getCarbohydrates();
        return total;
    }

    private double getTotalFats() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                if (d.hasNutritionalValues()) total += d.getFats();
        return total;
    }

    private String funComparison(double diff) {
        double abs = Math.abs(diff);
        String dir = diff < 0 ? "below" : "above";
        if (abs < 100) return "You're almost exactly on target!";
        if (abs < 250) return String.format("About %.0f kcal %s goal — roughly 1 apple worth!", abs, dir);
        if (abs < 500) return String.format("That's %.1f chocolate bars %s your target!", abs / 230.0, dir);
        if (abs < 900) return String.format("Roughly %.1f pizza slices %s your goal!", abs / 285.0, dir);
        return String.format("Wow — %.1f Big Macs %s your daily target!", abs / 550.0, dir);
    }

    private VBox createSummaryCard(double totalCal, double calDiff, double tdee,
                                   double protein, double carbs, double fats, boolean hasMacros,
                                   double water, String waterStatus) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.75); " +
                "-fx-background-radius: 16; -fx-border-color: #A9A9A9; " +
                "-fx-border-width: 2; -fx-border-radius: 16;");

        Label header = new Label("Daily Summary");
        header.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px;");

        String diffText = calDiff >= 0
                ? String.format("+%.0f kcal over TDEE", calDiff)
                : String.format("%.0f kcal under TDEE", Math.abs(calDiff));

        HBox calRow = new HBox(16);
        calRow.setAlignment(Pos.CENTER_LEFT);
        Label calLabel = new Label(String.format("%.0f kcal consumed   %s", totalCal, diffText));
        calLabel.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");
        Label funLabel = new Label(funComparison(calDiff));
        funLabel.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px; -fx-font-style: italic;");
        calRow.getChildren().addAll(calLabel, funLabel);
        card.getChildren().addAll(header, calRow);

        if (hasMacros) {
            Label macroLabel = new Label(String.format(
                    "protein  %.1fg     carbs  %.1fg     fats  %.1fg", protein, carbs, fats));
            macroLabel.setStyle("-fx-text-fill: #777777; -fx-font-size: 12px;");
            card.getChildren().add(macroLabel);
        }

        String waterEmoji = waterStatus.equals("Deficit") ? "💧 Drink more water!  " :
                waterStatus.equals("Surplus") ? "💧 You're well hydrated!  " :
                "💧 Perfect hydration!  ";
        Label waterLabel = new Label(waterEmoji + String.format("%.1f L today", water));
        waterLabel.setStyle("-fx-text-fill: #38bdf8; -fx-font-size: 13px; -fx-font-weight: bold;");
        card.getChildren().add(waterLabel);
        return card;
    }

    private VBox createCard(String title, String value, String subtitle, String accentColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.75);" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 16;");

        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px;");
        Label v = new Label(value);
        v.setStyle("-fx-text-fill: black; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label s = new Label(subtitle);
        s.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        card.getChildren().addAll(t, v, s);
        return card;
    }

    private VBox createMacroCard(double tdee) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.75);" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #a855f7;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 16;");

        Label t = new Label("Macros");
        t.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px;");

        Label p = new Label(String.format("%.0fg protein", Calculator.calculateProtein(tdee)));
        p.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label c = new Label(String.format("%.0fg carbs", Calculator.calculateCarbs(tdee)));
        c.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label f = new Label(String.format("%.0fg fats", Calculator.calculateFats(tdee)));
        f.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label s = new Label("Daily target");
        s.setStyle("-fx-text-fill: #a855f7; -fx-font-size: 13px; -fx-font-weight: bold;");

        card.getChildren().addAll(t, p, c, f, s);
        return card;
    }

    private VBox createWideCard(String title, String body, String accentColor) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.75);" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 16;");

        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px;");
        Label b = new Label(body);
        b.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        b.setWrapText(true);

        card.getChildren().addAll(t, b);
        return card;
    }

    private VBox createWorkoutCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.75);" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #f97316;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 16;");

        Label header = new Label("🔥 Workout & Activity");
        header.setStyle("-fx-text-fill: #f97316; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label burned = new Label(String.format("%.0f kcal burned today", MealRepository.getActiveCalories()));
        burned.setStyle("-fx-text-fill: black; -fx-font-size: 22px; -fx-font-weight: bold;");

        double active = MealRepository.getActiveCalories();
        String workoutMsg;
        if (active == 0) workoutMsg = "No workout today — rest is part of the journey too! 😴";
        else if (active < 200) workoutMsg = "Nice and easy — every move counts! 🚶";
        else if (active < 400) workoutMsg = "Solid effort today! Your body thanks you 💪";
        else if (active < 600) workoutMsg = "Great session! That's serious dedication 🏃";
        else workoutMsg = "Beast mode activated! Incredible work today 🔥";

        Label msg = new Label(workoutMsg);
        msg.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px; -fx-font-style: italic;");

        card.getChildren().addAll(header, burned, msg);
        return card;
    }

    private String bmiColor(String bmiCat) {
        if (bmiCat.equals("Underweight")) return "#60a5fa";
        if (bmiCat.equals("Normal")) return "#34d399";
        if (bmiCat.equals("Overweight")) return "#fbbf24";
        return "#f87171";
    }
}