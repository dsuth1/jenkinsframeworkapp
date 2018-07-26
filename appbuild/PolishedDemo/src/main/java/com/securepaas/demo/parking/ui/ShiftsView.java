package com.securepaas.demo.parking.ui;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.securepaas.demo.parking.ParkingUI;
import com.securepaas.demo.parking.Shift;
import com.securepaas.demo.parking.util.DataUtil;
import com.vaadin.addon.touchkit.ui.DatePicker;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent.Direction;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ShiftsView extends NavigationManager {
	private static final long serialVersionUID = -3692886502458049040L;
	private static final String STYLE_NAME = "shifts";
    private static final String STYLE_NAME_TABLE = "shiftstable";
    private static final String STYLE_NAME_FILTER = "shiftsfilter";

    private BeanItemContainer<Shift> shiftContainer;

    private final Collection<Field<?>> filterFields = Lists.newArrayList();

    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, ParkingUI.getApp().getLocale());
    private final DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, ParkingUI.getApp().getLocale());

    @Override
    public void attach() {
        super.attach();
        if (shiftContainer == null) {
            buildUi();
        }
    };

    private void buildUi() {
        setStyleName(STYLE_NAME);
        setCaption("Shifts");
        setSizeFull();

        shiftContainer = new BeanItemContainer<Shift>(Shift.class,DataUtil.getShifts());

        CssLayout contentLayout = new CssLayout();
        contentLayout.setCaption("Shifts");
        contentLayout.setSizeFull();

        NavigationView filteringLayout = buildFilteringLayout();
        contentLayout.addComponent(filteringLayout);
        contentLayout.addComponent(buildEditFiltersButton(filteringLayout));
        contentLayout.addComponent(buildShiftTable());

        setCurrentComponent(contentLayout);

    }

    private Component buildEditFiltersButton(
            final NavigationView filteringLayout) {
        final VerticalComponentGroup editFiltersGroup = new VerticalComponentGroup();
        editFiltersGroup.addStyleName("editfiltersbutton");

        final Component filtersContent = filteringLayout.getContent();

        final NavigationView navigationView = new NavigationView("Filters");
        navigationView.addStyleName("filtersview");
        navigationView.setSizeFull();
        navigationView.setRightComponent(buildClearButton());
        final NavigationButton editButton = new NavigationButton(
                "Edit filters...", navigationView);

        ValueChangeListener vcl = new ValueChangeListener() {
			private static final long serialVersionUID = 2875557801781026970L;

			@Override
            public void valueChange(final ValueChangeEvent event) {
                if (shiftContainer.hasContainerFilters()) {
                    StringBuilder sb = new StringBuilder();
                    for (Field<?> field : filterFields) {
                        Object value = field.getValue();
                        if (value != null
                                && !String.valueOf(value).trim().isEmpty()) {
                            if (!sb.toString().isEmpty()) {
                                sb.append(", ");
                            }

                            if (value instanceof Date) {
                                sb.append(dateFormat.format(value));
                            } else if (field instanceof AbstractSelect) {
                                sb.append(((AbstractSelect) field)
                                        .getItemCaption(value));
                            } else {
                                sb.append(String.valueOf(value));
                            }
                        }
                    }
                    editButton.setCaption(sb.toString());
                } else {
                    editButton.setCaption("Edit filters...");
                }
            }
        };

        for (Field<?> field : filterFields) {
            field.addValueChangeListener(vcl);
        }

        addNavigationListener(new NavigationListener() {
			private static final long serialVersionUID = 8470397446316837955L;

			@Override
            public void navigate(final NavigationEvent event) {
                if (event.getDirection() == Direction.FORWARD) {
                    navigationView.setContent(filtersContent);
                } else {
                    filteringLayout.setContent(filtersContent);
                }
            }
        });
        editFiltersGroup.addComponent(editButton);
        return editFiltersGroup;
    }

    private Component buildClearButton() {
        return new Button("Clear", new ClickListener() {
			private static final long serialVersionUID = -834322930138491237L;

			@Override
            public void buttonClick(ClickEvent event) {
                for (Field<?> field : filterFields) {
                    field.setValue(null);
                }
            }
        });
    }

    private static String toFirstUpper(final String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    private Component buildShiftTable() {
        final Table shiftTable = new Table(null, shiftContainer) {
			private static final long serialVersionUID = -2693775441145218442L;

			@Override
            protected String formatPropertyValue(Object rowId, Object colId,
                    Property<?> property) {
                String result = super.formatPropertyValue(rowId, colId,
                        property);
                if ("date".equals(colId)) {
                    result = dateFormat.format(property.getValue());
                } else if ("start".equals(colId) || "end".equals(colId)) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, (Integer) property.getValue());
                    cal.set(Calendar.MINUTE, 0);
                    result = timeFormat.format(cal.getTime());
                }
                return result;
            }
        };
        shiftTable.setSizeFull();
        shiftTable.setVisibleColumns(new Object[] { "name", "area", "date",
                "start", "end" });
        for (Object propertyId : shiftTable.getVisibleColumns()) {
            shiftTable.setColumnHeader(propertyId,
                    toFirstUpper((String) propertyId));
        }
        shiftTable.setSortContainerPropertyId("date");
        shiftTable.setSelectable(true);
        
    	VerticalLayout layout=new VerticalLayout();   	
    	layout.addStyleName(STYLE_NAME_TABLE);
    	layout.addComponent(shiftTable);
    	   	
    	Button refresh=new Button();
    	refresh.setIcon(FontAwesome.REFRESH);
    	refresh.setStyleName("icon-only");
    	refresh.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 5669348005675297880L;

			@Override
			public void buttonClick(ClickEvent event) {
				refreshShiftList();		
			}
    		
    	});
    	layout.addComponent(refresh);
    	layout.setSizeFull();
    	layout.setExpandRatio(shiftTable, 1);
    	
        return layout;
    }
    
    private void refreshShiftList()
    {
    	shiftContainer.removeAllItems();
    	Collection<Shift> shifts=DataUtil.getShifts();
    	if(shifts!=null && !shifts.isEmpty())
    		shiftContainer.addAll(shifts);
    }

    private NavigationView buildFilteringLayout() {
        final NavigationView filteringLayout = new NavigationView();
        filteringLayout.addStyleName(STYLE_NAME_FILTER);
        filteringLayout.setCaption("Filters");

        filteringLayout.setContent(buildFiltersLayout());
        filteringLayout.setRightComponent(buildClearButton());

        return filteringLayout;
    }

    private Component buildFiltersLayout() {
        filterFields.add(new FilteringTextField("name"));
        filterFields.add(new FilteringTextField("area"));
        filterFields.add(new FilteringDatePicker("date"));
        filterFields.add(new HourSelect("start"));
        filterFields.add(new HourSelect("end"));

        final VerticalComponentGroup filtersGroup = new VerticalComponentGroup();
        for (Field<?> field : filterFields) {
            filtersGroup.addComponent(field);
        }
        return filtersGroup;
    }

    private class FilteringTextField extends TextField {
		private static final long serialVersionUID = -3487638946938891880L;
		private Filter filter;
        private final String propertyId;

        public FilteringTextField(final String propertyId) {
            setWidth(100.0f, Unit.PERCENTAGE);
            setCaption(toFirstUpper(propertyId));
            setNullRepresentation("");
            this.propertyId = propertyId;

            addTextChangeListener(new TextChangeListener() {
				private static final long serialVersionUID = 4299275410113493950L;

				@Override
                public void textChange(final TextChangeEvent event) {
                    textChanged(event.getText());
                }
            });

            addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 7381191093736333515L;

				@Override
                public void valueChange(
                        final com.vaadin.data.Property.ValueChangeEvent event) {
                    textChanged((String) event.getProperty().getValue());
                }
            });
        }

        private void textChanged(String text) {
            shiftContainer.removeContainerFilter(filter);
            refreshShiftList();
            if (text != null && !text.isEmpty()) {
                filter = new SimpleStringFilter(propertyId, text, true, false);
                shiftContainer.addContainerFilter(filter);
            }
        }
    }

    private class FilteringDatePicker extends DatePicker {
		private static final long serialVersionUID = 1790027891971949274L;
		private Filter filter;

        public FilteringDatePicker(final String propertyId) {
            setWidth(100.0f, Unit.PERCENTAGE);
            setCaption(toFirstUpper(propertyId));

            addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 2043211239846397600L;

				@Override
                public void valueChange(
                        final com.vaadin.data.Property.ValueChangeEvent event) {
                    shiftContainer.removeContainerFilter(filter);
                    final Date filterValue = (Date) event.getProperty()
                            .getValue();
                    if (filterValue != null) {
                        filter = new Filter() {
							private static final long serialVersionUID = -7921025639499067185L;

							@Override
                            public boolean passesFilter(final Object itemId,
                                    final Item item)
                                    throws UnsupportedOperationException {
                                Date propertyValue = (Date) item
                                        .getItemProperty(propertyId).getValue();
                                return propertyValue.after(filterValue);
                            }

                            @Override
                            public boolean appliesToProperty(final Object pid) {
                                return Objects.equal(pid, propertyId);
                            }
                        };
                        shiftContainer.addContainerFilter(filter);
                    }
                }
            });

        }
    }

    private class HourSelect extends NativeSelect {
		private static final long serialVersionUID = 6570893509919083763L;
		private Filter filter;

        public HourSelect(final String propertyId) {
            setCaption(toFirstUpper(propertyId));
            setImmediate(true);
            setWidth(100.0f, Unit.PERCENTAGE);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MINUTE, 0);
            addItem(-1);
            setItemCaption(-1, "Choose...");
            setNullSelectionItemId(-1);
            setNullSelectionAllowed(false);
            for (int i = 0; i < 24; i++) {
                cal.set(Calendar.HOUR_OF_DAY, i);
                addItem(i);
                setItemCaption(i, timeFormat.format(cal.getTime()));
            }

            addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = -3757125365894801497L;

				@Override
                public void valueChange(
                        final com.vaadin.data.Property.ValueChangeEvent event) {
                    shiftContainer.removeContainerFilter(filter);
                    final Integer filterValue = (Integer) event.getProperty()
                            .getValue();
                    if (filterValue != null) {
                        filter = new Filter() {
							private static final long serialVersionUID = -3076479928110532359L;

							@Override
                            public boolean passesFilter(final Object itemId,
                                    final Item item)
                                    throws UnsupportedOperationException {
                                return filterValue.equals(item.getItemProperty(
                                        propertyId).getValue());
                            }

                            @Override
                            public boolean appliesToProperty(final Object pid) {
                                return Objects.equal(pid, propertyId);
                            }
                        };
                        shiftContainer.addContainerFilter(filter);
                    }
                }
            });
        }
    }
}
