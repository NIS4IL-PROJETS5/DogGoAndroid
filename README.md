<div align="center">
<img width="200" src="https://user-images.githubusercontent.com/67436391/193463210-0fce1b15-da7a-406a-8103-9386d08f2bf4.png" align="center">
 <br/>
</div>

# DogGo-API

This project is an Android application that uses Retrofit to communicate with an external API. It allows for retrieving data, sending data, and interacting with the API's endpoints.

📅 September 22 - February 23  
🧑‍🎓 Semester 5  
🐶 Les Joyeux Cabots

## 🌳 Architecture

📦src  
┣ 📂main  
┃ ┣ 📂java  
┃ ┃ ┗ 📂com  
┃ ┃ ┃ ┗ 📂example  
┃ ┃ ┃ ┃ ┗ 📂doggo_android  
┃ ┃ ┃ ┃ ┃ ┣ 📂Adapters -> `display data in a specific way in the views`  
┃ ┃ ┃ ┃ ┃ ┣ 📂Enums -> `define a set of named values for a given type `  
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DOC_STATUS.java -> `define the different statuses of a document`  
┃ ┃ ┃ ┃ ┃ ┣ 📂Interfaces -> `define a set of method signatures without providing an implementation`  
┃ ┃ ┃ ┃ ┃ ┣ 📂Models -> `define the data structure for the objects used in the app`  
┃ ┃ ┃ ┃ ┃ ┣ 📂ViewModels -> `store and manage UI-related data in a lifecycle conscious way`  
┃ ┃ ┃ ┃ ┃ ┣ 📂Views -> `define the layout and interface for the app's screens`  
┃ ┃ ┃ ┃ ┃ ┣ 📜FirebaseMessagingHandler.java -> `handle sending and receiving notifications through Firebase`  
┃ ┃ ┃ ┃ ┃ ┣ 📜MainActivity.java -> `the entry point for the app`  
┃ ┃ ┃ ┃ ┃ ┣ 📜RetrofitClientInstance.java -> `handle communication with a remote server through Retrofit`  
┃ ┃ ┃ ┃ ┃ ┗ 📜Utils.java -> `provide various utility functions for the app`  
┃ ┣ 📂res  
┃ ┃ ┣ 📂drawable -> `store image files in various resolutions`  
┃ ┃ ┣ 📂drawable-v24 -> `store image files for devices with API level 24 or higher`  
┃ ┃ ┣ 📂layout -> `store layout files for the app's screens`  
┃ ┃ ┃ ┣ 📂fragments -> `store layout files for fragments`  
┃ ┃ ┣ 📂menu -> `store menu layout files`  
┃ ┃ ┣ 📂navigation -> `store navigation files`  
┃ ┃ ┃ ┗ 📜nav_graph.xml -> `define the navigation flow for the app`  
┃ ┃ ┣ 📂raw -> `store raw files such as config files`  
┃ ┃ ┃ ┗ 📜config.properties -> `store configuration settings for the app`  
┃ ┃ ┣ 📂values -> `store values such as strings, colors, and dimensions`  
┃ ┗ 📜AndroidManifest.xml -> `define the app's components and permissions`  


## ⚙️ Installation

### Requirements

- Android Studio 3.0 or later
- Basic knowledge of Java and Android app development
- Basic knowledge of Retrofit 2
- Firbase
- Clone the repo

```
git clone https://github.com/NIS4IL-PROJETS5/DogGoAndroid.git
```

##### 🔥 FIREBASE

1. Go to your the Settings icon Project settings in the Firebase console.
2. In the Your apps card, select the package name of the app for which you need a config file.
3. Click  google-services.json.
4. Move your config file into the module (app-level) directory of your app.
Make sure that you only have this most recent downloaded config file in your app.

##### 🚀 USAGE

1. Download or clone this repository to your computer
2. Open the project in Android Studio
3. Add the Retrofit dependencies in your build.gradle file (app module) like so:
	```implementation 'com.squareup.retrofit2:retrofit:2.9.0'```
	```implementation 'com.squareup.retrofit2:converter-gson:2.9.0'```
4. Configure your API base URL in the Constants.java file
5. Use the service classes to perform requests to the API, such as the RetrofitClient and APIService classes. These classes use Retrofit annotations to define request methods (GET, POST, PUT, DELETE, etc.) and request parameters (headers, body, etc.)
6. Use the model classes to store the data from the API. These classes correspond to the JSON objects returned by the API and are created using the Gson library.
7. Use the controller classes to handle requests to the API and interactions with the user interface.
8. Run the app on a device or emulator

## Using the API

- The API's functionality can be found in the service classes, such as the RetrofitClient and APIService classes.
- Use the model classes to store the data from the API.
- Use the controller classes to handle requests to the API and interactions with the user interface.

## Contributing

If you would like to contribute to this project, please submit a pull request for review. Be sure to adhere to existing coding and documentation standards.

## 🦾 Powered by

<div align="center" style="display:flex;">
    <a href="https://developer.android.com/docs" target="_blank">
        <img alt="Java" src="https://camo.githubusercontent.com/651195b8c66a9dd22316e672992077dbcecea4ca904b45a6681558ebc0ecc517/68747470733a2f2f75706c6f61642e77696b696d656469612e6f72672f77696b6970656469612f656e2f7468756d622f332f33302f4a6176615f70726f6772616d6d696e675f6c616e67756167655f6c6f676f2e7376672f33303070782d4a6176615f70726f6772616d6d696e675f6c616e67756167655f6c6f676f2e7376672e706e67" width="75" />
    </a>
    <a href="https://firebase.google.com/docs" target="_blank">
        <img alt="Firebase" src="https://user-images.githubusercontent.com/290451/76235994-04b94800-623d-11ea-9b5b-f7a1626ecb06.png" width="75" />
    </a>
    <a href="https://square.github.io/retrofit/2.x/retrofit/" target="_blank">
        <img alt="Retrofit" src="https://i.imgur.com/C9xQwoO.png" width="75" />
    </a>
</div>
