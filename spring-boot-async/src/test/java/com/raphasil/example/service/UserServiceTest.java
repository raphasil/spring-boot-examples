package com.raphasil.example.service;

import com.raphasil.example.property.ApplicationProperty;
import com.raphasil.example.property.ClientsProperty;
import com.raphasil.example.property.client.RandomUserClientProperty;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private ApplicationProperty properties;

    @Spy
    private AsyncRestTemplate asyncRestTemplate;

    @Mock
    private ClientsProperty clientsProperty;

    @Mock
    private RandomUserClientProperty randomUserClientProperty;

    private MockRestServiceServer mockServer;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockServer = MockRestServiceServer.bindTo(asyncRestTemplate).build();

        Mockito.doReturn(clientsProperty).when(properties).getClients();

        Mockito.doReturn(randomUserClientProperty).when(clientsProperty).getRandomUser();

        Mockito.doReturn("https://mock.com/").when(randomUserClientProperty).getUrl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getOneUserAsyncWithSuccessRequest() throws Exception {

        String mockResult = "mockResult";

        mockServer
                .expect(requestTo(randomUserClientProperty.getUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockResult, MediaType.APPLICATION_JSON));

        final CompletableFuture<String> completable = userService.getOneUserAsync();
        final String result = completable.get();

        Assert.assertThat(result, Matchers.is(mockResult));
    }

    @Test
    public void getOneUserAsyncWithInternalServerErrorRequest() throws Exception {

        mockServer
                .expect(requestTo(randomUserClientProperty.getUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        final CompletableFuture<String> completable = userService.getOneUserAsync();
        final String result = completable.get();

        Assert.assertThat(result, Matchers.containsString("500 Internal Server Error"));
    }

    @Test
    public void getOneUserSyncWithSuccessRequest() throws Exception {

        String mockResult = "mockResult";

        mockServer
                .expect(requestTo(randomUserClientProperty.getUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockResult, MediaType.APPLICATION_JSON));

        final String result = userService.getOneUserSync();

        Assert.assertThat(result, Matchers.is(mockResult));
    }

    @Test
    public void getOneUserSyncWithInternalServerErrorRequest() throws Exception {

        mockServer
                .expect(requestTo(randomUserClientProperty.getUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        final String result = userService.getOneUserSync();

        Assert.assertThat(result, Matchers.containsString("500 Internal Server Error"));
    }

}