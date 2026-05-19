package model;

import static java.lang.Math.round;

public class Calculator {
        // BMI
        public static double calculateBMI(double weight, double height) {
            height/=100;
            return weight/(height*height);
        }

        // BMI category
        public static String getBMICategory(double bmi) {
            if(bmi<18.5){return "Underweight";}
            else if(bmi<24.9){return "Normal";}
            else if(bmi<29.9){return "Overweight";}
            else{return "Obese";}
        }

        // BMR
        public static double calculateBMR(double weight, double height, int age, String gender) {
            if(gender.equalsIgnoreCase("male")) {
                return 10*weight+6.25*height-5*age+5;
            } else {
                return 10*weight+6.25*height-5*age-161;
            }
        }

        // TDEE
        public static double calculateTDEE(double bmr, int activityLevel) {
            if(activityLevel==1){return bmr*1.2;}
            else if(activityLevel==2){return bmr*1.375;}
            else if(activityLevel==3){return bmr*1.55;}
            else if(activityLevel==4){return bmr*1.725;}
            else {return bmr*1.9;}
        }

        // TDEE category
        public static String getTDEECategory(double tdee, double gWeight, double weight) {
            if(gWeight<weight){return "Eat "+round(tdee*0.9)+
                    " kcal to loose 0.25kg a week or "+round(tdee*0.79)+
                    " kcal to loose 0.5kg a week";}
            else if(gWeight>weight){return "Eat "+round(tdee*1.1)+
                    " kcal to gain 0.25kg a week or "+round(tdee*1.21)+
                    " kcal to gain 0.5kg a week";}
            else {return "You are at your goal weight!";}
        }

        // Calories consumed
        public static String calculateCalories(double eaten, double tdee, double burned) {
            double calories = eaten-burned;
            if(Math.abs(calories-tdee)<50){return "Sufficient";}
            else if(calories<tdee){return "Deficit";}
            else {return "Surplus";}
        }

        // Water
        public static String calculateWater(double water, double weight) {
            double rec=(weight*35)/ 1000;
            if(Math.abs(water-rec)<0.1){return "Sufficient";}
            else if(water<rec){return "Deficit";}
            else {return "Surplus";}
        }

        // Macros
        public static double calculateProtein(double tdee) {
            return round((tdee*0.30)/4);
        }

        public static double calculateCarbs(double tdee) {
            return round((tdee*0.40)/4);
        }

        public static double calculateFats(double tdee) {
            return round((tdee*0.30)/9);
        }

        // Macros category
        public static String getMacroCategory(double tdee, double gWeight, double currentWeight) {
            if(gWeight<currentWeight){
                double target25 = tdee*0.9;
                double target5 = tdee*0.79;
                return String.format(
                        "Eat protein: %.0fg  carbohydrates: %.0fg  fats: %.0fg  to lose 0.25kg a week\n" +
                                "Eat protein: %.0fg  carbohydrates: %.0fg  fats: %.0fg  to lose 0.5kg a week",
                        (target25*0.30)/4, (target25*0.40)/4, (target25*0.30)/9,
                        (target5*0.30)/4, (target5*0.40)/4, (target5*0.30)/9
                );
            } else if(gWeight>currentWeight) {
                double target25 = tdee*1.1;
                double target5 = tdee*1.21;
                return String.format(
                        "Eat protein: %.0fg  carbohydrates: %.0fg  fats: %.0fg  to gain 0.25kg a week\n" +
                                "Eat protein: %.0fg  carbohydrates: %.0fg  fats: %.0fg  to gain 0.5kg a week",
                        (target25*0.30)/4, (target25*0.40)/4, (target25*0.30)/9,
                        (target5*0.30)/4, (target5*0.40)/4, (target5*0.30)/9
                );
                } else{
                return String.format(
                        "Maintain with protein: %.0fg  carbohydrates: %.0fg  fats: %.0fg  daily",
                        calculateProtein(tdee), calculateCarbs(tdee), calculateFats(tdee)
                );
        }
    }
}
