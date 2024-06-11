package com.colvir.shop.controller;

import com.colvir.shop.dto.ErrorResponse;
import com.colvir.shop.expception.CatalogNotFoundException;
import com.colvir.shop.expception.CategoryNotFoundException;
import com.colvir.shop.expception.DatabaseException;
import com.colvir.shop.expception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.colvir.shop.model.InternalErrorStatus.*;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(PRODUCT_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> categoryNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(CATEGORY_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CatalogNotFoundException.class)
    public ResponseEntity<ErrorResponse> catalogNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(CATALOG_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> databaseException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(DATABASE_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SEE_OTHER);
    }
}
