package com.cold0.realestatemanager

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.screens.converter.ConverterActivity
import com.cold0.realestatemanager.screens.photoviewer.PhotoViewerActivity
import java.util.*


@ExperimentalCoilApi
object ComposeUtils {

	// --------------------------------------
	// Easy Activity Open
	// --------------------------------------
	@ExperimentalCoilApi
	fun openPhotoViewerActivity(context: Context, photo: Photo) {
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

	// --------------------------------------------
	// Fix register for Activity not sending result
	// --------------------------------------------
	@Composable
	fun <I, O> registerForActivityResult(
		contract: ActivityResultContract<I, O>,
		onResult: (O) -> Unit,
	): ActivityResultLauncher<I> {
		val owner = LocalContext.current as ActivityResultRegistryOwner
		val activityResultRegistry = owner.activityResultRegistry
		val currentOnResult = rememberUpdatedState(onResult)
		val key = remember { UUID.randomUUID().toString() }
		val realLauncher = remember { mutableStateOf<ActivityResultLauncher<I>?>(null) }
		val returnedLauncher = remember {
			object : ActivityResultLauncher<I>() {
				override fun launch(input: I, options: ActivityOptionsCompat?) {
					realLauncher.value?.launch(input, options)
				}

				override fun unregister() {
					realLauncher.value?.unregister()
				}

				override fun getContract() = contract
			}
		}
		DisposableEffect(activityResultRegistry, key, contract) {
			realLauncher.value = activityResultRegistry.register(key, contract) {
				currentOnResult.value(it)
			}
			onDispose {
				realLauncher.value?.unregister()
			}
		}
		return returnedLauncher
	}

	// ----------------------------
	// Do action if context is Activity
	// ----------------------------
	@Composable
	fun RunWithLifecycleOwner(action: @Composable (LifecycleOwner) -> (Unit)) {
		fun Context.getActivity(): LifecycleOwner? = when (this) {
			is LifecycleOwner -> this
			is ContextWrapper -> baseContext.getActivity()
			else -> null
		}

		val activity = LocalContext.current.getActivity()
		activity?.let {
			action(it)
		}
	}

	@Composable
	fun isScreenSmall(): Boolean {
		val configuration = LocalConfiguration.current
		return configuration.screenWidthDp <= 450
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

	fun sendNotification(context: Context, title: String?, message: String?, intent: Intent?, reqCode: Int) {
		val pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT)
		val CHANNEL_ID = "ESTATE_ADDED" //
		val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
			.setSmallIcon(R.drawable.ic_menu_add)
			.setContentTitle(title)
			.setContentText(message)
			.setAutoCancel(true)
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
			.setContentIntent(pendingIntent)
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name: CharSequence = "Estate Added"
			val importance = NotificationManager.IMPORTANCE_HIGH
			val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
			notificationManager.createNotificationChannel(mChannel)
		}
		notificationManager.notify(reqCode, notificationBuilder.build()) 
	}

	// ------------------------
	// Grayscale ColorFilter
	// ------------------------
	private val grayScaleMatrix = ColorMatrix(
		floatArrayOf(
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0f, 0f, 0f, 1f, 0f
		)
	)
	val colorFilterGrayscale = ColorFilter.colorMatrix(grayScaleMatrix)
}