package by.krutikov.controller.commonendpoints;

import by.krutikov.BaseIntegrationTest;
import by.krutikov.annotations.IT;
import org.junit.jupiter.api.Test;

@IT
class RegistrationControllerTest extends BaseIntegrationTest {

    @Test
    void createNewAccount() {
    }
}
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void updateUser() throws Exception {
//        Map<?, ?> map =
//                objectMapper.readValue(
//                        Paths.get("src/test/resources/json/create_account.json").toFile(), Map.class);
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders.put("/api/users/2")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(map))
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.user.firstName").value("Vladimir"))
//                .andExpect(jsonPath("$.user.lastName").value("Ivanov"))
//                .andExpect(jsonPath("$.user.credentials.userLogin").value("JsonTestLogin"));
//    }
