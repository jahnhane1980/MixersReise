package com.deinname.mixersreise // WICHTIG: Muss exakt dein gewählter Package-Name sein!

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize

class MainActivity : ComponentActivity() {

    // Initialisiert das ViewModel. 'by viewModels()' sorgt dafür, dass
    // die Daten (Herzen etc.) auch beim Drehen des Bildschirms erhalten bleiben.
    private val viewModel: MixerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hier könntest du beim Start einen GPS-Check anstoßen
        viewModel.updateLocation(52.52, 13.40) // Beispiel-Koordinaten Berlin

        setContent {
            // MixerTheme ist das Standard-Theme, das Android Studio generiert hat.
            // Du kannst auch einfach 'MaterialTheme' schreiben.
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Hier rufen wir dein Haupt-UI auf
                    MixerWorldScreen(viewModel = viewModel)
                }
            }
        }
    }
}