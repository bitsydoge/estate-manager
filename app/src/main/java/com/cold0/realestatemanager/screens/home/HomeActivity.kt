package com.cold0.realestatemanager.screens.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetails
import com.cold0.realestatemanager.theme.RealEstateManagerTheme


@ExperimentalCoilApi
@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val viewModel: HomeViewModel by viewModels()
		viewModel.initDatabase(applicationContext)

		setContent {
			var openLeftDrawer by remember { mutableStateOf(true) }

			val estateList by viewModel.rememberEstateList();
			viewModel.updateEstateListFromDB()
			val estateSelected by viewModel.rememberEstateSelected()

			Log.e("", "onCreate: $estateSelected")

			RealEstateManagerTheme {
				HomeTopAppBar(
					viewModel = viewModel,
					listEstate = estateList,
					toggleDrawer = {
						openLeftDrawer = !openLeftDrawer
					}
				) {
					// ----------------------------
					// Message when list is Empty
					// ----------------------------
					if (estateList.isNullOrEmpty()) {
						Column(
							modifier = Modifier.fillMaxSize(),
							verticalArrangement = Arrangement.Center,
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							Text(
								text = "There is no Estate, press the + button in the top right to add new Estate and start editing it",
								style = MaterialTheme.typography.h6.copy(color = Color.LightGray),
								modifier = Modifier.padding(64.dp)
							)
						}
					}
					// ----------------------------
					// When List is not Empty
					// ----------------------------
					else estateList?.let { estateListChecked ->
						if (estateListChecked.find { it.uid == estateSelected?.first ?: -1 } == null) {
							if (!estateList.isNullOrEmpty())
								estateList!!.first().let { viewModel.setSelectedEstate(it.uid, it.timestamp) }
						}
						Row(Modifier.fillMaxSize()) {
							AnimatedVisibility(visible = openLeftDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
								EstateList(estateListChecked, viewModel)
							}
							estateListChecked.find { it.uid == estateSelected?.first ?: 0 }
								?.let { EstateDetails(it) }
						}
					}
				}

				// ----------------------------
				// Debug Build Only
				// ----------------------------
				if (BuildConfig.DEBUG) {
					HomeDebug(viewModel = viewModel)
				}
			}
		}
	}
}