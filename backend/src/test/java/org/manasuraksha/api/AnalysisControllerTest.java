package org.manasuraksha.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalysisController.class)
class AnalysisControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void smsEndpointReturnsExplainableAssessment() throws Exception {
        mvc.perform(post("/api/v1/analysis/sms")
                .contentType("application/json")
                .content("{\"text\":\"Please share your OTP immediately\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.riskLevel").value("HIGH"))
            .andExpect(jsonPath("$.findings[0].ruleCode").value("OTP_DISCLOSURE_REQUEST"));
    }

    @Test
    void rejectsBlankMessage() throws Exception {
        mvc.perform(post("/api/v1/analysis/sms")
                .contentType("application/json")
                .content("{\"text\":\"  \"}"))
            .andExpect(status().isBadRequest());
    }
}
