package com.raphasil.example.async.controller;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raphasil.example.async.service.UserService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

public class UserControllerTest {

  private static final String PATH = "/api/v1/users";
  @InjectMocks
  private UserController userController;
  @Mock
  private UserService userService;
  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders
        .standaloneSetup(userController)
        .build();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void getUserSync() throws Exception {

    String mockResult = "mock result";

    Mockito.doReturn(mockResult).when(userService).getOneUserSync();

    mockMvc.perform(get(PATH + "/sync"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is(mockResult)))
        .andReturn();
  }

  @Test
  public void getUserCallable() throws Exception {

    String mockResult = "mock result";

    Mockito.doReturn(mockResult).when(userService).getOneUserSync();

    MvcResult mvcResult = this.mockMvc.perform(get(PATH + "/callable"))
        .andExpect(request().asyncStarted())
        .andExpect(request().asyncResult(instanceOf(ResponseEntity.class)))
        .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is(mockResult)))
        .andReturn();

  }

  @Test
  public void getUserDeferred() throws Exception {

    String mockResult = "mock result";

    Mockito.doReturn(CompletableFuture.completedFuture(mockResult)).when(userService).getOneUserAsync();

    MvcResult mvcResult = this.mockMvc.perform(get(PATH + "/deferred"))
        .andExpect(request().asyncStarted())
        .andExpect(request().asyncResult(instanceOf(ResponseEntity.class)))
        .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is(mockResult)))
        .andReturn();

  }
}