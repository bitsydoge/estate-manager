@file:Suppress("SpellCheckingInspection")

package com.cold0.realestatemanager.repository

import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.Photo
import java.util.*
import kotlin.random.Random.Default.nextInt


object DummyDataProvider {

	// ---------------------
	// Random Model
	// ---------------------
	private fun randomLoremIpsum(): String {
		return listOf(
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor is, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit.",
			"Mauris euismod rutrum mauris, vitae tincidunt turpis fringilla vel. Praesent vehicula urna sed ligula dignissim, vitae rhoncus augue imperdiet. Etiam ac sem justo. Quisque condimentum tincidunt urna, non bibendum nisl. Donec sollicitudin tortor eget nisl ultrices, non mattis tellus varius. Curabitur ultricies lorem et nibh venenatis laoreet. Etiam orci tellus, tincidunt et iaculis sit amet, scelerisque ut velit. Donec et malesuada lectus. Maecenas elit lectus, consequat ultricies dictum aliquet, rutrum a tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin ornare ullamcorper volutpat. Vestibulum ligula augue, commodo ut condimentum nec, bibendum varius mauris.",
			"Pellentesque semper at eros convallis lobortis. Etiam quam mi, eleifend eget finibus eu, auctor vel metus. Aliquam leo enim, vulputate in justo at, condimentum viverra velit. Aenean ex turpis, laoreet ac mi ut, consectetur facilisis ligula. Morbi a maximus lorem. Curabitur vitae enim ullamcorper, fringilla tellus eu, tristique libero. Sed lectus mauris, pulvinar vitae interdum quis, bibendum ut urna. Donec dolor turpis, consequat eget tortor elementum, elementum blandit risus. Morbi blandit dolor vel lectus eleifend, ut auctor massa vehicula. Morbi aliquam arcu augue, sed mollis enim feugiat id. Aliquam vitae felis at elit laoreet cursus sit amet id nunc. Nam eget ex id magna elementum ultricies. Praesent tempus purus a lectus aliquam ultrices. Maecenas id felis fermentum, vestibulum sem eget, vulputate ipsum.",
			"Cras sit amet elementum neque. Nunc condimentum ex elit, ac posuere nulla pellentesque non. Suspendisse id aliquet eros. Praesent efficitur vulputate ullamcorper. Proin ultrices nibh felis, ut iaculis elit dignissim quis. Phasellus lobortis enim ornare, vulputate elit vel, pretium lorem. Donec leo erat, bibendum non tellus nec, ornare ultricies massa. Aenean elementum neque sit amet ornare bibendum. Donec ultrices nibh risus, dictum scelerisque lectus vestibulum et. Aliquam sem risus, semper eget facilisis nec, sollicitudin quis nisi. Etiam vel tincidunt ipsum. Curabitur eu dui eleifend, luctus ipsum lobortis, euismod leo. Vivamus porta luctus tortor in efficitur. Phasellus pulvinar tincidunt quam, vitae imperdiet magna aliquam at. Nulla ut mattis odio.",
			"Donec id magna massa. Nullam malesuada, massa eget lobortis viverra, quam mi ullamcorper nisi, consectetur commodo dolor ante ut odio. Donec pulvinar eget metus vel sodales. Nunc non sem sit amet sapien ornare facilisis. Nam tellus diam, ornare nec turpis dictum, convallis malesuada elit. In sagittis tellus convallis, euismod enim at, efficitur enim. Curabitur ornare condimentum ligula, a tempus tellus consectetur at. Nunc nec felis at tellus imperdiet consequat id ut nisl. Proin blandit odio vel laoreet ultricies. Proin non pretium elit."
		).random().substring(0, nextInt(20, 250))
	}

	private fun randomAddress(): String {
		return listOf(
			"8739 S. Newcastle Drive\nEvans, GA 30809",
			"897 East Buckingham Dr.\nSpring Valley, NY 10977",
			"9357 Birchwood St.\nDerry, NH 03038",
			"873 E. Glen Ridge St.\nHillsboro, OR 97124",
			"276 Vine Ave.\nWhitestone, NY 11357",
			"20 Lafayette Drive\nWest Chicago, IL 60185"
		).random()
	}

	private fun randomPhotoName(): String {
		return listOf(
			"Front",
			"Kitchen",
			"Bedroom 1",
			"Bedroom 2",
			"Bedroom 3",
			"Bathroom",
			"Entrance",
			"Keeping Room",
			"Pantry",
			"Dining Room",
			"Living Room"
		).random()
	}

	private fun randomInterest(): String {
		return listOf(
			"Nearby elementary school, a supermarket and a bakery in the same street",
			"Nearby bakery",
			"College and high school within 10 minutes walking distance"
		).random()
	}

	private fun randomDistrict(): String {
		return listOf("Manhattan", "Montauk", "Brooklyn", "Southampton", "Upper East Side", "Queens", "Staten Island", "Bronx", "New Jersey").random()
	}

	private fun randomName(): String {
		return listOf("Bon", "Julia", "José", "Hamza", "Esteban", "Camille", "Marie", "Antoine").random()
	}

//	private fun randomDateString(): String {
//		return "${nextInt(1, 28).toString().padStart(2, '0')}/${nextInt(1, 13).toString().padStart(2, '0')}/2021"
//	}

	private fun randomDate(): GregorianCalendar {
		return GregorianCalendar(nextInt(2020, 2022), nextInt(0, 12), nextInt(0, 29))
	}

	private fun randomLocation(): String {
		return "-74.00${nextInt(0, 1000).toString().padStart(3, '0')},40.71${nextInt(0, 1000).toString().padStart(3, '0')}"
	}

	private fun randomPhotoUrl(): String {
		return "https://picsum.photos/id/${nextInt(150)}/400"
	}

	private inline fun <reified E : Enum<E>> randomEnum(): E {
		return enumValues<E>().random()
	}

	// ---------------------
	// Random Model
	// ---------------------
	private fun randomPhoto(): Photo {
		return Photo(name = randomPhotoName(), onlineUrl = randomPhotoUrl(), description = randomLoremIpsum())
	}

	private fun randomPhotoList(): List<Photo> {
		val list = mutableListOf<Photo>()
		repeat(nextInt(0, 8))
		{
			list.add(randomPhoto())
		}
		return Collections.unmodifiableList(list)
	}

	private fun randomEstate(): Estate {
		return Estate(
			district = randomDistrict(),
			surface = nextInt(20, 250),
			type = randomEnum(),
			description = randomLoremIpsum(),
			address = randomAddress(),
			location = randomLocation(),
			agent = randomName(),
			dateAdded = randomDate(),
			dateSold = randomDate(),
			price = nextInt(100000, 3500000),
			photos = Collections.unmodifiableList(randomPhotoList()),
			numberOfBathrooms = nextInt(1, 4),
			numberOfRooms = nextInt(4, 10),
			numberOfBedrooms = nextInt(2, 5),
			interest = randomInterest(),
			status = randomEnum()
		)
	}

	// ---------------------
	// Public
	// ---------------------
	fun getRandomEstateList(): List<Estate> {
		val list = mutableListOf<Estate>()
		repeat(nextInt(6, 12))
		{
			list.add(randomEstate())
		}
		return Collections.unmodifiableList(list)
	}
}