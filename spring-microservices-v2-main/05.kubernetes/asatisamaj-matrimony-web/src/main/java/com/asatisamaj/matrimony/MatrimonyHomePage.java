package com.asatisamaj.matrimony;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import com.asatisamaj.matrimony.domain.MatrimonyResponse;
import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.domain.MemberDetails;
import com.asatisamaj.matrimony.domain.SearchCriteria;
import com.asatisamaj.matrimony.domain.SearchFilters;
import com.asatisamaj.matrimony.service.GenericService;
import com.asatisamaj.matrimony.utils.DropDownValues;
import com.asatisamaj.matrimony.utils.SearchOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@Theme("valo")
public class MatrimonyHomePage extends UI {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private VerticalLayout layout;

    @Value("${asatisamaj.service.url}")
    private String serviceUrl;

    @Autowired
    private GenericService genericService;

    private MatrimonyResponse matrimony;
    private MatrimonySearchCriteria matrimonySearchCriteria = new MatrimonySearchCriteria();
    private List<SearchCriteria> searchCriteriaList = new ArrayList<>();

    private static final Logger LOGGER = LogManager.getLogger(MatrimonyHomePage.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        addForm();
    }

    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setResponsive(true);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    private void addHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label header = new Label("Member List");
        header.addStyleName(ValoTheme.LABEL_BOLD);
        header.addStyleName(ValoTheme.LABEL_COLORED);
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
        layout.addComponent(headerLayout);

    }

    private void addForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsive(true);
        formLayout.setWidth("100%");
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        matrimonySearchCriteria.setPage(0);
        matrimonySearchCriteria.setSize(20);
        matrimonySearchCriteria.setSortColumn("memberId");
        matrimonySearchCriteria.setSearchCriteriaList(searchCriteriaList);

        Label pageDetails = new Label();

        matrimony = getSearchResult(matrimonySearchCriteria);
        String pageDetailstr = (matrimony.getCurrentPage() + 1) + " of " + matrimony.getTotalPages();

        pageDetails.setValue(pageDetailstr);

        DropDownValues dropDownValues = new DropDownValues();

        TextField memberIdtxtField = new TextField();
        memberIdtxtField.setPlaceholder("MemberId");
        memberIdtxtField.setResponsive(true);
        memberIdtxtField.setSizeFull();
        ComboBox<String> samajAreaCmbField = new ComboBox<>();
        samajAreaCmbField.setItems(dropDownValues.getGetSamajArea());
        samajAreaCmbField.setPlaceholder("SamajArea");
        samajAreaCmbField.setSizeFull();

        ComboBox<String> genderCmbField = new ComboBox<>();
        genderCmbField.setItems("Male", "Female");
        genderCmbField.setPlaceholder("Gender");
        genderCmbField.setSizeFull();
        ComboBox<String> educationCmbField = new ComboBox<>();
        educationCmbField.setItems(dropDownValues.getGetEducation());
        educationCmbField.setPlaceholder("Education");
        educationCmbField.setSizeFull();

        ComboBox<String> educationDetailsCmbField = new ComboBox<>();
        educationDetailsCmbField.setItems(dropDownValues.getGetEducationDetails());
        educationDetailsCmbField.setPlaceholder("Education Details");
        educationDetailsCmbField.setSizeFull();

        ComboBox<String> occupationCmbField = new ComboBox<>();
        occupationCmbField.setItems(dropDownValues.getGetOccupation());
        occupationCmbField.setPlaceholder("Occupation");
        occupationCmbField.setSizeFull();
        ComboBox<String> ageRangeCmbField = new ComboBox<>();
        ageRangeCmbField.setItems(dropDownValues.getGetAgeRange());
        ageRangeCmbField.setPlaceholder("AgeRange");
        ageRangeCmbField.setScrollToSelectedItem(true);
        ageRangeCmbField.setSizeFull();
        Grid<MemberDetails> grid = new Grid<>(MemberDetails.class);
        grid.setItems(matrimony.getMemberDetails());
        grid.setWidth("100%");
        grid.setHeight("500");
        grid.removeColumn("id");

        grid.setColumns("memberId", "fullName", "fatherName", "age", "motherName", "grandFather", "gender", "samajArea",
                "birthDate", "complexion", "manglik", "education", "educationDetails", "boardUniversity", "occupation",
                "occupationDetails", "fullAddress", "cityState", "mobile1", "mobile2", "email", "fatherOccupation",
                "brothers", "marriedBrothers", "sisters", "marriedSisters", "vansh", "gotra", "requirement");
        
        Button nextButton = new Button("Next page", e -> {
            if (matrimony.getCurrentPage() + 1 >= matrimony.getTotalPages()) {
                return;
            }
            matrimonySearchCriteria.setPage(matrimony.getCurrentPage() + 1);
            matrimony = getSearchResult(matrimonySearchCriteria);
            pageDetails.setValue((matrimony.getCurrentPage() + 1) + " of " + matrimony.getTotalPages());
            grid.setItems(matrimony.getMemberDetails());
        });
        Button previousButton = new Button("Previous page", e -> {
            if (matrimony.getCurrentPage() <= 0) {
                return;
            }
            matrimonySearchCriteria.setPage(matrimony.getCurrentPage() - 1);
            matrimony = getSearchResult(matrimonySearchCriteria);
            pageDetails.setValue((matrimony.getCurrentPage() + 1) + " of " + matrimony.getTotalPages());
            grid.setItems(matrimony.getMemberDetails());
        });

        Button searchBtn = new Button("Search", e -> {
            searchCriteriaList.clear();
            SearchFilters searchFilters = new SearchFilters();
            searchFilters.setMemberId(memberIdtxtField.getValue());
            searchFilters.setSamajArea(samajAreaCmbField.getValue());
            searchFilters.setGender(genderCmbField.getValue());
            searchFilters.setEducation(educationCmbField.getValue());
            searchFilters.setEducationDetails(educationDetailsCmbField.getValue());
            searchFilters.setOccupation(occupationCmbField.getValue());
            searchFilters.setAgeRange(ageRangeCmbField.getValue());
            applyFilteredSearch(searchFilters);
            matrimonySearchCriteria.setPage(0);
            matrimonySearchCriteria.setSize(20);
            matrimonySearchCriteria.setSortColumn("memberId");
            matrimonySearchCriteria.setSearchCriteriaList(searchCriteriaList);
            matrimony = getSearchResult(matrimonySearchCriteria);
            pageDetails.setValue((matrimony.getCurrentPage() + 1) + " of " + matrimony.getTotalPages());
            grid.setItems(matrimony.getMemberDetails());
        });

        searchBtn.setSizeFull();
        HorizontalLayout layoutSearchFields = new HorizontalLayout();
        layoutSearchFields.setWidth("100%");
        layoutSearchFields.addComponents(memberIdtxtField, samajAreaCmbField, genderCmbField, ageRangeCmbField,
                educationCmbField, educationDetailsCmbField, occupationCmbField, searchBtn);
        HorizontalLayout layoutPagination = new HorizontalLayout();
        layoutPagination.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layoutPagination.addComponents(previousButton, pageDetails, nextButton);
        
        formLayout.addComponents(layoutSearchFields, grid, layoutPagination);

        layout.addComponent(formLayout);
    }

    private void applyFilteredSearch(SearchFilters searchFilters) {
        if (null != searchFilters.getMemberId() && !searchFilters.getMemberId().isEmpty()) {
            searchCriteriaList.add(new SearchCriteria("memberId", searchFilters.getMemberId(), SearchOperation.EQUAL));
        }
        if (null != searchFilters.getSamajArea() && !searchFilters.getSamajArea().isEmpty()) {
            searchCriteriaList
                    .add(new SearchCriteria("samajArea", searchFilters.getSamajArea().trim(), SearchOperation.MATCH));
        }
        if (null != searchFilters.getGender() && !searchFilters.getGender().isEmpty()) {
            searchCriteriaList
                    .add(new SearchCriteria("gender", searchFilters.getGender().trim(), SearchOperation.EQUAL));
        }
        if (null != searchFilters.getEducation() && !searchFilters.getEducation().isEmpty()) {
            searchCriteriaList
                    .add(new SearchCriteria("education", searchFilters.getEducation().trim(), SearchOperation.MATCH));
        }
        if (null != searchFilters.getEducationDetails() && !searchFilters.getEducationDetails().isEmpty()) {
            searchCriteriaList.add(new SearchCriteria("educationDetails", searchFilters.getEducationDetails().trim(),
                    SearchOperation.MATCH));
        }
        if (null != searchFilters.getOccupation() && !searchFilters.getOccupation().isEmpty()) {
            searchCriteriaList
                    .add(new SearchCriteria("occupation", searchFilters.getOccupation().trim(), SearchOperation.MATCH));
        }
        if (null != searchFilters.getAgeRange() && !searchFilters.getAgeRange().isEmpty()) {
            if (searchFilters.getAgeRange().equalsIgnoreCase("21 to 25 Years")) {
                searchCriteriaList.add(new SearchCriteria("age", 25, SearchOperation.LESS_THAN_EQUAL));
                searchCriteriaList.add(new SearchCriteria("age", 21, SearchOperation.GREATER_THAN_EQUAL));
            } else if (searchFilters.getAgeRange().equalsIgnoreCase("26 to 30 Years")) {
                searchCriteriaList.add(new SearchCriteria("age", 30, SearchOperation.LESS_THAN_EQUAL));
                searchCriteriaList.add(new SearchCriteria("age", 26, SearchOperation.GREATER_THAN_EQUAL));
            } else if (searchFilters.getAgeRange().equalsIgnoreCase("31 to 35 Years")) {
                searchCriteriaList.add(new SearchCriteria("age", 35, SearchOperation.LESS_THAN_EQUAL));
                searchCriteriaList.add(new SearchCriteria("age", 31, SearchOperation.GREATER_THAN_EQUAL));
            } else if (searchFilters.getAgeRange().equalsIgnoreCase("36 to 40 Years")) {
                searchCriteriaList.add(new SearchCriteria("age", 40, SearchOperation.LESS_THAN_EQUAL));
                searchCriteriaList.add(new SearchCriteria("age", 36, SearchOperation.GREATER_THAN_EQUAL));
            } else if (searchFilters.getAgeRange().equalsIgnoreCase("40+ Years")) {
                searchCriteriaList.add(new SearchCriteria("age", 40, SearchOperation.GREATER_THAN));
            }

        }
    }

    private MatrimonyResponse getSearchResult(MatrimonySearchCriteria matrimonySearchCriteria) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<?> response = genericService.retriveSearchResult(serviceUrl, matrimonySearchCriteria);
        return mapper.convertValue(response.getBody(), MatrimonyResponse.class);
    }
}
