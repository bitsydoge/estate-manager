package com.cold0.realestatemanager.screens.home.filter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.screens.commons.OutlinedDropDown
import com.cold0.realestatemanager.screens.commons.OutlinedFieldFromTo
import com.cold0.realestatemanager.screens.home.HomeViewModel

@Suppress("SelfAssignment")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun EtateListFilter(top: Dp, viewmodel: HomeViewModel, closeFilterView: () -> (Unit)) {
	Column(Modifier
		.padding(top = top, start = 8.dp, end = 8.dp, bottom = 8.dp)
		.fillMaxHeight()
		.fillMaxWidth()
		.verticalScroll(rememberScrollState())
	) {
		var estateFrom by remember {
			mutableStateOf(viewmodel.getFilterSetting().from,
				policy = neverEqualPolicy())
		}

		var estateTo by remember {
			mutableStateOf(viewmodel.getFilterSetting().to,
				policy = neverEqualPolicy())
		}

		var mapOfProps by remember {
			mutableStateOf(viewmodel.getFilterSetting().mapOfProps,
				policy = neverEqualPolicy())
		}

		var checkboxType by remember {
			mutableStateOf(viewmodel.getFilterSetting().type,
				policy = neverEqualPolicy())
		}
		var checkboxStatus by remember {
			mutableStateOf(viewmodel.getFilterSetting().status,
				policy = neverEqualPolicy())
		}

		Row(Modifier.fillMaxWidth())
		{
			Button(onClick = {
				// Reset map
				mapOfProps = FilterSetting.Default.mapOfProps

				// Reset Estates
				estateTo = FilterSetting.Default.to
				estateFrom = FilterSetting.Default.from

				// Reset Enum Checkbox
				checkboxType = FilterSetting.Default.type
				checkboxStatus = FilterSetting.Default.status

				viewmodel.setFilterSetting(FilterSetting.Default)
				closeFilterView()
			},
				Modifier
					.weight(1.0f)) {
				Text("Reset")
			}
			Spacer(Modifier.width(8.dp))
			Button(onClick = {
				viewmodel.setFilterSetting(FilterSetting(
					to = estateTo,
					from = estateFrom,
					enabled = true,
					type = checkboxType,
					status = checkboxStatus,
					mapOfProps = mapOfProps
				))
				closeFilterView()
			},
				Modifier
					.weight(1.0f)) {
				Text("Apply")
			}
		}

		Row(Modifier.padding(top = 8.dp)) {
			Checkbox(checked = checkboxType, onCheckedChange = { checkboxType = it })
			Text(text = "Type",
				fontWeight = FontWeight.Bold,
				color = Color(0xffa0a0a0),
				modifier = Modifier.padding(start = 8.dp))
		}
		OutlinedDropDown(currentSelected = estateFrom.type, onValueSelected = {
			estateFrom.type = it
			estateFrom = estateFrom
		})

		Row(Modifier.padding(top = 8.dp)) {
			Checkbox(checked = checkboxStatus, onCheckedChange = { checkboxStatus = it })
			Text(text = "Status",
				fontWeight = FontWeight.Bold,
				color = Color(0xffa0a0a0),
				modifier = Modifier.padding(start = 8.dp))
		}
		OutlinedDropDown(label = "Status", currentSelected = estateFrom.status, onValueSelected = {
			estateFrom.status = it
			estateFrom = estateFrom
		})

		// -------------------
		// Props Spawn
		// -------------------
		mapOfProps.keys.forEach { field ->
			Row(Modifier.padding(top = 8.dp)) {
				Checkbox(checked = mapOfProps[field] ?: false, onCheckedChange = { mapOfProps[field] = it; mapOfProps = mapOfProps })
				Text(
					text = field.getName(),
					fontWeight = FontWeight.Bold,
					color = Color(0xffa0a0a0),
					modifier = Modifier.padding(start = 8.dp)
				)
			}
			OutlinedFieldFromTo(propsContainer = field, from = estateFrom, to = estateTo, onValuesChanged = { from, to -> estateFrom = from; estateTo = to })
		}
	}
}