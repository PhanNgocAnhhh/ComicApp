plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.comicapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.comicapp"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    //noinspection GradleCompatible
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    // Add the Realtime Database SDK
    implementation("com.google.firebase:firebase-database")
    // Thư viện load ảnh Picasso
    implementation ("com.squareup.picasso:picasso:2.5.2")
    // PhotoView trong ViewPager
    implementation ("com.github.chrisbanes:PhotoView:2.0.0")
    // Banner
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    // Circle Image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.wajahatkarim:EasyFlipView:3.0.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}