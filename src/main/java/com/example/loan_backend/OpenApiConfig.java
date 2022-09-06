package com.example.loan_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Loan", description = "Here is the documentation for loan backend, you can use to check the response types and request types. For protected routes /signup and /signin if not registered , else use auth/signin to generate a token and press the lock on protected routes before sending any request to them."))
public class OpenApiConfig {
}
