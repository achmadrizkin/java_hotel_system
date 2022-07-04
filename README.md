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
  - same as ADMIN
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

## How to use MapBox, Facebook, Google, Github
### read to their documentation, its just too much if i type here
- https://docs.mapbox.com/android/maps/guides/
- https://firebase.google.com/docs/auth/android/facebook-login
- https://firebase.google.com/docs/auth/android/google-signin
- https://firebase.google.com/docs/auth/android/github-auth


## Previrew App
  Splash Screen                 |   Login or Sign Up       |  Login via GITHUB
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/175751462-8203b9b5-8e7c-4941-bc67-a9dcc1316269.png"> |<img src="https://user-images.githubusercontent.com/75843138/175751508-7cd7151f-313e-4a72-8b75-dc9d0d762b1e.png"> |<img src="https://user-images.githubusercontent.com/75843138/175751545-ed53381f-b0b3-4997-9681-6566036662d8.png">

  Booking (if user was not login)                 |   Me (if user was not login)       |  Room Detail (if user was not login)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/175751615-169428cd-f017-4d1c-b233-b839b5ff3479.png"> |<img src="https://user-images.githubusercontent.com/75843138/175751720-ee582e1e-6d04-4d64-b334-1e7bd157d289.png"> |<img src="https://user-images.githubusercontent.com/75843138/175751946-258efa5f-d5c6-412d-b46f-9da76fd7f28b.png">

  Show Direction on Google Maps                 |   Select Role      |  Me (if user role was super_admin)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/175752144-c6b63a25-970b-472a-8aa9-a8f0d35edd9b.png"> |<img src="https://user-images.githubusercontent.com/75843138/175752357-d214b8d3-6b87-4e91-bc50-0b0ad1aefdf4.png"> |<img src="https://user-images.githubusercontent.com/75843138/175752389-c185deb4-4778-492e-b0f7-a71c18b03657.png">

  Print in PDF                 |   Home      |  Me (if user role was admin)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/177167105-7a9cca90-d757-4f26-bf11-e2e5e91b2e8c.jpg"> |<img src="https://user-images.githubusercontent.com/75843138/177167393-2a1d454c-e468-48ef-ac53-70cc18ae4319.png"> |<img src="https://user-images.githubusercontent.com/75843138/177167878-70791d11-9cbc-4dbd-8492-d4d27ca2911f.png">

  Map Box Map                 |   Add Room      |  Me (Scan Barcode)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/177168042-a680bf09-6b4c-4d27-b1e8-b6c06d39d757.png"> |<img src="https://user-images.githubusercontent.com/75843138/177168209-65d1dddf-3dba-4267-b465-c4632d9fff6e.png"> |<img src="https://user-images.githubusercontent.com/75843138/177168461-50a0ca2e-f84c-47bc-a70b-38c4096ed46f.png">

  Me (if role was user)                 |   History Booking      |  Make QR-Code
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/177169221-c45553cf-78f9-409f-b22b-4496008077bb.png"> |<img src="https://user-images.githubusercontent.com/75843138/177169420-39f8889d-ecc0-40df-aa7b-dfbaa52f5ba2.png"> |<img src="https://user-images.githubusercontent.com/75843138/177169646-96d8ffb4-4022-456e-ac30-04fc1a06c04a.png">

  Get By City                 |   Search      |  Search (2)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/177170343-d8708a34-6956-42d6-a947-f95ccbe71738.png"> |<img src="https://user-images.githubusercontent.com/75843138/177170551-c5d3cede-6ba0-4f27-aa94-c323edab0bc4.png"> |<img src="https://user-images.githubusercontent.com/75843138/177170739-3c9ca2af-cf19-4286-9178-ee88d6868139.png">

  Search (no result)                 |   Room Detail (if role was user)      |  Filter date (admin/s_admin = get all transaction in that day, user = only show user transaction)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/177170867-b86862fb-224e-4482-ba82-a3d6664ab1a7.png"> |<img src="https://user-images.githubusercontent.com/75843138/177171117-b5533ad4-c23f-4c28-9322-a31726477a5e.png"> |<img src="https://user-images.githubusercontent.com/75843138/177171339-3cf100a3-29c9-442d-a916-b7542e818b12.png">

  All User (super admin)                  |   Change Role (super admin)      |  Room Details (role admin/super_admin)
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/75843138/177172002-57afd992-6da3-4f4c-840d-d45459728d92.png"> |<img src="https://user-images.githubusercontent.com/75843138/177172167-fef9ef5e-848a-48d5-9377-16ea482c354a.png"> |<img src="https://user-images.githubusercontent.com/75843138/177174166-7a20d5d3-e9ed-4d22-a0c2-0101693d598f.png">


## Video Documentation
https://drive.google.com/file/d/1SWFTcTWg_bVujjm-q9-kbavF5fZNihxI/view?usp=drivesdk (Scan Barcode)
https://drive.google.com/file/d/1ShNPVX62bospT5yKuj_9RuhWhQv-tKtl/view?usp=drivesdk (Change Role)
https://drive.google.com/file/d/1SlgNjTv_WIx86-OJRJePJUrLAIKYEyEe/view?usp=drivesdk (print into PDF)
