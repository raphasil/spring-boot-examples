package com.raphasil.example.controller;

import com.raphasil.example.property.ApplicationProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertyControllerTest {

    private PropertyController propertyController;

    private ApplicationProperty properties;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        properties = new ApplicationProperty();
        propertyController = new PropertyController(properties);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(propertyController)
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getProperties() throws Exception {

        mockMvc.perform(get("/api/v1/properties"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.async", isEmptyOrNullString()))
                .andExpect(jsonPath("$.clients", isEmptyOrNullString()))
                .andReturn();
    }

}