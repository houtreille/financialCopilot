package com.eblood.financialcopilot.household;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import tools.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HouseholdMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HouseholdMemberRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsAndRetrievesTheHouseholdMember() throws Exception {
        HouseholdMemberRequest request = new HouseholdMemberRequest(
                "Jane",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "Switzerland",
                "Switzerland",
                new BigDecimal("6000.00"),
                new BigDecimal("15000.00"));

        mockMvc.perform(post("/api/household-member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("Jane"));

        mockMvc.perform(get("/api/household-member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.countryOfResidence").value("Switzerland"))
                .andExpect(jsonPath("$.countryOfEmployment").value("Switzerland"))
                .andExpect(jsonPath("$.averageMonthlySalary").value(6000.00))
                .andExpect(jsonPath("$.currentCash").value(15000.00));
    }

    @Test
    void returnsNotFoundWhenNoHouseholdMemberExists() throws Exception {
        repository.deleteAll();

        mockMvc.perform(get("/api/household-member")).andExpect(status().isNotFound());
    }
}
