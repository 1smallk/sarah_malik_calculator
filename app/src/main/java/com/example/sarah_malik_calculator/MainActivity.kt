package com.example.sarah_malik_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sarah_malik_calculator.ui.theme.Sarah_malik_calculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sarah_malik_calculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("Addition") }
    var result by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Operations options
    val operations = listOf("Addition", "Subtraction", "Multiplication", "Division", "Modulus")

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Input fields for the operands
        TextField(
            value = operand1,
            onValueChange = { operand1 = it },
            label = { Text("Enter first number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = operand2,
            onValueChange = { operand2 = it },
            label = { Text("Enter second number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons for operation selection
        Text(text = "Choose an operation:")
        operations.forEach { operation ->
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOperation == operation,
                    onClick = { selectedOperation = operation }
                )
                Text(text = operation, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to calculate the result
        Button(onClick = {
            try {
                val num1 = operand1.toDoubleOrNull()
                val num2 = operand2.toDoubleOrNull()

                if (num1 == null || num2 == null) {
                    errorMessage = "Please enter valid numbers."
                    result = null
                } else {
                    result = when (selectedOperation) {
                        "Addition" -> (num1 + num2).toString()
                        "Subtraction" -> (num1 - num2).toString()
                        "Multiplication" -> (num1 * num2).toString()
                        "Division" -> {
                            if (num2 == 0.0) "Cannot divide by zero"
                            else (num1 / num2).toString()
                        }
                        "Modulus" -> {
                            if (num2 == 0.0) "Cannot modulus by zero"
                            else (num1 % num2).toString()
                        }
                        else -> "Unknown operation"
                    }
                    errorMessage = null
                }
            } catch (e: Exception) {
                errorMessage = "An error occurred: ${e.message}"
            }
        }) {
            Text(text = "Calculate")
        }

        // Display the result or error message
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        } else if (result != null) {
            Text(text = "Result: $result", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    Sarah_malik_calculatorTheme {
        CalculatorApp()
    }
}
