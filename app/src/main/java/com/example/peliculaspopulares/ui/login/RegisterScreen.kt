package com.example.peliculaspopulares.ui.login

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme
import com.google.firebase.auth.FirebaseAuth


@Composable
fun RegisterScreen(modifier: Modifier, onRegisterButtonClicked: () -> Unit){
    RegisterPage(modifier = modifier, onRegisterButtonClicked = onRegisterButtonClicked)
}

@Composable
fun RegisterPage(modifier: Modifier = Modifier, onRegisterButtonClicked: () -> Unit) {

    val context = LocalContext.current
    val stateEmail = rememberTextFieldState()
    val statePassword = rememberTextFieldState()
    var passwordHidden by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.HowToReg,
            modifier = Modifier.size(width = 150.dp, height = 150.dp),
            contentDescription = "Logo",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Registro",
            color = Color.Gray,
            fontSize = 25.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            state = stateEmail,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = "Email"
                )
            },
            label = { Text("Email") },
        )
        Spacer(modifier = Modifier.height(16.dp))
        SecureTextField(
            state = statePassword,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") },
            textObfuscationMode =
                if (passwordHidden) TextObfuscationMode.Visible
                else TextObfuscationMode.RevealLastTyped,
            leadingIcon = {
                Icon(
                    Icons.Filled.Key,
                    contentDescription = "Password"
                )
            },
            trailingIcon = {
                val description = if (passwordHidden) "Show password" else "Hide password"
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
                    tooltip = { PlainTooltip { Text(description) } },
                    state = rememberTooltipState(),
                ) {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon = if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val emailStr = stateEmail.text.toString()
                val passwordStr = statePassword.text.toString()

                if (passwordStr.isNotEmpty()) {
                    if (emailStr.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                        registerFirebase(emailStr, passwordStr, context, onRegisterButtonClicked)
                        editRegisterTFState(stateEmail)
                        editRegisterTFState(statePassword)
                    } else {
                        Toast.makeText(context, "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                        editRegisterTFState(stateEmail)
                        editRegisterTFState(statePassword)
                    }
                } else {
                    Toast.makeText(context, "La contraseña no puede estar vacía", Toast.LENGTH_LONG).show()
                    editRegisterTFState(stateEmail)
                    editRegisterTFState(statePassword)
                }

            }) {
            Text("Registrar")
        }

    }
}


fun registerFirebase(email: String, password: String, context : Context, onRegisterButtonClicked: () -> Unit) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Usuario registrado correctamente.", Toast.LENGTH_LONG).show()
                onRegisterButtonClicked()
            } else {
                Toast.makeText(context, "Contraseña incorrecta/usuario existente.", Toast.LENGTH_LONG).show()
            }
        }
}

fun editRegisterTFState(textFieldState: TextFieldState) {
    textFieldState.clearText()
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    PeliculasPopularesTheme {
        RegisterPage(onRegisterButtonClicked = {})
    }
}