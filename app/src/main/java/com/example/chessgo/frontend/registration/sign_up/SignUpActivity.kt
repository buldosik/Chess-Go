package com.example.chessgo.frontend.registration.sign_up


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgo.frontend.MainActivity
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var uiState by mutableStateOf(SignUpUiState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database.reference
    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
                RegistrationForm {
                    val intent = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun createAccount() {
        if (uiState.isNotEmpty()) {

            auth.createUserWithEmailAndPassword(uiState.email, uiState.password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(uiState.userName)
                            .build()

                        updateUI(user, profileUpdates)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null, null)
                    }
                }
        }
    }
    private fun saveUserToDatabase(userName: String?, email: String?, uid: String?){

        val user = User(userName, email)
        if (uid != null) {
            System.out.println(1)
            database.child("Users").child(uid).setValue(user)
                .addOnSuccessListener {
                    System.out.println(2)
                    Log.d(TAG, "User data saved to database")
                }
                .addOnFailureListener { e ->
                    System.out.println(3)
                    Log.e(TAG, "Error saving user data to database: $e")
                }
        }
    }
    private fun updateUI(user: FirebaseUser?, profileUpdates: UserProfileChangeRequest?) {
        profileUpdates?.let {
            user?.updateProfile(it)?.addOnCompleteListener { profileUpdateTask ->
                if (profileUpdateTask.isSuccessful) {
                    System.out.println(uiState.userName)
                    System.out.println( uiState.email)
                    System.out.println( auth.currentUser?.uid)
                    saveUserToDatabase(uiState.userName, uiState.email, auth.currentUser?.uid)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun checkIfStringLegal(email: String?, password: String?, userName: String?): Boolean {

        if (email != null && password != null && userName != null) {
            return false
        }
        return true
    }


    @Composable
    fun RegistrationForm(onSignInClick: () -> Unit){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Registration", style = TextStyle(fontSize = 40.sp), modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))

            Spacer(modifier = Modifier.height(20.dp))
            //var email by remember { mutableStateOf("") }
            TextField(
                value = uiState.email,
                onValueChange = {email ->  uiState = uiState.copy(email = email) },
                label = { Text(text = "Email address") }
            )

            Spacer(modifier = Modifier.height(20.dp))
            //var userName by remember { mutableStateOf("") }
            TextField(
                value = uiState.userName,
                onValueChange = {userName ->  uiState = uiState.copy(userName = userName)},
                label = { Text(text = "Username") }
            )

            Spacer(modifier = Modifier.height(20.dp))
            //var password by remember { mutableStateOf("") }
            TextField(
                value = uiState.password,
                onValueChange = {password -> uiState = uiState.copy(password = password) },
                label = { Text(text = "Password") }
            )
            Spacer(modifier = Modifier.height(1.dp))

            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = { },

                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(end = 60.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                ),

                )
            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
                    onClick = {
                        createAccount()
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up Free!", style = TextStyle(fontSize = 25.sp), color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp
                    )
                ) {
                    append("Have an account? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 14.sp
                    )
                ) {
                    append("Sign in here!")
                }
            }
            ClickableText(
                text = text,
                onClick = { onSignInClick() },
            )

        }

    }
}