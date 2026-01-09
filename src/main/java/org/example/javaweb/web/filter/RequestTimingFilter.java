package org.example.javaweb.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestTimingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestTimingFilter.class);

    // prag “sporog” requesta (ms) - prilagodi po želji
    private static final long SLOW_THRESHOLD_MS = 500;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            String uri = request.getRequestURI();
            String method = request.getMethod();
            int status = response.getStatus();

            if (duration >= SLOW_THRESHOLD_MS) {
                log.warn("SLOW REQUEST {} {} -> {} ({} ms)", method, uri, status, duration);
            } else {
                log.info("REQ {} {} -> {} ({} ms)", method, uri, status, duration);
            }
        }
    }
}
