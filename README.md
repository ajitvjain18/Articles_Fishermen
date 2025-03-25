# Articles_Fishermen (Android Challenge Application)

**Overview**
<br/> 
This Android application fetches and displays a list of articles from a mock API. Users can view article details and switch between Grid and List view modes. The project showcases UI design, API integration, cache management, and various Android libraries.
<br/> 

**Features**<br/> 
Fetch articles from a mock API using Retrofit and OkHttp<br/> 
Display articles in Grid or List view (user-selectable, saved with SharedPreferences)<br/> 
View detailed information for each article<br/> 
Network status monitoring with Connectivity Manager<br/>
Clean and modern UI based on provided Figma design<br/> 

**Tech Stack**
<br/> 
Language: Java 
<br/> 
Min SDK: 28
<br/> 
Target SDK: 35<br/> 
Compile SDK: 35

**Libraries Used**
<br/> 
Retrofit - API calls<br/> 
OkHttp - Networking<br/> 
OkHttp Logging Interceptor - Logs network requests<br/> 
Dagger - Dependency Injection<br/> 
RxJava & RxAndroid - Reactive programming<br/> 
RxLifecycle - Manage lifecycle of RxJava components<br/> 
Gson - JSON parsing<br/> 
Glide - Image loading<br/> 
View Binding - Efficient UI binding<br/> 
<br/> 
**API Details**<br/> 
Endpoint: [Mock API](https://mocki.io/v1/9b040bf5-62aa-4ba6-b3f2-f7a1e146097a)<br/> 
Uses Retrofit and OkHttp for data fetching<br/> 
Logging Interceptor enabled to monitor requests and responses<br/> 

**UI & Design**<br/> 
Implements a clean and modern UI for the article list and detail screens<br/> 
Uses Glide for efficient image handling (16:9 ratio, center-cropped)<br/> 
View Binding reduces boilerplate code<br/> 
Fully aligned with Figma Design<br/> 

**Steps to Run the Project**<br/> 
Clone the repository: [git clone https://github.com/yourusername/android-challenge-app.git](https://github.com/ajitvjain18/Articles_Fishermen.git)<br/> 
Open the project in Android Studio.<br/> 
Sync Gradle dependencies.<br/> 
Run the application on an emulator or physical device.<br/> 

**Additional Features**<br/> 
Connectivity Handling: Detects internet availability and displays appropriate messages.<br/> 
Caching: Saves user preferences for view mode in SharedPreferences.<br/> 
Search Handling: Enables article filtering<br/> 
![Screenshot_20250325_123659](https://github.com/user-attachments/assets/5e972a7b-b6c1-455f-8ad3-84006a1cf252)

