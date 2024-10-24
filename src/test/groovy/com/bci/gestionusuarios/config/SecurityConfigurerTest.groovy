package com.bci.gestionusuarios.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Title("SecurityConfigurer unit tests")
@SpringBootTest
@AutoConfigureMockMvc // Esto configura automáticamente MockMvc con seguridad
class SecurityConfigurerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "should forbid access to secured endpoint without authentication"() {
        expect:
        mockMvc.perform(get("/api/some-secured-endpoint"))
                .andExpect(status().isForbidden()) // Debe denegar el acceso sin autenticación
    }
}