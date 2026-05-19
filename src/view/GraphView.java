package view;

import controller.GraphController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.User;
import model.WeightEntry;

public class GraphView {

    public StackPane build(GraphController ctrl) {
        User user = ctrl.getUser();

        Label title = new Label("Weight Progress");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label(user.getUsername() + "  ·  goal: " + user.getGoalWeight() + " kg");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        xAxis.setStyle("-fx-tick-label-fill: #aaaaaa; -fx-text-fill: #aaaaaa;");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (kg)");
        yAxis.setStyle("-fx-tick-label-fill: #aaaaaa; -fx-text-fill: #aaaaaa;");
        yAxis.setAutoRanging(false);

        double minWeight = user.getWeightHistory().stream()
                .mapToDouble(WeightEntry::getWeight).min().orElse(50);
        double maxWeight = user.getWeightHistory().stream()
                .mapToDouble(WeightEntry::getWeight).max().orElse(100);
        double padding = 5;
        yAxis.setLowerBound(Math.min(minWeight, user.getGoalWeight()) - padding);
        yAxis.setUpperBound(Math.max(maxWeight, user.getWeightHistory().isEmpty() ? maxWeight : user.getWeightHistory().get(0).getWeight()) + padding);
        yAxis.setTickUnit(1);

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalZeroLineVisible(false);
        chart.setStyle("-fx-background-color: #1a1a2e;");

        XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();
        for (WeightEntry entry : user.getWeightHistory()) {
            weightSeries.getData().add(
                    new XYChart.Data<>(entry.getDate().toString(), entry.getWeight())
            );
        }

        XYChart.Series<String, Number> startSeries = new XYChart.Series<>();
        double startWeight = user.getWeightHistory().isEmpty()
                ? user.getCurrentWeight()
                : user.getWeightHistory().get(0).getWeight();
        for (WeightEntry entry : user.getWeightHistory()) {
            startSeries.getData().add(
                    new XYChart.Data<>(entry.getDate().toString(), startWeight)
            );
        }

        XYChart.Series<String, Number> goalSeries = new XYChart.Series<>();
        for (WeightEntry entry : user.getWeightHistory()) {
            goalSeries.getData().add(
                    new XYChart.Data<>(entry.getDate().toString(), user.getGoalWeight())
            );
        }

        chart.getData().addAll(weightSeries, startSeries, goalSeries);

        Platform.runLater(() -> {
            if (chart.lookup(".chart-plot-background") != null)
                chart.lookup(".chart-plot-background").setStyle("-fx-background-color: #1a1a2e;");
            if (chart.lookup(".chart-content") != null)
                chart.lookup(".chart-content").setStyle("-fx-padding: 0;");
            if (chart.lookup(".chart-border") != null)
                chart.lookup(".chart-border").setStyle("-fx-border-color: transparent;");

            if (weightSeries.getNode() != null)
                weightSeries.getNode().setStyle("-fx-stroke: #60a5fa; -fx-stroke-width: 2.5px;");
            if (startSeries.getNode() != null)
                startSeries.getNode().setStyle("-fx-stroke: #f87171; -fx-stroke-width: 1.5px; -fx-stroke-dash-array: 8 4;");
            if (goalSeries.getNode() != null)
                goalSeries.getNode().setStyle("-fx-stroke: #34d399; -fx-stroke-width: 1.5px; -fx-stroke-dash-array: 8 4;");

            for (XYChart.Data<String, Number> d : startSeries.getData())
                if (d.getNode() != null) d.getNode().setVisible(false);
            for (XYChart.Data<String, Number> d : goalSeries.getData())
                if (d.getNode() != null) d.getNode().setVisible(false);
        });

        Label weightLegend = new Label("● Weight");
        weightLegend.setStyle("-fx-text-fill: #60a5fa; -fx-font-size: 12px;");
        Label startLegend = new Label("- - Starting weight");
        startLegend.setStyle("-fx-text-fill: #f87171; -fx-font-size: 12px;");
        Label goalLegend = new Label("- - Goal weight");
        goalLegend.setStyle("-fx-text-fill: #34d399; -fx-font-size: 12px;");

        HBox legend = new HBox(20, weightLegend, startLegend, goalLegend);
        legend.setAlignment(Pos.CENTER);

        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(12, title, subtitle, chart, legend);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(30));
        VBox.setVgrow(chart, Priority.ALWAYS);

        StackPane root = new StackPane(content, backBtn);
        root.setStyle("-fx-background-color: #1a1a2e;");
        StackPane.setAlignment(backBtn, Pos.BOTTOM_LEFT);
        StackPane.setMargin(backBtn, new Insets(0, 0, 15, 15));

        return root;
    }
}