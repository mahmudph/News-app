/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.myone.paging_3_example.R
import id.myone.paging_3_example.ui.route.RouteName
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme


data class BottomMenuItem(
    val label: Int,
    val icon: ImageVector,
    val activeIcon: ImageVector,
    val route: String,
)

private fun prepareBottomMenuItemList(): ArrayList<BottomMenuItem> {
    val bottomMenuList = arrayListOf<BottomMenuItem>()

    bottomMenuList.add(
        BottomMenuItem(
            label = R.string.home,
            icon = Icons.Outlined.Home,
            activeIcon = Icons.Filled.Home,
            route = RouteName.home,
        )
    )

    bottomMenuList.add(
        BottomMenuItem(
            label = R.string.bookmark,
            icon = Icons.Outlined.FavoriteBorder,
            activeIcon = Icons.Filled.Favorite,
            route = RouteName.bookmark,
        )
    )

    bottomMenuList.add(
        BottomMenuItem(
            label = R.string.profile,
            icon = Icons.Outlined.Person,
            activeIcon = Icons.Filled.Person,
            route = RouteName.profile
        )
    )

    return bottomMenuList
}


@Composable
fun HomeBottomAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.background,
    navController: NavController,

    ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "main"
    val bottomMenuList = prepareBottomMenuItemList()

    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(bottomMenuList.size) { index ->
                NavigationBarItem(selected = currentRoute == bottomMenuList[index].route,
                    onClick = {
                        navController.navigate(bottomMenuList[index].route)
                    },
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            stringResource(id = bottomMenuList[index].label),
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = if (currentRoute == bottomMenuList[index].route) bottomMenuList[index].activeIcon else bottomMenuList[index].icon,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeBottomAppBarPreview() {
    Paging3exampleTheme {
        HomeBottomAppBar(navController = rememberNavController())
    }
}