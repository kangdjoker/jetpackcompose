package id.tunjukin.jetpackcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orhanobut.hawk.Hawk

class InputPage: AppCompatActivity() {
    var initialized:Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }

    @Composable
    fun MainLayout(){
        val openLoad = remember { mutableStateOf(false) }
        val openSave = remember { mutableStateOf(false) }
        var items = remember { mutableStateListOf<InputModel.ItemPoint>() }
        if(!initialized){
            initialized = true;
            var savedItems = loadItems();
            for(item in savedItems){
                items.add(item)
            }
        }
        LaunchedEffect(items.toList()) {
            println("List was changed.")
        }
        Column (Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(all = 10.dp)){
            Title()
            Box(modifier= Modifier.height(10.dp))
            InputItem(items)
            LazyColumn (state = rememberLazyListState(), reverseLayout = false, modifier = Modifier.weight(1f)){
                items(count = items.size,
//                    key={ index->items[index].id }
                ){ index ->
                    Item( items[index],items,index)
                }
            }
            Footer(openLoad,openSave)
            when {
                openLoad.value -> {
                    showLoadFromServer(openLoad)
                }

                openSave.value -> {
                    showSaveFromServer(openSave)
                }
            }
        }
    }

    @Composable
    fun Footer(openLoad : MutableState<Boolean>,openSave : MutableState<Boolean>){
        Row {
            Button(onClick = {
                openSave.value = true
            },modifier = Modifier.weight(1f)){
                Text("Save to Server")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                openLoad.value = true
            },modifier = Modifier.weight(1f)){
                Text("Load from Server")
            }
        }
    }

    @Composable
    fun showLoadFromServer(ms:MutableState<Boolean>){
        var inputName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(text = "Load from Server") },
            text = {
                Column{
                    Text(text = "Please input saved name.")
                    TextField(value = inputName, onValueChange = {
                        inputName = it
                    } )
                }
            },
            confirmButton = {
                Button(onClick = {
                    ms.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = {
                    ms.value = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
    @Composable
    fun showSaveFromServer(ms:MutableState<Boolean>){
        var inputName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(text = "Save to Server") },
            text = {
                Column{
                    Text(text = "Please input saved name.")
                    TextField(value = inputName, onValueChange = {
                        inputName = it
                    } )
                }
            },
            confirmButton = {
                Button(onClick = {
                    ms.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = {
                    ms.value = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    @Preview
    @Composable
    fun Title(){
        Text(text = "ToDo List", style = TextStyle(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W500,
            color = androidx.compose.ui.graphics.Color.DarkGray,
            fontSize = 30.sp
        ), modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
        )
    }

    @Composable
    fun InputItem(items: MutableList<InputModel.ItemPoint>){
        var inputText by remember { mutableStateOf("") }
        Row(Modifier.fillMaxWidth()){
            TextField(value = inputText, onValueChange = {
                inputText = it
            }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                items.add(InputModel.ItemPoint(name = inputText, id = System.currentTimeMillis()))
                inputText = ""
                changeItems(items)
            }) {
                Text(text = "Save", style = TextStyle(color = Color.White),)
            }
        }
    }
    @Composable
    fun Item(itemPoint: InputModel.ItemPoint, items: MutableList<InputModel.ItemPoint>, index:Int ) {
        var checkColor = Color.Gray
        var decoration = TextDecoration.None
        if (itemPoint.checked) {
            checkColor = Color.Green
            decoration = TextDecoration.LineThrough
        }
        Column(Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp)){
            Row(Modifier.padding(top = 5.dp, bottom = 5.dp)){
                TextButton(onClick = {
                    itemPoint.checked = !itemPoint.checked
                    items[index]= InputModel.ItemPoint(
                        id = itemPoint.id,
                        name = itemPoint.name,
                        checked = itemPoint.checked
                    )
                    changeItems(items)
                }, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(all = 0.dp)){
                    Icon(Icons.Rounded.CheckCircle, tint = checkColor, contentDescription = "Checklist", modifier = Modifier
                        .padding(all = 0.dp)
                        .width(50.dp)
                        .height(50.dp))
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(itemPoint.name,overflow = TextOverflow.Ellipsis, maxLines = 2, textAlign = TextAlign.Left, modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(color = Color.Transparent), style= TextStyle(color = Color.Black, textDecoration = decoration),)
                TextButton(onClick = {
                    items.remove(itemPoint)
                    changeItems(items)
                }, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(all = 0.dp)){
                    Icon(Icons.Rounded.Delete, tint = Color.Red, contentDescription = "delete", modifier = Modifier
                        .padding(all = 0.dp)
                        .width(50.dp)
                        .height(50.dp))
                }
            }
            HorizontalDivider(thickness = (0.7).dp)
        }
    }
    private fun changeItems(items: MutableList<InputModel.ItemPoint>){
        Hawk.put("items",InputModel(items = items))
    }
    private fun loadItems():MutableList<InputModel.ItemPoint>{
        var default = mutableListOf<InputModel.ItemPoint>()
        var iModel = Hawk.get<InputModel>("items")
        if(iModel!=null){
            return iModel.items;
        }
        return default
    }
}

