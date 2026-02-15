package com.haushekmiva.filter;

import com.haushekmiva.dto.ErrorDto;
import com.haushekmiva.exceptions.DataAccessException;
import com.haushekmiva.exceptions.InvalidParameterValueException;
import com.haushekmiva.exceptions.MissingParameterException;
import com.haushekmiva.exceptions.ResourceNotFoundException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.haushekmiva.utils.ResponseUtils.forwardUser;

@WebFilter("/*")
public class ErrorHandlingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            chain.doFilter(request, response);
        } catch (InvalidParameterValueException | MissingParameterException e) {
            forwardUserToErrorPage(
                    request,
                    response,
                    new ErrorDto(HttpServletResponse.SC_BAD_REQUEST,
                            e.getMessage())
            );
        } catch (ResourceNotFoundException e) {
            forwardUserToErrorPage(
                    request,
                    response,
                    new ErrorDto(HttpServletResponse.SC_NOT_FOUND,
                            e.getMessage())
            );
        } catch (Exception e) {
            log.error("Internal server error", e);
            forwardUserToErrorPage(
                    request,
                    response,
                    new ErrorDto(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "Internal server error")
            );
        }
    }

    private void forwardUserToErrorPage(HttpServletRequest request, HttpServletResponse response, ErrorDto errorDto) throws ServletException, IOException {
        request.setAttribute("errorDto", errorDto);
        forwardUser(request, response, "error.jsp");
    }

}
