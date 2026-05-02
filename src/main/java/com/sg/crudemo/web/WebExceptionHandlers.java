package com.sg.crudemo.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;
import java.util.Optional;

@ControllerAdvice
public class WebExceptionHandlers {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleCatalogEntityMissing(EntityNotFoundException ex,
                                             HttpServletRequest request,
                                             RedirectAttributes redirectAttributes) {
        attachCatalogMissMessage(redirectAttributes);
        return catalogLandingRedirect(request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleEmptyOptional(NoSuchElementException ex,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        attachCatalogMissMessage(redirectAttributes);
        return catalogLandingRedirect(request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleIntegrity(DataIntegrityViolationException ex,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "The submitted data violates a constraint in the catalog. Duplicate pairs or dangling references cannot be accepted.");
        return catalogLandingRedirect(request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) {
        attachCatalogMissMessage(redirectAttributes);
        return catalogLandingRedirect(request);
    }

    private static void attachCatalogMissMessage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "No catalog rows matched those identifiers.");
    }

    private static String catalogLandingRedirect(HttpServletRequest request) {
        String uri = Optional.ofNullable(request.getRequestURI()).orElse("");
        if (uri.startsWith("/authors")) {
            return "redirect:/authors";
        }
        return "redirect:/books";
    }
}
