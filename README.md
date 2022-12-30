# MyKitten
Demo App build using Kotlin multiplatform (KMM)

#### Shared Moudle
Shared module is a multiplatform module contains business logic for all the supported platforms. At the moment it supports only mobile but it will be extended to Web & desktop apps as well.

#### Mobile Moudle
Mobile module is also multiplatform module target only mobile platforms (android and IOS). It uses shared module for business logic and contains UI for both android and ios platform.

#### AndroidApp Moudle
This module is for android application. It uses mobile platform and only contains configuration.


<img src="https://user-images.githubusercontent.com/64067304/210078797-48d4433c-c534-4d0a-b256-4ac0ca23003b.png" width="292" height="648"/>


#### IosApp Moudle
This module is for ios application. It uses mobile platform and only contains configuration.

<img src="https://user-images.githubusercontent.com/64067304/210078778-d752fa1d-ae14-497d-9347-98470e48c7f2.png" width="292" height="648"/>
