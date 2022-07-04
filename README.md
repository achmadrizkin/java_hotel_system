## Java Hotel System MVVM
Make Hotel System MVVM using Java, and for backend i use Firebase, and PHP

## Feature
- MVVM
- Retrofit2
- 20+ Screen
- Google Maps (MAPBOX)
- Rx Java
- Dependency Injection (Dagger Hillt)
- Firebase Auth (Sign In using Google, Facebook, and Github account)
- Live Data
- Network API 
- Glide
- Print booking details to PDF with barcode
- Make Barcode (Already encrypt barcode, only can use in this app)
- Scan Barcode
- Shared Preference (Saved user login)
- Splash Screen, etc

## ROLE
- SUPER_ADMIN
  - make other people become super_admin, admin, or user
  - can ban user/admin (not yet implemented, i will update this)
  - change role to all user
- ADMIN
  - can insert hotel room
  - can update hotel room
  - can delete hotel room
  - can see all booking with filtered by date
- USER
  - can booking
  - can scan qr
  - can see history booking
  - can see filtered booking by date
  - can open maps, and directly open google maps for direction
  - can scan pdf

## API 
https://achmadrizkin.my.id/mobprog_hotel/select_user.php?uid={YOUR_UID_RIGHT_HERE}

    {
      "data": [
        {
          "id": "30",
          "name": "Achmad Rizki Nur Fauzie",
          "uid": "UR_UID_WILL_BE_RIGHT_HERE",
          "image_url": "https://avatars.githubusercontent.com/u/75843138?v=4",
          "role": "user",
          "log_via": "GITHUB"
        }
      ]
    }

## How to use MapBox, Facebook, Google, Github as AUTH
### read to their documentation, its just too much if i type here
- https://docs.mapbox.com/android/maps/guides/
- https://firebase.google.com/docs/auth/android/facebook-login
- https://firebase.google.com/docs/auth/android/google-signin
- https://firebase.google.com/docs/auth/android/github-auth
