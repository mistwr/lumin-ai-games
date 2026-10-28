package pt.luminai.games.nativeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Gold = Color(0xFFE6BC63)
private val Ink = Color(0xFF0D0B16)
private val Purple = Color(0xFF8065F5)
private val Consoles = listOf("Game Boy","Game Boy Color","Game Boy Advance","NES","SNES","Mega Drive","PlayStation","PSP")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var tab by remember { mutableStateOf("Biblioteca") }
            var selected by remember { mutableStateOf<String?>(null) }
            val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
                uri?.let { selected = it.lastPathSegment ?: "ROM selecionada" }
            }
            MaterialTheme(colorScheme = darkColorScheme(primary = Gold, background = Ink, surface = Color(0xFF211C31))) {
                Column(Modifier.fillMaxSize().background(Ink).padding(18.dp)) {
                    Text("✦ LUMIN AI GAMES", fontSize = 25.sp, color = Gold, fontWeight = FontWeight.Bold)
                    Text("O TEU UNIVERSO RETRO", fontSize = 12.sp, color = Gold)
                    Spacer(Modifier.height(22.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Biblioteca","Descobrir","Consolas").forEach { name ->
                            Button(onClick = { tab = name }, colors = ButtonDefaults.buttonColors(containerColor = if (tab == name) Purple else Color(0xFF2C263A))) { Text(name, fontSize = 11.sp) }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    when (tab) {
                        "Consolas" -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) { items(Consoles) { system -> Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) { Text("🎮  $system", Modifier.padding(22.dp), color = Gold, fontSize = 18.sp) } } }
                        "Descobrir" -> Column { Text("DESCOBRIR", color = Gold, fontSize = 24.sp); Spacer(Modifier.height(12.dp)); Text("Em breve: catálogo homebrew com licenças verificadas e capas.", color = Color.White) }
                        else -> Column {
                            Text("A minha biblioteca", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(12.dp))
                            Button(onClick = { picker.launch(arrayOf("application/octet-stream", "*/*")) }) { Text("＋ Importar ROM") }
                            selected?.let { Text("Selecionado: $it", color = Gold) }
                            Spacer(Modifier.height(18.dp))
                            Text("A emulação nativa será ligada após validar a base Libretro.", color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}
