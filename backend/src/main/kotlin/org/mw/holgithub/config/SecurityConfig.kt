package org.mw.holgithub.config

import org.mw.holgithub.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: UserDetailsServiceImpl) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            cors {
                disable()
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(SessionFilter())

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
        }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder(16, 32, 1, 19 * 1024, 2)
    }

    @Bean
    fun authenticationManager(passwordEncoder: PasswordEncoder): ProviderManager {
        val daoProvider = DaoAuthenticationProvider()
        daoProvider.setUserDetailsService(userDetailsService)
        daoProvider.setPasswordEncoder(passwordEncoder)
        return ProviderManager(daoProvider)
    }
}