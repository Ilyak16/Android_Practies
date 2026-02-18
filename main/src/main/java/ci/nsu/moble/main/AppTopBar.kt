package ci.nsu.moble.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    CurrentScreenTitle: Int,
    CanNavigateBack: Boolean,
    NavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {Text(stringResource(CurrentScreenTitle))},
        modifier = modifier,
        navigationIcon = {
            if (CanNavigateBack) {
                IconButton(onClick = NavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        "Назад"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}