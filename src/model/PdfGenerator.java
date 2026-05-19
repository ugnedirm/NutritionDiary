package model;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.FileOutputStream;

public class PdfGenerator {

    public static String generate(User user) {
        String path = user.getUsername() + "_report.pdf";

        double bmi = Calculator.calculateBMI(user.getCurrentWeight(), user.getHeight());
        String bmiCat = Calculator.getBMICategory(bmi);
        double bmr = Calculator.calculateBMR(user.getCurrentWeight(), user.getHeight(), user.getAge(), user.getGender());
        double tdee = Calculator.calculateTDEE(bmr, user.getActivityLevel());
        String tdeeRec = Calculator.getTDEECategory(tdee, user.getGoalWeight(), user.getCurrentWeight());
        String waterStatus = Calculator.calculateWater(MealRepository.getWater(), user.getCurrentWeight());
        String macroRec = Calculator.getMacroCategory(tdee, user.getGoalWeight(), user.getCurrentWeight());

        double totalCal = getTotalCalories();
        double totalProtein = getTotalProtein();
        double totalCarbs = getTotalCarbs();
        double totalFats = getTotalFats();
        boolean hasMacros = totalProtein > 0;

        try {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, Color.BLACK);
            Font headingFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(30, 30, 80));
            Font bodyFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);
            Font subtitleFont = new Font(Font.HELVETICA, 11, Font.ITALIC, new Color(100, 100, 100));
            Font greenFont = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(34, 139, 34));
            Font redFont = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(200, 50, 50));
            Font blueFont = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(50, 100, 200));

            Paragraph title = new Paragraph("Nutrition Report — " + user.getUsername(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            doc.add(title);

            addSectionHeader(doc, "User Info", headingFont);
            doc.add(new Paragraph("Age: " + user.getAge(), bodyFont));
            doc.add(new Paragraph(String.format("Height: %.1f cm", user.getHeight()), bodyFont));
            doc.add(new Paragraph(String.format("Current weight: %.1f kg", user.getCurrentWeight()), bodyFont));
            doc.add(new Paragraph(String.format("Goal weight: %.1f kg", user.getGoalWeight()), bodyFont));
            doc.add(new Paragraph("Gender: " + user.getGender(), bodyFont));
            doc.add(Chunk.NEWLINE);

            addSectionHeader(doc, "BMI", headingFont);
            doc.add(new Paragraph(String.format("BMI: %.1f — %s", bmi, bmiCat), bodyFont));
            doc.add(Chunk.NEWLINE);

            addSectionHeader(doc, "Daily Calories", headingFont);
            doc.add(new Paragraph(String.format("TDEE (daily target): %.0f kcal", tdee), bodyFont));
            doc.add(new Paragraph(String.format("Calories consumed: %.0f kcal", totalCal), bodyFont));
            doc.add(new Paragraph(String.format("Calories burned: %.0f kcal", MealRepository.getActiveCalories()), bodyFont));
            double net = totalCal - MealRepository.getActiveCalories();
            String calStatus = Calculator.calculateCalories(totalCal, tdee, MealRepository.getActiveCalories());
            Font calFont = calStatus.equals("Sufficient") ? greenFont : calStatus.equals("Deficit") ? redFont : blueFont;
            doc.add(new Paragraph("Status: " + calStatus, calFont));
            doc.add(Chunk.NEWLINE);

            addSectionHeader(doc, "Water Intake", headingFont);
            doc.add(new Paragraph(String.format("Consumed: %.1f L", MealRepository.getWater()), bodyFont));
            double rec = (user.getCurrentWeight() * 35) / 1000;
            doc.add(new Paragraph(String.format("Recommended: %.1f L", rec), bodyFont));
            Font waterFont = waterStatus.equals("Sufficient") ? greenFont : redFont;
            doc.add(new Paragraph("Status: " + waterStatus, waterFont));
            doc.add(Chunk.NEWLINE);

            addSectionHeader(doc, "Macros", headingFont);
            doc.add(new Paragraph(String.format("Recommended — protein: %.0fg  carbs: %.0fg  fats: %.0fg",
                    Calculator.calculateProtein(tdee),
                    Calculator.calculateCarbs(tdee),
                    Calculator.calculateFats(tdee)), bodyFont));
            if (hasMacros) {
                doc.add(new Paragraph(String.format("Consumed — protein: %.1fg  carbs: %.1fg  fats: %.1fg",
                        totalProtein, totalCarbs, totalFats), bodyFont));
            }
            doc.add(Chunk.NEWLINE);

            addSectionHeader(doc, "Recommendation", headingFont);
            doc.add(new Paragraph(tdeeRec, bodyFont));
            doc.add(Chunk.NEWLINE);

            if (hasMacros) {
                addSectionHeader(doc, "Macro Recommendation", headingFont);
                doc.add(new Paragraph(macroRec, bodyFont));
                doc.add(Chunk.NEWLINE);
            }

            Paragraph footer = new Paragraph("Generated by Nutrition Diary", subtitleFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            doc.add(footer);

            doc.close();
            return path;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addSectionHeader(Document doc, String text, Font font) throws DocumentException {
        Paragraph p = new Paragraph(text, font);
        p.setSpacingBefore(8);
        p.setSpacingAfter(4);
        doc.add(p);
    }

    private static double getTotalCalories() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                total += d.getCalories();
        return total;
    }

    private static double getTotalProtein() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                if (d.hasNutritionalValues()) total += d.getProtein();
        return total;
    }

    private static double getTotalCarbs() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                if (d.hasNutritionalValues()) total += d.getCarbohydrates();
        return total;
    }

    private static double getTotalFats() {
        double total = 0;
        for (String meal : new String[]{"morning", "midday", "evening"})
            for (Dish d : MealRepository.getDishes(meal))
                if (d.hasNutritionalValues()) total += d.getFats();
        return total;
    }
}