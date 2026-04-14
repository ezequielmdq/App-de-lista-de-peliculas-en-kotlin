package com.example.peliculaspopulares.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.compose.rememberLifecycleOwner
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.model.UserPreferencesViewModel
import com.example.peliculaspopulares.repositorio.SessionData
import com.example.peliculaspopulares.repositorio.UserPreferences
import com.example.peliculaspopulares.repositorio.UserPreferencesRepository
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import kotlin.coroutines.CoroutineContext

private const val TAG = "LoginScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    modifier: Modifier,
    onUserLoginButtonClicked: () -> Unit,
    onUserRegisterButtonClicked: () -> Unit,
    sessionLogin: SessionData,
    dataStore: UserPreferencesViewModel
) {

    verificarSessionAbierta(sessionLogin, onUserLoginButtonClicked)
    LoginPage(
        modifier = modifier,
        onUserLoginButtonClicked = onUserLoginButtonClicked,
        onUserRegisterButtonClicked = onUserRegisterButtonClicked,
        dataStore = dataStore
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginPage(
    modifier: Modifier,
    onUserLoginButtonClicked: () -> Unit,
    onUserRegisterButtonClicked: () -> Unit,
    dataStore: UserPreferencesViewModel,
) {

    val context = LocalContext.current
    val stateEmail = rememberTextFieldState()
    val statePassword = rememberTextFieldState()
    var passwordHidden by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.LockPerson,
            modifier = Modifier.size(width = 150.dp, height = 150.dp),
            contentDescription = "Logo",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Acceso | Login",
            color = Color.Gray,
            fontSize = 25.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            state = stateEmail,
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
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
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
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
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            onClick = {
                val emailStr = stateEmail.text.toString()
                val passwordStr = statePassword.text.toString()



                if (passwordStr.isNotEmpty()) {
                    if (emailStr.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                        loginFirebase(emailStr, passwordStr, context, onUserLoginButtonClicked, dataStore)
                        editLoginTFState(stateEmail)
                        editLoginTFState(statePassword)
                    } else {
                        // 2. Usamos la variable 'context' aquí
                        Toast.makeText(context, "Formato de correo incorrecto", Toast.LENGTH_LONG).show()
                        editLoginTFState(stateEmail)
                        editLoginTFState(statePassword)
                    }
                } else {
                    Toast.makeText(context, "La contraseña no puede estar vacía", Toast.LENGTH_LONG).show()
                    editLoginTFState(stateEmail)
                    editLoginTFState(statePassword)
                }

            }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            onClick = {
                onUserRegisterButtonClicked()
                editLoginTFState(stateEmail)
                editLoginTFState(statePassword)
            }) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            onClick = {
                scope.launch{
                    bottomSheet(
                        webClientId = context.getString(R.string.web_client),
                        context = context,
                        onUserLoginButtonClicked = onUserLoginButtonClicked,
                        dataStore = dataStore
                    )
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google Icon",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Acceso con Google")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            onClick = {
                resetPassword(stateEmail.text.toString(), context)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.GppGood,
                contentDescription = "Reset Password Icon",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Recuperar contraseña")
        }
    }
}


fun loginFirebase(email: String, password: String, context : Context, onUserLoginButtonClicked: () -> Unit, dataStore: UserPreferencesViewModel) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onUserLoginButtonClicked()
                guardarSession(task.isSuccessful, dataStore)
            } else {
                Toast.makeText(context, "Error en el login", Toast.LENGTH_LONG).show()
            }
        }
}

fun editLoginTFState(textFieldState: TextFieldState) {
    textFieldState.clearText()
}

fun guardarSession(sesion: Boolean, dataStore: UserPreferencesViewModel){
    dataStore.login(sesion)
}

fun verificarSessionAbierta(session: SessionData, onUserLoginButtonClicked: () -> Unit) {
    val currentUser = FirebaseAuth.getInstance().currentUser?.providerData
    if (currentUser != null) {
    println(currentUser)}
    else {
        println("No hay usuario logueado")
    }
    if (session.sesion) {
        onUserLoginButtonClicked()
    }
}


//This line is not needed for the project to build, but you will see errors if it is not present.
//This code will not work on Android versions < UpsideDownCake



@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
suspend fun bottomSheet(webClientId: String, context: Context, onUserLoginButtonClicked: () -> Unit, dataStore: UserPreferencesViewModel) {

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(webClientId)
        .setNonce(generateSecureRandomNonce())
        .build()

    // Create a credential request with the Google ID option.
    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    // Attempt to sign in with the created request using an authorized account
    val e = signIn(request, context, onUserLoginButtonClicked, dataStore)
    // If the sign-in fails with NoCredentialException,  there are no authorized accounts.
    // In this case, we attempt to sign in again with filtering disabled.
    if (e is NoCredentialException) {
        val googleIdOptionFalse: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setNonce(generateSecureRandomNonce())
            .build()

        val requestFalse: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptionFalse)
            .build()

        //We will build out this function in a moment
        signIn(requestFalse, context, onUserLoginButtonClicked, dataStore)
    }

}


//This function is used to generate a secure nonce to pass in with our request

fun generateSecureRandomNonce(byteLength: Int = 32): String {
    val randomBytes = ByteArray(byteLength)
    SecureRandom().nextBytes(randomBytes)
    return Base64.encodeToString(randomBytes, Base64.NO_WRAP or Base64.URL_SAFE or Base64.NO_PADDING)
}

//This code will not work on Android versions < UPSIDE_DOWN_CAKE when GetCredentialException is
//is thrown.

suspend fun signIn(
    request: GetCredentialRequest,
    context: Context,
    onUserLoginButtonClicked: () -> Unit,
    dataStore: UserPreferencesViewModel,
): Exception? {
    val credentialManager = CredentialManager.create(context)
    val failureMessage = "Sign in failed!"
    var e: Exception ?= null

    try {
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )

        handleSignIn(result.credential, dataStore, onUserLoginButtonClicked)

        Log.i(TAG, result.toString())

        Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "(☞ﾟヮﾟ)☞  Sign in Successful!  ☜(ﾟヮﾟ☜)")


    } catch (e: NoCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": No credentials found", e)
        return e

    } catch (e: GetCredentialCancellationException) {
        Toast.makeText(context, ": Sign-in cancelled", Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Sign-in was cancelled", e)
        return e

    } catch (e: GoogleIdTokenParsingException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Issue with parsing received GoogleIdToken", e)
        return e

    } catch (e: GetCredentialCustomException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Issue with custom credential request", e)
        return e

    } catch (e: GetCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Failure getting credentials", e)
        return e

    }
    return e
}

private fun firebaseAuthWithGoogle(idToken: String, dataStore: UserPreferencesViewModel, onUserLoginButtonClicked: () -> Unit) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                guardarSession(
                    sesion = true,
                    dataStore = dataStore
                )
                onUserLoginButtonClicked()
                val user = FirebaseAuth.getInstance().currentUser
                println(user.toString())
            } else {
                // If sign in fails, display a message to the user
                Log.w(TAG, "signInWithCredential:failure", task.exception)

            }
        }
}

private fun handleSignIn(credential: Credential, dataStore: UserPreferencesViewModel, onUserLoginButtonClicked: () -> Unit) {
    // Check if credential is of type Google ID
    if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        // Create Google ID Token
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

        // Sign in to Firebase with using the token
        firebaseAuthWithGoogle(googleIdTokenCredential.idToken, dataStore = dataStore , onUserLoginButtonClicked = onUserLoginButtonClicked )
    } else {
        Log.w(TAG, "Credential is not of type Google ID!")
    }
}

fun resetPassword(email: String, context: Context) {
    if (email.isNotEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Email de restablecimiento enviado a $email", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                   }
          }
    }else {
        Toast.makeText(context, "Introduce un email válido para restablecer la contraseña", Toast.LENGTH_SHORT).show()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    PeliculasPopularesTheme {
        LoginPage(
            onUserLoginButtonClicked = {},
            onUserRegisterButtonClicked = {},
            modifier = Modifier,
            dataStore = UserPreferencesViewModel(
                UserPreferencesRepository(
                    UserPreferences(LocalContext.current)
                )
            )
        )
    }
}
