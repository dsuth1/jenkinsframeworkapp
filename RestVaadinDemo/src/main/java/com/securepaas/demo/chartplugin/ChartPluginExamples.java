package com.securepaas.demo.chartplugin;

import java.security.Principal;
import java.util.Locale;
import java.util.Random;

import com.securepaas.demo.chartplugin.model.CustomChartTypes;
import com.securepaas.demo.chartplugin.model.MapSeries;
import com.securepaas.demo.chartplugin.model.ValueRange;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartModel;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.ZoomType;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ChartPluginExamples extends VerticalLayout {
	public static boolean mapLess3 = true;
	public static boolean map3to10 = true;
	public static boolean map10to30 = true;
	public static boolean map30to100 = true;
	public static boolean map100to300 = true;
	public static boolean map300to1000 = true;
	public static boolean map1000Plus = true;
	

    public ChartPluginExamples(Principal principal) {
    	String username="Guest";
    	if(principal!=null)
    		username=principal.getName();
        setMargin(true);
        setSpacing(true);
        addComponent(new Label(
                "<h1>Chart plugins</h1>Welcome "+username+". " +
                        "This example uses Vaadin Charts 2.0.0 with a custom heat map chart type",
                ContentMode.HTML));
        addComponent(createHeatMap());
    }

    public static Chart createHeatMap() {
    	ChartPluginExamples.mapLess3 =MyVaadinUI.isAllowed("mapLess3");
    	ChartPluginExamples.map3to10 = MyVaadinUI.isAllowed("map3to10");
    	ChartPluginExamples.map10to30 = MyVaadinUI.isAllowed("map10to30");
    	ChartPluginExamples.map30to100 = MyVaadinUI.isAllowed("map30to100");
    	ChartPluginExamples.map100to300 = MyVaadinUI.isAllowed("map100to300");
    	ChartPluginExamples.map300to1000 = MyVaadinUI.isAllowed("map300to1000");
    	ChartPluginExamples.map1000Plus = MyVaadinUI.isAllowed("map1000Plus");
    	
        final Chart chart = new Chart(CustomChartTypes.MAP);
        chart.setWidth("800px");
        chart.setHeight("500px");

        final Configuration configuration = chart.getConfiguration();
        ChartModel model = new ChartModel();
        configuration.setChart(model);

        model.setType(CustomChartTypes.MAP);
        model.setBorderWidth(1);
        model.setZoomType(ZoomType.XY);
        model.setInverted(false);

        configuration.getTitle().setText(
                "Vaadin Charts Test for complex highcharts plugin");

        XAxis xAxis = configuration.getxAxis();
        xAxis.setEndOnTick(false);
        xAxis.setGridLineWidth(0);
        xAxis.getLabels().setEnabled(false);
        xAxis.setLineWidth(0);
        xAxis.setMinPadding(0);
        xAxis.setMaxPadding(0);
        xAxis.setStartOnTick(false);
        xAxis.setTickWidth(0);

        YAxis yAxis = configuration.getyAxis();
        yAxis.setEndOnTick(false);
        yAxis.setGridLineWidth(0);
        yAxis.getLabels().setEnabled(false);
        yAxis.setLineWidth(0);
        yAxis.setMinPadding(0);
        yAxis.setMaxPadding(0);
        yAxis.setStartOnTick(false);
        yAxis.setTickWidth(0);
        yAxis.setTitle("");
        yAxis.setReversed(true);

        Legend legend = configuration.getLegend();
        legend.setHorizontalAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.BOTTOM);
        legend.setFloating(true);
        legend.setLayout(LayoutDirection.VERTICAL);
        configuration.setExporting(false);

        MapSeries series = new MapSeries();

        if(mapLess3)
        	series.addValueRange(new ValueRange(null, 3, new SolidColor(19, 64, 117, 0.05)));
        else
            series.addValueRange(new ValueRange(null, 3, new SolidColor("#ff0000")));
        if(map3to10)
        	series.addValueRange(new ValueRange(3, 10, new SolidColor(19, 64, 117,0.2)));
        else
            series.addValueRange(new ValueRange(3, 10, new SolidColor("#ff0000")));
        if(map10to30)
        	series.addValueRange(new ValueRange(10, 30, new SolidColor(19, 64, 117, 0.4)));
        else
            series.addValueRange(new ValueRange(10, 30, new SolidColor("#ff0000")));
        if(map30to100)
        	series.addValueRange(new ValueRange(30, 100, new SolidColor(19, 64, 117, 0.5)));
        else
            series.addValueRange(new ValueRange(30, 100, new SolidColor("#ff0000")));
        if(map100to300)
        	series.addValueRange(new ValueRange(100, 300, new SolidColor(19, 64, 117, 0.6)));
        else
            series.addValueRange(new ValueRange(100, 300, new SolidColor("#ff0000")));
        if(map300to1000)
        	series.addValueRange(new ValueRange(300, 1000, new SolidColor(19, 64, 117, 0.8)));
        else
            series.addValueRange(new ValueRange(300, 1000, new SolidColor("#ff0000")));
        if(map1000Plus)
        	series.addValueRange(new ValueRange(1000, null, new SolidColor(19, 64, 117, 1)));
        else
            series.addValueRange(new ValueRange(1000, null, new SolidColor("#ff0000")));

        Random random = new Random();
        for (String c : Locale.getISOCountries()) {
            DataSeriesItem p = new DataSeriesItem(c.toLowerCase(),
                    random.nextInt(1200));
            series.add(p);
        }
        configuration.addSeries(series);

        chart.drawChart(configuration);
        return chart;
    }
}
