# TV Show Manager

<img src="https://www.svod.org/wp-content/uploads/2014/11/combyne_logo.jpg" width="500" style="max-width:100%;">

This is take way assigment for Combyne recruitment process.

## Table of Contents

- [Prerequisite](#prerequisite)
- [The App](#theapp)
- [Architecture](#architecture)
- [Testing](#testing)
- [ScreenShots](#screenshots)
- [Sample App and Source Code](#sampleappandsourcecode)

## Prerequisite

This project uses the Gradle build system. To build this project, use the
`gradlew build` command after cloning or use "Import Project" in Android Studio.

## The App

A small app that loads and presents a list of TV Shows added by the user and other users. 
The app has one Activity that only act as entry point with two fragmnents; ShowListFragments which show list of 
added TV Shows and AddShowFragment which enable the user to add and save new TV Shows; connected with 
 [Android Jetpack's Navigation component](https://developer.android.com/guide/navigation?gclid=Cj0KCQjw1dGJBhD4ARIsANb6Odmh4CV6GS_peBCMej132Lmw-XgWFrSs-QD5qIATxSGStgHi4OLvlkAaAilWEALw_wcB&gclsrc=aw.ds).

The project has been written in Kotlin language. For network requests, it uses Apollo's Graphql with RxJava.

Dagger2 has been used for Dependency injection.

## Architecture
The project is built using the MVVM architectural pattern and make heavy use of a couple of Android Jetpack components. Mvvm allows for the separation of concern which also makes testing easier.


## MVVM implementation
The first time the app is opened, the data will be fetched from the backend api service and stored locally with 
the help of Apollo's SqlNormalizedCacheFactory which stores data in a SQLDelight database and  LruNormalizedCache 
which stores data in memory.
I have chained an LruNormalizedCacheFactory with a SqlNormalizedCacheFactory to get the best of both caches.
But if there is no internet or the api service is down, the data will be fetched from the local cache.
This is handled in the repository class.
ViewModel is basically responsible for updating the UI (Activity/Fragment) with the data changes.
The ViewModel will initialise an instance of the Repository class and update the UI based with this data.



## Testing
All tests are under the Android Test package. All the tests are run using JUnit.

## ScreenShots


The app is available in both day and night theme.


<img src="https://user-images.githubusercontent.com/47601553/132138596-4a7c9a6a-7ae0-45fb-a239-ceafb1d7f870.jpg" width="200" style="max-width:100%;">    <img src="https://user-images.githubusercontent.com/47601553/132138675-5d6261cc-6824-4cb8-af66-a16ef1a2c9f7.jpg" width="200" style="max-width:100%;">   <img src="https://user-images.githubusercontent.com/47601553/132138704-d4d86bb7-3cd3-4c9d-8914-8bc83ee57847.jpg" width="200" style="max-width:100%;">    <img src="https://user-images.githubusercontent.com/47601553/132138611-a8eef9c0-be0e-4ea7-92cd-2802ca332a9a.jpg" width="200" style="max-width:100%;"></br></br>

## Libraries

Libraries used in the whole application are:

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
- [RxJava](https://github.com/ReactiveX/RxJava) - RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.
- [Kotlin.coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQjw1dGJBhD4ARIsANb6Odld-9wkN4Lkm6UJAvWRshusopwstZH5IXkSLzxv_Q5JYjgjozIywfcaAlS9EALw_wcB&gclsrc=aw.ds) - Concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Dagger2](https://dagger.dev/dev-guide/) - Used for Dependency injection
- [Apollo](https://github.com/apollographql/apollo-android) -  A strongly-typed, caching GraphQL client for the JVM, Android, and Kotlin multiplatform. 
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) - Enables mock creation, verification and stubbing for testing
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients
Kotlin coroutines
## Sample App and Source Code

[Sample.App](https://drive.google.com/file/d/1zhJAbgBXnlqnxNtl--YDGQIyXFSyeDG7/view?usp=sharing) - Get APK debug app
<p><a href="https://drive.google.com/file/d/1zhJAbgBXnlqnxNtl--YDGQIyXFSyeDG7/view?usp=sharing"><img width="150" alt="Get it on Google Drive" src="https://user-images.githubusercontent.com/47601553/128481494-a5347b33-cc5f-4a2a-8d2a-7fc5f6acd41b.jpg" data-canonical-src="https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Google_Drive.max-1100x1100.png" style="max-width:100%;"></a></p>

[Source.Code](https://github.com/Hechio/tv-show-manager) - Access to the project's github reporitory
