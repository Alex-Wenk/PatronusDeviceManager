package com.patronus.devicemanager.web

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.*


@Component
@WebFilter("/*")
class RequestLoggingFilter : Filter {

    companion object {
        private val log = LoggerFactory.getLogger(RequestLoggingFilter::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val start = Instant.now()
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        try {
            chain.doFilter(request, response)
        } finally {
            if (req.requestURI != "/health") {
                val finish = Instant.now()
                val time = Duration.between(start, finish).toMillis()
                log.info("${req.method} ${req.requestURI} ${time}ms. Result ${res.status}.")
            }
        }
    }

}