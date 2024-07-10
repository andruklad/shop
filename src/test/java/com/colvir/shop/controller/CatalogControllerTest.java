package com.colvir.shop.controller;

import com.colvir.shop.config.SecurityConfig;
import com.colvir.shop.dto.CatalogRequest;
import com.colvir.shop.dto.CatalogsResponse;
import com.colvir.shop.generator.CatalogDtoGenerator;
import com.colvir.shop.generator.CatalogGenerator;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.model.User;
import com.colvir.shop.security.UserDetailsImpl;
import com.colvir.shop.security.UserService;
import com.colvir.shop.service.CatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@WebMvcTest(CatalogController.class)
@Import(SecurityConfig.class)
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogService catalogService;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    SecurityConfig securityConfig;

    @Test
    @WithMockUser(username="user",roles={"USER"})
    void save_success() throws Exception {

        //Подготовка входных данных
        CatalogRequest catalogRequest = CatalogDtoGenerator.createCatalogRequestBuilder()
                .build();
        String stringRequest = objectMapper.writeValueAsString(catalogRequest);

        //Подготовка ожидаемого результата
        Catalog expectedResponse = CatalogGenerator.createCatalogBuilder()
                .build();
        when(catalogService.save(catalogRequest)).thenReturn(expectedResponse);
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setRoles("USER");
        when(userService.loadUserByUsername("user")).thenReturn(new UserDetailsImpl(user));

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                .post("/catalog/save")
                .content(stringRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()) // TODO. Добавил в ходе экспериментов
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedResponse.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedResponse.getName()));

        verify(catalogService).save(catalogRequest);
        verifyNoMoreInteractions(catalogService);
    }

    @Test
    void getByCode_success() throws Exception {

        //Подготовка входных данных
        String catalogCodeRequest = CatalogGenerator.CATALOG_CODE;

        //Подготовка ожидаемого результата
        Catalog expectedResponse = CatalogGenerator.createCatalogBuilder()
                .build();
        when(catalogService.getByCode(catalogCodeRequest)).thenReturn(expectedResponse);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/catalog/get-by-code", catalogCodeRequest)
                        .param("catalogCode", catalogCodeRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedResponse.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedResponse.getName()));

        verify(catalogService).getByCode(catalogCodeRequest);
        verifyNoMoreInteractions(catalogService);
    }

    @Test
    void update_success() throws Exception {

        //Подготовка входных данных
        CatalogRequest catalogRequest = CatalogDtoGenerator.createCatalogRequestBuilder()
                .build();
        String stringRequest = objectMapper.writeValueAsString(catalogRequest);

        //Подготовка ожидаемого результата
        Catalog expectedResponse = CatalogGenerator.createCatalogBuilder()
                .build();
        when(catalogService.update(catalogRequest)).thenReturn(expectedResponse);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/catalog/update")
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedResponse.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedResponse.getName()));

        verify(catalogService).update(catalogRequest);
        verifyNoMoreInteractions(catalogService);
    }

    @Test
    void deleteByCode_success() throws Exception {

        //Подготовка входных данных
        String catalogCodeRequest = CatalogGenerator.CATALOG_CODE;

        //Подготовка ожидаемого результата

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/catalog/delete-by-code", catalogCodeRequest)
                        .param("catalogCode", catalogCodeRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(catalogService).deleteByCode(catalogCodeRequest);
        verifyNoMoreInteractions(catalogService);
    }

    @Test
    void getAllCatalogs_success() throws Exception {

        //Подготовка входных данных

        //Подготовка ожидаемого результата
        CatalogsResponse expectedResponse = CatalogDtoGenerator.createCatalogsResponseBuilder()
                .build();
        when(catalogService.getAllCatalogs()).thenReturn(expectedResponse);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/catalog/get-all-catalogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.catalogs[0].catalogCode").value(CatalogGenerator.CATALOG_CODE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.catalogs[1].catalogCode").value(CatalogGenerator.CATALOG_CODE_2));

        verify(catalogService).getAllCatalogs();
        verifyNoMoreInteractions(catalogService);
    }
}