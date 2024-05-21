package com.example.navigationdrawercompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navigationdrawercompose.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    data class NavigationItems(
        val title:String,
        var selectedIcon:ImageVector,
        var unselectedIcon:ImageVector,
        val route:String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationDrawerComposeTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {
                    navigaitonDrawer()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun navigaitonDrawer(){

    val items = listOf(
        MainActivity.NavigationItems(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "Profile Screen"
        ),
        MainActivity.NavigationItems(
            title = "Email",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            route = "Email Screen"
        ),
        MainActivity.NavigationItems(
            title = "Edit",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            route = "Edit Screen"
        )

    )

    val drawerstate = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                items.forEachIndexed { index, navItems ->

                    NavigationDrawerItem(
                        label = { Text(text = navItems.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {drawerstate.close()}
                            Toast.makeText(context,"${navItems.route}",Toast.LENGTH_SHORT).show()
                        },
                        icon = {
                            Icon(imageVector = if (index == selectedItemIndex) navItems.selectedIcon
                                else navItems.unselectedIcon
                                , contentDescription = navItems.title)
                        }
                    )
                }
            }
        },
        drawerState = drawerstate
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Navigation Drawer", fontFamily = FontFamily.Serif, color = Color.Black) },
                    navigationIcon = {

                        IconButton(onClick = { scope.launch { drawerstate.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "menu icon")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                )
            },
            containerColor = Color.White
        ) {

        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationDrawerComposeTheme {
        navigaitonDrawer()
    }
}