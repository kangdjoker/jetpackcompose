package id.tunjukin.jetpackcompose

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                Row{
                    Spacer(modifier = Modifier.width(50.dp))
                    Text("Sample ToDoApp!", style = TextStyle(
                        color= androidx.compose.ui.graphics.Color.Black,
                    ))
                }
                Spacer(modifier = Modifier.height(50.dp))
                MessageCard("Android")
            }
        }
    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name!", style = TextStyle(color = Color.Black))
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard(name = "Android")
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MessageCard(name = "Android")
    }

}



