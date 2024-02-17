import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class EmailAndAnonymousAuthActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    companion object {
        const val RC_SIGN_IN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        EmailAndPasswordInput()
                        AnonymousSignInButton()
                        WelcomeMessage()
                        ProductsScreen()

                    }
                }
            }
        }
    }

    @Composable
    fun EmailAndPasswordInput() {
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxSize(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxSize(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Handle action */ })
        )
        EmailSignInButton(email.value, password.value)
        SignUpButton(email.value, password.value)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EmailSignInButton(email: String, password: String) {
        val context = LocalContext.current
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Inicio de sesión exitoso, navegar a la siguiente pantalla o mostrar mensaje
                        } else {
                            // Manejar el error de inicio de sesión
                        }
                    }
            },
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text(text = "Iniciar sesión con correo electrónico")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AnonymousSignInButton() {
        val context = LocalContext.current
        Button(onClick = {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Inicio de sesión anónimo exitoso
                    } else {
                        // Manejar el error de inicio de sesión anónimo
                    }
                }
        }) {
            Text(text = "Iniciar sesión de forma anónima")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpButton(email: String, password: String) {
        val context = LocalContext.current
        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso
                        } else {
                            // Manejar el error de registro
                        }
                    }
            },
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text(text = "Registrarse con correo electrónico")
        }
    }

    @Composable
    fun WelcomeMessage() {
        val user = auth.currentUser
        if (user != null) {
            Text(
                text = "Bienvenido, ${user.displayName ?: "Usuario"}!",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun ProductsScreen() {
        val products = remember { mutableStateOf<List<String>>(emptyList()) }

        Column {
            Button(onClick = { loadProducts { products.value = it } }) {
                Text("Load Products")
            }

            LazyColumn {
                items(products.value) { product ->
                    Text(
                        text = product,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }

    private fun loadProducts(onSuccess: (List<String>) -> Unit) {
        db.collection("1").get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<String>()
                for (document in querySnapshot.documents) {
                    val productName = document.getString("name")
                    productName?.let {
                        productList.add(it)
                    }
                }
                onSuccess(productList)
            }
        .addOnFailureListener { exception ->
            // Handle failure
        }

    }
}

