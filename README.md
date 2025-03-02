# maMeal

maMeal is an Android application that helps users plan their weekly meals. It provides features for meal searching, categorization, favoriting, and backup, ensuring accessibility even without an internet connection. The app integrates Firebase authentication and uses Room database for offline storage.

## Features

### 🔥 Core Features
- **Meal of the Day**: Displays a randomly selected meal for inspiration.
- **Search Meals**: Users can search meals by:
  - Country
  - Ingredient
  - Category
- **Meal Categories**: Browse through different meal categories.
- **Popular Meals by Country**: View meals specific to different countries.
- **Favorite Meals**:
  - Add/remove meals from favorites.
  - Stored locally using Room database.
  - View favorite meals even offline.
- **Meal Planner**:
  - Add meals to a weekly plan.
  - View the meal plan offline.
- **User Authentication**:
  - Login/signup using email and password.
  - Social authentication (Google) via Firebase Authentication.
  - Persistent login using SharedPreferences.
  - Guest mode for basic browsing.
- **Meal Details**:
  - Name, image, country of origin, ingredients , measurements, steps.
  - Embedded video tutorial.
  - Add/remove meal to/from favorites.
- **Data Backup**: Synchronize and restore user data upon login using Firebase.
- **Splash Screen**: Lottie animation for an engaging startup experience.

## 🛠 Tech Stack
- **Language**: Java
- **Architecture**: MVP (Model-View-Presenter)
- **Database**: Room (for offline storage)
- **Networking**: Retrofit
- **Reactive Programming**: RX-Java
- **Authentication & Backup**: Firebase Authentication & Firestore
- **Animations**: Lottie

## 🚀 Setup & Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/abdelrahman elshreif/maMeal.git
   ```
2. Open the project in **Android Studio**.
3. Sync dependencies using **Gradle**.
4. Run the application on an emulator or a physical device.

## 📸 Screenshots
![image](https://github.com/user-attachments/assets/c1e2d2eb-62ff-429d-9fdb-08a1740e50df)
![image](https://github.com/user-attachments/assets/5990a0f4-d967-477f-a887-850e20c5e769)
![image](https://github.com/user-attachments/assets/aca93237-a9d4-4637-9abe-6be70257c5a5)
![image](https://github.com/user-attachments/assets/973c7104-9372-443a-96dd-41eb858e23c2)
![image](https://github.com/user-attachments/assets/9f8b5cba-3516-4206-9961-2af20ecf03d5)
![image](https://github.com/user-attachments/assets/20c77fe5-1288-4fc8-b0b6-eb254e46bf20)
![image](https://github.com/user-attachments/assets/8a89e753-78d5-4bbd-950a-149a0fd73c89)
![image](https://github.com/user-attachments/assets/5b9b8435-47ec-4f90-8cfd-858fd0790efe)
![image](https://github.com/user-attachments/assets/2c206c78-2285-4b91-a0a0-62573eb890dd)
![image](https://github.com/user-attachments/assets/0761a1fd-8501-4150-9062-7bddd8a18c1e)

## 🤝 Contributions
Contributions are welcome! Feel free to fork the repository and submit pull requests.

## 📧 Contact
For any queries, reach out at [abdelrahman93999@gmail.com](mailto:abdelrahman93999@gmail.com).

