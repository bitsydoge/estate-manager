package com.cold0.realestatemanager.screens

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.screens.converter.ConverterActivity
import com.cold0.realestatemanager.screens.editestate.EditEstateActivity
import com.cold0.realestatemanager.screens.photoviewer.PhotoViewerActivity


@ExperimentalCoilApi
object ScreensUtils {
	@ExperimentalCoilApi
	fun openPhotoViewerActivity(context: Context, photo: String) {
		val intent = Intent(context, PhotoViewerActivity::class.java).apply {
			putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}

	fun openConverterActivity(context: Context) {
		val intent = Intent(context, ConverterActivity::class.java).apply {
			//putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}

	@Composable
	fun OpenEditEstateActivity(context: Context, estate: Estate, onResult: (Estate) -> (Unit) = {}) {
		val intent = Intent(context, EditEstateActivity::class.java)
		intent.putExtra("estate", estate)
		val loginLauncher = rememberLauncherForActivityResult(
			ActivityResultContracts.StartActivityForResult()
		) { result ->
			if (result != null) {
				result.data?.getParcelableExtra<Estate>("estate")?.let { onResult(it) }
			}
		}
		SideEffect {
			loginLauncher.launch(intent)
		}
		//ContextCompat.startActivity(context, intent, null)
	}

	fun formatApiRequestGeoapify(
		width: Int = 400,
		height: Int = 400,
		localisation: String = "-74.005157,40.710785",
		apiKey: String = BuildConfig.GEOAPIFY_KEY,
	): String {
		return "https://maps.geoapify.com/v1/staticmap" +
				"?style=osm-bright-grey" +
				"&width=$width&height=$height" +
				"&center=lonlat:$localisation" +
				"&zoom=16.4226&pitch=44" +
				"&marker=lonlat:$localisation;color:%23ff0000;size:medium" +
				"&apiKey=$apiKey"
	}
}