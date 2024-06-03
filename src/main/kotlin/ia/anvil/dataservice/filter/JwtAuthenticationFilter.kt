package ia.anvil.dataservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.nimbusds.jose.Algorithm
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException

class JwtAuthenticationFilter(private val authenticationManager: AuthenticationManager): UsernamePasswordAuthenticationFilter() {

    init {
        setFilterProcessesUrl("/api/v1/authentication")
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val user = ObjectMapper().readValue(request?.inputStream, User::class.java)
            val usernamePasswordToken = UsernamePasswordAuthenticationToken(user.username, user.password, emptyList())

            return authenticationManager.authenticate(usernamePasswordToken)
        } catch (exception: IOException) {
            throw RuntimeException(exception)
        }
    }
}