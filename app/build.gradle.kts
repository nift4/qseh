plugins {
	id("com.android.application")
}

android {
	namespace = "org.nift4.qseh"
	compileSdk = 34

	signingConfigs {
		create("platform") {
			// create this using https://github.com/LineageOS/android_packages_apps_Updater/blob/lineage-21.0/gen-keystore.sh
			storeFile = file("platform.keystore")
			storePassword = "android"
			keyAlias = "android"
			keyPassword = "android"
		}
		named("debug") {
			// create this using https://github.com/LineageOS/android_packages_apps_Updater/blob/lineage-21.0/gen-keystore.sh
			storeFile = file("platform.keystore")
			storePassword = "android"
			keyAlias = "android"
			keyPassword = "android"
		}
	}

	defaultConfig {
		applicationId = "org.nift4.qseh"
		minSdk = 33
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"
	}

	lint {
		abortOnError = false
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
			signingConfig = signingConfigs.getByName("platform")
		}
		debug {
			signingConfig = signingConfigs.getByName("platform")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}

dependencies {
	compileOnly(files("libs/SystemUISharedLib.jar", "libs/SystemUIPluginLib.jar"))
}