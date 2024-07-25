<img src="./graphics/icon.png" width="160" height="160" align="right" hspace="20">

# Dynamic Key

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Release](https://img.shields.io/maven-central/v/com.pranavpandey.android/dynamic-key)](https://search.maven.org/artifact/com.pranavpandey.android/dynamic-key)

A library to use other apps as a key to unlock features in the primary app on Android 
4.0 (API 14) and above.

> It uses [AndroidX][androidx] so, first [migrate][androidx-migrate] your project to AndroidX.
<br/>It is dependent on Java 8 due to the dependency on [Dynamic Utils][dynamic-utils].
<br/>Since v1.1.1, it is targeting Java 17 to provide maximum compatibility.
<br/>Since v1.2.0, the minimum SDK is Android 4.4 (API 19) to comply with the latest policies.

---

## Contents

- [Installation](#installation)
- [Usage](#usage)
    - [Broadcast](#broadcast)
    - [Sponsor](#sponsor)
    - [Dependency](#dependency)
- [License](#license)

---

## Installation

It can be installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    // For AndroidX enabled projects.
    implementation 'com.pranavpandey.android:dynamic-key:1.2.0'
}
```

---

## Usage

It provides an `activity` and a `receiver` to implement a dynamic key that can unlock features
in other (client) apps. It has utility methods to validate a key at runtime within the supported 
apps, so they must be signed with the same `signatures` to validate an installed key on the device.

> For a complete reference, please read the [documentation][documentation].

### Broadcast

Broadcast the key event on successful validation to notify key apps so that they can do
the required work. In most cases, it should hide the icon from the app launcher.

```java
// Broadcast the activate intent from the client app.
DynamicKeyUtils.broadcast(context, KEY_PACKAGE, Key.Intent.ACTION_ACTIVATE);
```

### Sponsor

Please become a [sponsor][sponsor] to get a detailed guide and priority support.

### Dependency

It depends on the [dynamic-utils][dynamic-utils] and [dynamic-preferences][dynamic-preferences]
to perform various internal operations. So, their functions can also be used to perform other
useful operations.

---

## Author

Pranav Pandey

[![GitHub](https://img.shields.io/github/followers/pranavpandey?label=GitHub&style=social)](https://github.com/pranavpandey)
[![Follow on Twitter](https://img.shields.io/twitter/follow/pranavpandeydev?label=Follow&style=social)](https://twitter.com/intent/follow?screen_name=pranavpandeydev)
[![Donate via PayPal](https://img.shields.io/static/v1?label=Donate&message=PayPal&color=blue)](https://paypal.me/pranavpandeydev)

---

## License

    Copyright 2022-2024 Pranav Pandey

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[androidx]: https://developer.android.com/jetpack/androidx
[androidx-migrate]: https://developer.android.com/jetpack/androidx/migrate
[documentation]: https://pranavpandey.github.io/dynamic-key
[sponsor]: https://github.com/sponsors/pranavpandey
[dynamic-utils]: https://github.com/pranavpandey/dynamic-utils
[dynamic-preferences]: https://github.com/pranavpandey/dynamic-preferences
